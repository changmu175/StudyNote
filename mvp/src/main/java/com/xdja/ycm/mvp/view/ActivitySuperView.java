package com.xdja.ycm.mvp.view;

import android.graphics.ImageFormat;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xdja.ycm.mvp.Command;
import com.xdja.ycm.mvp.widget.XDialog;
import com.xdja.ycm.presenter.R;

/**
 * Created by 长霂大哥 on 2016/11/13.
 */
public class ActivitySuperView<T extends Command> extends SuperView<T> implements ActivityVu<T> {
    private XDialog progressDialog;
    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onAttachedToWindow() {

    }

    @Override
    public void onDetachedFromWindow() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionItemSelected(MenuItem menuItem) {
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void showCommonProgressDialog(String msg) {
        showCommonProgressDialog(msg, false, true);
    }

    public void showCommonProgressDialog(String msg, boolean isTouch, boolean onKey) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        if (progressDialog == null) {
            progressDialog = new XDialog(getContext());
            View view = LayoutInflater.from(getContext()).inflate(R.layout.view_circle_progress_dialog, null);
                    progressDialog.setView(view);
        }
        View progressDialogView = progressDialog.getView();
        if (progressDialogView != null) {
            TextView messageView = (TextView) progressDialogView.findViewById(R.id.dialog_message);
            messageView.setVisibility(View.VISIBLE);
            messageView.setText(msg);
        }
        progressDialog.setCancledOnTuchOutside(isTouch);
        progressDialog.setCancelable(onKey);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void showCommonProgressDialog(@StringRes int resId) {
        showCommonProgressDialog(getStrings(resId), false, true);
    }

    @Override
    public void dismissCommonProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing() && !getActivity().isFinishing()){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToast(@StringRes int resId) {
        Toast.makeText(getContext(), getStrings(resId), Toast.LENGTH_LONG).show();
    }
}
