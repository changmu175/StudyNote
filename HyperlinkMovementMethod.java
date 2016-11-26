package com.xdja.imp.widget;

import android.app.Activity;
import android.graphics.Color;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xdja.imp.domain.model.TalkMessageBean;

/**
 * 项目名称：ActomaV2
 * 类描述：
 * 创建人：yuchangmu
 * 创建时间：2016/11/25.
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HyperlinkMovementMethod extends LinkMovementMethod {
    private long lastClickTime;
    private static final long CLICK_DELAY = 500l;
    private static ViewGroup viewGroup1;
    TextPaint ds1;
    TextPaint ds2;
    boolean isPress = false;
    static Activity activity1;
    static TalkMessageBean talkMessageBean1;
    Layout layout;
//    TextView textView;
    Spannable buffers;
    private static final HyperlinkMovementMethod INSTANCE = new HyperlinkMovementMethod();
    public static HyperlinkMovementMethod getInstance(final ViewGroup viewGroup, final TalkMessageBean talkMessageBean, final Activity activity) {
        viewGroup1 = viewGroup;
        activity1 = activity;
        talkMessageBean1 = talkMessageBean;

        return INSTANCE;
    }

//    public void setHyperlinkMovementMethod (ViewGroup viewGroup, TalkMessageBean talkMessageBean, Activity activity) {
//        this.viewGroup = viewGroup;
//        this.activity = activity;
//        this.talkMessageBean = talkMessageBean;
//    }

    @Override
    public boolean onTouchEvent(final TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();
//        textView = widget;
        Log.d("duixianga", widget.toString()+"    "+ viewGroup1.toString());
        buffers = buffer;
        ds1 = new TextPaint();
        ds2 = new TextPaint();
        ds1.bgColor = Color.BLUE;
        ds2.bgColor = Color.TRANSPARENT;
        gestureDetector.onTouchEvent(event);
        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
            isPress = false;
            int x = (int) event.getX();
            int y = (int) event.getY();
            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    if (System.currentTimeMillis() - lastClickTime < CLICK_DELAY) {
                        link[0].onClick(widget);
                    }
                } else {
                    Selection.setSelection(buffer, buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]));
                    lastClickTime = System.currentTimeMillis();
                }
                return true;
            } else {
                Selection.removeSelection(buffer);
            }
        }
        return false;
    }

    GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("duixianga", "    "+ viewGroup1.toString());
            viewGroup1.performLongClick();
        }
    });


}
