package com.xdja.ycm.studynote.ui.view;

import android.content.Intent;

import com.xdja.ycm.mvp.view.ImpActivitySuperView;
import com.xdja.ycm.studynote.R;
import com.xdja.ycm.studynote.presenter.Command.IFloatButtonCommand;
import com.xdja.ycm.studynote.presenter.activity.MainActivity;
import com.xdja.ycm.studynote.ui.def.IFloatButtonVu;

/**
 * Created by 长霂大哥 on 2016/11/14.
 */
public class FloatButtonView extends ImpActivitySuperView<IFloatButtonCommand> implements IFloatButtonVu {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_float;
    }


}
