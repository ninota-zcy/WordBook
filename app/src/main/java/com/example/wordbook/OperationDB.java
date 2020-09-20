package com.example.wordbook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wordbook.dummy.Words;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OperationDB {

    static WordsDBHelper helper;

    static OperationDB ins = new OperationDB();
    public static OperationDB getOperations(){
        return OperationDB.ins;
    }

    private OperationDB(){
        if(helper == null){
            helper = new WordsDBHelper(WordsApplication.getContext());
        }
    }

    public void close(){
        if(helper != null){
            helper.close();
        }
    }

    public Words.WordDescription getSingleWord(String id){
        Words.WordDescription result = new Words.WordDescription();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from words where _id = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{id});
        if(cursor.moveToFirst()){
            result.setWord(cursor.getString(cursor.getColumnIndex("word")));
            result.setMeaning(cursor.getString(cursor.getColumnIndex("meaning")));
            result.setSample(cursor.getString(cursor.getColumnIndex("sample")));
            return result;
        }
        return null;

    }

    public ArrayList<Map<String,String>> getAllWord(){
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from words";
        Cursor cursor = db.rawQuery(sql,null);
        return ConvertCursorToWordList(cursor);
    }

    //前者为id，后者为Word
    public ArrayList<Map<String,String>> ConvertCursorToWordList(Cursor cursor){
        ArrayList<Map<String,String>> list = new ArrayList<Map<String, String>>();
        if(cursor.moveToFirst()){
            Map<String,String> map = new HashMap<String,String>();
            map.put(cursor.getColumnName("id"), cursor.getColumnName("word"));
        }
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
