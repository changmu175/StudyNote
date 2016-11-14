package com.xdja.ycm.studynote.ui.view;

import android.content.Intent;

import com.xdja.ycm.mvp.view.ImpActivitySuperView;
import com.xdja.ycm.studynote.R;
import com.xdja.ycm.studynote.presenter.Command.ISplashCommand;
import com.xdja.ycm.studynote.presenter.activity.MainActivity;
import com.xdja.ycm.studynote.ui.def.ISplashVu;

/**
 * Created by 长霂大哥 on 2016/11/12.
 */
public class SplashView extends ImpActivitySuperView<ISplashCommand> implements ISplashVu{
    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent();
        intent.setClass(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_splash;
    }
}
