package com.xdja.ycm.mvp.presenter;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.xdja.ycm.mvp.Command;
import com.xdja.ycm.mvp.view.ActivityVu;


/**
 * Created by ycm on 2016/11/11.
 */
public abstract class BasePresenterActivity<P extends Command, V extends ActivityVu> extends AppCompatActivity {
    private V vu;
    public V getVu() {
        return vu;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preBindView(savedInstanceState);
        if (getVuClass() != null) {
            try {
                vu = getVuClass().newInstance();
                vu.setCommand(getCommand());
                vu.setActivity(this);
                vu.init(getLayoutInflater(), null);
                setContentView(vu.getView());
                vu.onCreate();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    protected void preBindView(Bundle savedInstanceState) {

    }

    protected void onBindView(Bundle savedInstanceState) {

    }

    @NonNull
    protected abstract P getCommand();

    @Nullable
    protected abstract Class<? extends V> getVuClass();

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration config = new Configuration();
        config.fontScale = 1.0f;
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        return resources;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vu != null) {
            vu.onDestroy();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (vu != null) {
            vu.onPause();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (vu != null) {
            vu.onResume();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (vu != null) {
            vu.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (vu != null) {
            vu.onStop();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (vu != null) {
            vu.onAttachedToWindow();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (vu != null) {
            vu.onDetachedFromWindow();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (vu != null) {
            vu.onCreateOptionsMenu(menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (vu != null) {
            vu.onOptionItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (vu != null) {
            vu.onPrepareOptionsMenu(menu);
        }
        return super.onPrepareOptionsMenu(menu);

    }


}
