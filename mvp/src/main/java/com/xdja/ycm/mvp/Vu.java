package com.xdja.ycm.mvp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ycm on 2016/11/11.
 */
public interface Vu<P extends Command> {

    void init(LayoutInflater inflater, ViewGroup container);

    View getView();

    void setCommand(P command);

    <A extends Activity> void setActivity(A activity);
}
