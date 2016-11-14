package com.xdja.ycm.mvp.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

/**
 * Created by 长霂大哥 on 2016/11/13.
 */
public class XDialog {
    private boolean mCancel;
    private boolean mCancelable = true;
    private Context mContext;
    private AlertDialog mAlertDialog;
    private Builder mBuilder;
    private View mView;
    private int mTitleResId;
    private CharSequence mTitle;
    private int mMessageResId;
    private CharSequence mMessage;
    private boolean mHasShow = false;
    public XDialog(Context context) {
        this.mContext = context;
    }

    public void setView(View view) {
        this.mView = view;
    }

    public View getView() {
        return mView;
    }

    public XDialog setCancledOnTuchOutside(boolean cancledOnTuchOutside) {
        this.mCancel = cancledOnTuchOutside;
        if (mBuilder != null) {
            mBuilder.setCanceledOnTouchOutside(mCancel);
        }
        return this;
    }

    public XDialog setCancelable(boolean cancelable) {
        this.mCancelable = cancelable;
        if (mBuilder != null) {
            mBuilder.setCancelable(cancelable);
        }
        return this;
    }
    public boolean isShowing() {
        if (mAlertDialog != null) {
            return mAlertDialog.isShowing();
        } else {
            return false;
        }
    }

    public void show() {
        if (!mHasShow) {
            mBuilder = new Builder();
        } else {
            mAlertDialog.show();
        }
        mHasShow = true;
    }

    public void dismiss() {
        mAlertDialog.dismiss();
    }

    private class Builder {

        public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            mAlertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        }

        public void setCancelable(boolean cancelable) {
            mAlertDialog.setCancelable(cancelable);
        }

        public void dismiss() {
            mAlertDialog.dismiss();
        }
    }

}
