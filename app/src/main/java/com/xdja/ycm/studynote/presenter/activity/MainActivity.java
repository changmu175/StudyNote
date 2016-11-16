package com.xdja.ycm.studynote.presenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xdja.ycm.studynote.R;
import com.xdja.ycm.studynote.ui.NoteActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.fab) FloatingActionButton fab;
//    @InjectView(R.id.note_fab) FloatingActionButton note_fab;
//    @InjectView(R.id.camera_fab) FloatingActionButton camera_fab;
//    @InjectView(R.id.record_fab) FloatingActionButton record_fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
//        initView();
    }
    private void initView() {
//        fab.setVisibility(View.VISIBLE);
//        note_fab.setVisibility(View.GONE);
//        camera_fab.setVisibility(View.GONE);
//        record_fab.setVisibility(View.GONE);
    }
    @OnClick(R.id.fab)
    void displayMenu(){
//        fab.setVisibility(View.GONE);
//        note_fab.setVisibility(View.VISIBLE);
//        camera_fab.setVisibility(View.VISIBLE);
//        record_fab.setVisibility(View.VISIBLE);
        Intent intent = new Intent();
        intent.setClass(this, NoteActivity.class);
        startActivity(intent);
    }

//    @OnClick(R.id.note_fab)
//    void textNote() {
//        Snackbar.make(note_fab, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
//    }
//
//    @OnClick(R.id.record_fab)
//    void recordNote() {
//        Snackbar.make(record_fab, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
//    }
//
//    @OnClick(R.id.camera_fab)
//    void cameraNote() {
//        Snackbar.make(camera_fab, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
