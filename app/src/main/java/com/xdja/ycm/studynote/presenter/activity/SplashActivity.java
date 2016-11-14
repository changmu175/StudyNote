package com.xdja.ycm.studynote.presenter.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xdja.ycm.mvp.presenter.BasePresenterActivity;
import com.xdja.ycm.studynote.presenter.Command.ISplashCommand;
import com.xdja.ycm.studynote.ui.def.ISplashVu;
import com.xdja.ycm.studynote.ui.view.SplashView;

public class SplashActivity extends BasePresenterActivity<ISplashCommand, ISplashVu> implements ISplashCommand {

    @Override
    protected void onBindView(Bundle savedInstanceState) {
        super.onBindView(savedInstanceState);
    }

    @NonNull
    @Override
    protected ISplashCommand getCommand() {
        return this;
    }

    @Nullable
    @Override
    protected Class<? extends ISplashVu> getVuClass() {
        return SplashView.class;
    }
}
