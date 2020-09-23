package com.example.wordbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.wordbook.dummy.Words;

public class WordsDBHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME= "wordsdb";//数据库名字
    private final static int DATABASE_VERSION = 1;//数据库版本
    //建表语句
    private final static String SQL_CREATE_DATABASE = "CREATE TABLE "
            + Words.Word.TABLE_NAME + " (" +
            Words.Word._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Words.Word.COLUMN_NAME_WORD + " TEXT" + "," +
            Words.Word.COLUMN_NAME_MEANING + " TEXT" + ","
            + Words.Word.COLUMN_NAME_SAMPLE + " TEXT" + " )";

    //删表语句
    private final static String SQL_DELETE_DATABASE = "DROP TABLE IF EXISTS " + Words.Word.TABLE_NAME;


    public WordsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //建表
    public void onCreate(SQLiteDatabase db) {
        Log.v("test","建表成功");
        db.execSQL(SQL_CREATE_DATABASE);
    }

    @Override
    //更新，先删后建
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_DATABASE);
        onCreate(db);
    }
}
