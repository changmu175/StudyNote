package com.xdja.ycm.mvp.view;

import android.support.annotation.StringRes;
import android.view.Menu;
import android.view.MenuItem;
;import com.xdja.ycm.mvp.Command;
import com.xdja.ycm.mvp.Vu;

/**
 * Created by ycm on 2016/11/11.
 */
public interface ActivityVu<P extends Command> extends Vu<P> {
    void onCreate();
    void onResume();
    void onStart();
    void onRestart();
    void onPause();
    void onStop();
    void onDestroy();
    void onAttachedToWindow();
    void onDetachedFromWindow();
    boolean onCreateOptionsMenu(Menu menu);
    boolean onOptionItemSelected(MenuItem menuItem);
    boolean onPrepareOptionsMenu(Menu menu);
    void showCommonProgressDialog(String msg);
    void showCommonProgressDialog(@StringRes int resId);
    void dismissCommonProgressDialog();
    void showToast(String msg);
    void showToast(@StringRes int resId);
}
