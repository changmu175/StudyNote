package com.xdja.ycm.studynote.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 项目名称：ActomaV2
 * 类描述：
 * 创建人：yuchangmu
 * 创建时间：2017/1/23.
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class NoteDatabaseHelper extends SQLiteOpenHelper {

    public NoteDatabaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NoteDatabaseBuilder.SQL_CREATE_TABLE_NOTE_BILL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
