package com.example.wordbook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wordbook.dummy.Words;

import java.util.ArrayList;
import java.util.Map;

public class OperationDB {
    static SQLiteDatabase db;
    static WordsDBHelper helper;

    static OperationDB ins = new OperationDB();
    public static OperationDB getOperations(){
        return OperationDB.ins;
    }

    private OperationDB(){
    }

    public void close(){
        if(helper != null){
            helper.close();
        }
    }

    public Words.WordDescription getSingleWord(String id){
        return null;
    }

    public ArrayList<Map<String,String>> getAllWord(){
        return null;
    }

    public ArrayList<Map<String,String>> ConvertCursor2WordList(Cursor cursor){
        return null;
    }

    public void InsertWord (String word, String meaning, String sample){}

    public void Insert(String word, String meaning, String sample){}

    public void DeleteWord (String strID){}
    public void Delete(String strID){}

    public void UpdateUseSql(String strId, String strWord, String strMeaning, String strSample) {}
    public void Update(String strId, String strWord, String strMeaning, String strSample) { }
    //查找
    public ArrayList<Map<String, String>> SearchUseSql(String strWordSearch) {
        return null;
    }
    public ArrayList<Map<String, String>> Search(String strWordSearch) {
        return null;
    }



}
