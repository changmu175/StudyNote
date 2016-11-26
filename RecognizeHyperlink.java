package com.xdja.imp.util;

import android.app.Activity;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xdja.imp.domain.model.TalkMessageBean;
import com.xdja.imp.widget.HyperlinkMovementMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目名称：ActomaV2
 * 类描述：
 * 创建人：yuchangmu
 * 创建时间：2016/11/25.
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class RecognizeHyperlink {
    String at = "@";
    private final int LINK_WEB = 0x01;
    private final int LINK_EMAIL = 0x02;
    private final int LINK_PHONE = 0x04;
    public final int ALL = LINK_WEB | LINK_EMAIL | LINK_PHONE;
    private String[] webPrefix = new String[]{"http://", "https://", "rtsp://"};
    private String[] emailPrefix = new String[]{"mailto:"};
    private String[] phonePrefix = new String[]{"tel:"};
    public static final String GOOD_IRI_CHAR =
            "a-zA-Z0-9\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF";
    private static final String IRI
            = "[" + GOOD_IRI_CHAR + "]([" + GOOD_IRI_CHAR + "\\-]{0,61}[" + GOOD_IRI_CHAR + "]){0,1}";
    private static final String GOOD_GTLD_CHAR =
            "a-zA-Z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF";
    private static final String GTLD = "[" + GOOD_GTLD_CHAR + "]{2,63}";
    private static final String HOST_NAME = "(" + IRI + "\\.)+" + GTLD;
    public static final Pattern IP_ADDRESS
            = Pattern.compile(
            "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                    + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                    + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                    + "|[1-9][0-9]|[0-9]))");
    public static final Pattern DOMAIN_NAME
            = Pattern.compile("(" + HOST_NAME + "|" + IP_ADDRESS + ")");
