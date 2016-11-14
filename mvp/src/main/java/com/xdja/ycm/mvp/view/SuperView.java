package com.xdja.ycm.mvp.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xdja.ycm.mvp.Command;
import com.xdja.ycm.mvp.Vu;
import com.xdja.ycm.mvp.annotation.ContentView;

import butterknife.ButterKnife;

/**
 * Created by 长霂大哥 on 2016/11/12.
 */
public class SuperView<T extends Command> implements Vu<T> {
    private View view;
    private T command;
    private Activity activity;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        ContentView contentView = getClass().getAnnotation(ContentView.class);
        if (container != null) {
           view =  inflater.inflate(contentView.value(), container, false);
        } else {
            view = inflater.inflate(getLayoutRes(), container, false);
        }
        injectView();
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setCommand(T command) {
        this.command = command;
    }

    @Override
    public <A extends Activity> void setActivity(A activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public T getCommand() {
        return this.command;
    }
    public Context getContext() {
        return this.activity;
    }
    @NonNull
    public String getStrings(@StringRes int res) {
        return getContext().getString(res);
    }

    @NonNull
    public int getColor(@ColorRes int res) {
        return getContext().getResources().getColor(res);
    }

    @Nullable
    public Drawable getDrawable(@DrawableRes int res) {
        return  getContext().getResources().getDrawable(res);
    }

    @Nullable
    public int getDimensRes(@DimenRes int res) {
        return getContext().getResources().getDimensionPixelOffset(res);
    }
    protected void injectView() {
        ButterKnife.inject(this, view);
    }

    @LayoutRes
    protected int getLayoutRes() {
        return -1;
    }
}
