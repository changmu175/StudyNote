package com.xdja.ycm.studynote.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

/**
 * 项目名称：ActomaV2
 * 类描述：
 * 创建人：yuchangmu
 * 创建时间：2017/1/23.
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class DbHelper {
    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "_note.db";
    private static DbHelper dbHelper;
    private NoteDatabaseHelper noteDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    protected DbHelper() {

    }

    public static DbHelper getInstance() {
        if (dbHelper == null) {
            synchronized (DbHelper.class) {
                dbHelper = new DbHelper();
            }
        }
        return dbHelper;
    }

    public void initDatabase(Context context) {
        if (context == null) {
            throw new RuntimeException("database init error");
        }
        this.noteDatabaseHelper = new NoteDatabaseHelper(context, DATABASE_NAME, DATABASE_VERSION);
    }

    public synchronized SQLiteDatabase getDatebase() {
        if (this.noteDatabaseHelper == null) {
            throw new RuntimeException("database already close...");
        }

        if (sqLiteDatabase == null) {
            sqLiteDatabase = noteDatabaseHelper.getReadableDatabase();
        }
        return sqLiteDatabase;
     }
}
