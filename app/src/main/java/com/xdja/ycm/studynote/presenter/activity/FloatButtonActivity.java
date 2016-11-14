package com.xdja.ycm.studynote.presenter.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xdja.ycm.mvp.presenter.BasePresenterActivity;
import com.xdja.ycm.studynote.presenter.Command.IFloatButtonCommand;
import com.xdja.ycm.studynote.ui.def.IFloatButtonVu;
import com.xdja.ycm.studynote.ui.view.FloatButtonView;

/**
 * Created by 长霂大哥 on 2016/11/14.
 */
public class FloatButtonActivity extends BasePresenterActivity<IFloatButtonCommand, IFloatButtonVu> implements IFloatButtonCommand {

    @NonNull
    @Override
    protected IFloatButtonCommand getCommand() {
        return this;
    }

    @Nullable
    @Override
    protected Class<? extends IFloatButtonVu> getVuClass() {
        return FloatButtonView.class;
    }
}
