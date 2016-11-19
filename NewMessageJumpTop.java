package com.xdjaxa.ycm.sharedtest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;

/**
 * 项目名称：ActomaV2
 * 类描述：
 * 创建人：yuchangmu
 * 创建时间：2016/11/19.
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class NewMessageJumpTop extends ImageButton {

    public NewMessageJumpTop(Context context) {
        super(context);
    }

    public NewMessageJumpTop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NewMessageJumpTop(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void removeButton() {
        int index = 0;
        int fromXDelta = index*100;
        int toXdelta = (index -1)*100;
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 200f, 0, 0);
        translateAnimation.setDuration(3000);
        translateAnimation.setFillAfter(true);
        startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (getVisibility() == View.VISIBLE) {
                    setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void showButton() {
        final int index = 0;
        int fromXDelta = index *100;
        int toXdelta = (index -1)*100;
        TranslateAnimation translateAnimation = new TranslateAnimation(50f, 0f, 0, 0);
        translateAnimation.setDuration(3000);
        translateAnimation.setFillAfter(true);
        startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
