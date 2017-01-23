package com.xdja.ycm.studynote.db;

/**
 * 项目名称：ActomaV2
 * 类描述：
 * 创建人：yuchangmu
 * 创建时间：2017/1/23.
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class NoteDatabaseBuilder {
    public static final String TABLE_NAME = "bill";
    public static final String ID = "_id";
    public static final String TIME = "time";
    public static final String PURPOSE = "purpose";
    public static final String REMARK = "remark";
    public static final String PAY_TYPE = "pay_type";
    public static final String IS_INCOME = "is_income";

    public static final String[] ALL_COLUMNS = {ID, TIME, PURPOSE, REMARK, PAY_TYPE, IS_INCOME};

    public static final String SQL_CREATE_TABLE_NOTE_BILL =
            "CRATE TABLE " + TABLE_NAME + " ("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + TIME + " INTEGER DEFAULT 0"
                    + PURPOSE + " TEXT"
                    + REMARK + " TEXT"
                    + PAY_TYPE + " INTEGER DEFAULT -1"
                    + IS_INCOME + " INTEGER DEFAULT -1)";
}