//    public static final Pattern WEB_URL = Pattern.compile(
//            "((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)"
//                    + "\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_"
//                    + "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?"
//                    + "(?:" + DOMAIN_NAME + ")"
//                    + "(?:\\:\\d{1,5})?)" // plus option port number
//                    + "(\\/(?:(?:[" + "\\;\\/\\?\\:\\@\\&\\=\\#\\~"  // plus option query params
//                    + "\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?"
//                    + "(?:\\b|$)");

    public static final Pattern WEB_URL = Pattern.compile(
            "((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)"
                    + "\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_"
                    + "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?"
                    + "(?:" + DOMAIN_NAME + ")"
                    + "(?:\\:\\d{1,5})?)" // plus option port number
                    + "(\\/(?:(?:[" + GOOD_IRI_CHAR + "\\;\\/\\?\\:\\@\\&\\=\\#\\~"  // plus option query params
                    + "\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?"
                    + "(?:\\b|$)");

    public static final Pattern WEB_URL2 = Pattern.compile(
            "((f|ht){1}(tp|tps):\\/\\/)?([^\\u4E00-\\u9Fa5][^\\s]+\\.)+[\\w-]+(\\/[\\w- ./?%&=]+)+");
    private static final RecognizeHyperlink INSTANCE = new RecognizeHyperlink();

    public static final RecognizeHyperlink getInstance() {
        return INSTANCE;
    }
    public void recognizeHyperlinks(final TextView contentView, TalkMessageBean talkMessageBean, Activity activity, final ViewGroup viewGroup, int type) {
        if (type == 0) {
            return;
        }

        String content = contentView.getText().toString();
        List<HyperLinkBean> links = new ArrayList<>();
        if ((type & LINK_WEB) != 0) {
            getWebOrEmailLink(links, content, Patterns.WEB_URL, webPrefix, isEmail(content));
        }

        if ((type & LINK_EMAIL) != 0) {
            getWebOrEmailLink(links, content, Patterns.EMAIL_ADDRESS, emailPrefix, !isEmail(content));
        }

        if ((type & LINK_PHONE) != 0) {
            getPhoneLink(links, content, Patterns.PHONE);
        }
        hyperlinkFilter(links);
        if (links.size() != 0) {
            final SpannableString spannableString = new SpannableString(content);
//            SpannableStringBuilder builder = new SpannableStringBuilder(content);
            for (final HyperLinkBean link : links) {
                ForegroundColorSpan linkColor = new ForegroundColorSpan(Color.RED);
                spannableString.setSpan(linkColor, link.startPosition, link.endPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(
                        new SpanClickListenser() {
                    @Override
                    public void onClick(View widget) {
                        if (link.linkType == LINK_WEB) {
                            Toast.makeText(widget.getContext(), "web      " + link.hyperlink, Toast.LENGTH_LONG).show();
                        } else if (link.linkType == LINK_EMAIL) {
                            Toast.makeText(widget.getContext(), "Email      " + link.hyperlink, Toast.LENGTH_LONG).show();

                        } else if (link.linkType == LINK_PHONE) {
                            Toast.makeText(widget.getContext(), "phone      " + link.hyperlink, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setUnderlineText(false);
                    }
                }
                        , link.startPosition, link.endPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            contentView.setText(spannableString);

            contentView.setMovementMethod(HyperlinkMovementMethod.getInstance(viewGroup, talkMessageBean, activity));
        }
    }

    private boolean isEmail(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        } else {
            return s.contains(at);
        }
    }

    private void getWebOrEmailLink(List<HyperLinkBean> links,
                                   String s, Pattern pattern, String[] schemes, boolean isEmail) {
        String str = s.toString();
        Matcher matcher = pattern.matcher(s);
//        boolean isEmail = isEmail;
        if (isEmail) {
            return;
        }
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            HyperLinkBean hyperLink = new HyperLinkBean();
            String link = createLink(matcher.group(0), schemes);
            hyperLink.hyperlink = link;
            hyperLink.startPosition = start;
            hyperLink.endPosition = end;
            if (pattern == Patterns.WEB_URL) {
                hyperLink.linkType = LINK_WEB;
            } else if (pattern == Patterns.EMAIL_ADDRESS) {
                hyperLink.linkType = LINK_EMAIL;
            }
            links.add(hyperLink);
        }
    }

    private String createLink(String group, String[] prefixes) {
        boolean isHasPrefix = false;
        for (int i = 0; i < prefixes.length; i++) {
            if (group.regionMatches(true, 0, prefixes[i], 0, prefixes[i].length())) {
                isHasPrefix = true;
                break;
            }
        }

        if (!isHasPrefix) {
            group = prefixes[0] + group;
        }
        return group;
    }

    private void getPhoneLink(List<HyperLinkBean> links, String s, Pattern pattern) {
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            HyperLinkBean hyperLink = new HyperLinkBean();
            String link = createLink(matcher.group(0), phonePrefix);
            hyperLink.hyperlink = link;
            hyperLink.startPosition = start;
            hyperLink.endPosition = end;
            hyperLink.linkType = LINK_PHONE;
            links.add(hyperLink);
        }
    }


    private List<HyperLinkBean> sortAllLink(List<HyperLinkBean> linkList) {
        Comparator<HyperLinkBean> comparator = new Comparator<HyperLinkBean>() {
            @Override
            public int compare(HyperLinkBean lhs, HyperLinkBean rhs) {
                if (lhs.startPosition < rhs.startPosition) {
                    return -1;
                }

                if (lhs.startPosition > rhs.startPosition) {
                    return 1;
                }

                if (lhs.endPosition < rhs.endPosition) {
                    return -1;
                }

                if (lhs.endPosition > rhs.endPosition) {
                    return 1;
                }
                return 0;
            }
        };
        Collections.sort(linkList, comparator);
        return linkList;
    }

    private void hyperlinkFilter(List<HyperLinkBean> linkList) {
        int len = linkList.size();
        int i = 0;
        linkList = sortAllLink(linkList);
        while (i < len - 1) {
            HyperLinkBean a = linkList.get(i);
            HyperLinkBean b = linkList.get(i + 1);
            int index = -1;
            if ((a.startPosition <= b.startPosition) && (a.endPosition >= b.startPosition)) {
                if (a.endPosition >= b.endPosition) {
                    index = i + 1;
                } else if ((a.endPosition - a.startPosition) > (b.endPosition - b.startPosition)) {
                    index = i + 1;
                } else if ((a.endPosition - a.startPosition) < (b.endPosition - b.startPosition)) {
                    index = i;
                }
                if (index != -1) {
                    linkList.remove(index);
                    len--;
                    continue;
                }

            }

            i++;
        }
    }

    class SpanClickListenser extends ClickableSpan implements View.OnLongClickListener {

        @Override
        public void onClick(View v) {
        }


        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    class HyperLinkBean {
        String hyperlink;
        int startPosition;
        int endPosition;
        int linkType;
    }
}
