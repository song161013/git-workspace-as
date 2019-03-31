package com.android.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by songfei on 2018/6/19
 * Description：
 */

public class MySqliteHelper extends SQLiteOpenHelper {

    private static final String TAG = "MySqliteHelper";
    private final String CREATE_TAGLE_NAME = "CREATE TABLE news ("
            + "id integer primary key autoincrement"
            + "title text"
            + "content text"
            + "publicdate integer"
            + "commentCount integer)";

    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TAGLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
