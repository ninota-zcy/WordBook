package com.example.wordbook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
        String sql = "select * from words ";
        Cursor cursor = db.rawQuery(sql,null);
        return ConvertCursorToWordList(cursor);
    }

    //疑难
    public ArrayList<Map<String,String>> ConvertCursorToWordList(Cursor cursor){
        ArrayList<Map<String,String>> list = new ArrayList<Map<String, String>>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Map<String,String> map = new HashMap<String,String>();
            System.out.println("dsl:"+cursor.getString(cursor.getColumnIndex("_id"))+ "word:"+cursor.getString(cursor.getColumnIndex("word")));
            map.put(Words.Word._ID, cursor.getString(cursor.getColumnIndex("_id")));
            map.put(Words.Word.COLUMN_NAME_WORD, cursor.getString(cursor.getColumnIndex("word")));
            list.add(map);
        }

        if(cursor.moveToFirst()){

        }
        return list;
    }

    public void InsertWord (String word, String meaning, String sample){
        String sql="insert into  words(word,meaning,sample) values(?,?,?)";

        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(sql,new String[]{word,meaning,sample});
    }

    public void Insert(String word, String meaning, String sample){
        String sql="insert into  words(word,meaning,sample) values(?,?,?)";

        //Gets the data repository in write mode*/
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(sql,new String[]{word,meaning,sample});
    }

    public void DeleteWord (String strID){
        String sql="delete from words where _id='"+strID+"'";

        SQLiteDatabase db = helper.getReadableDatabase();
        db.execSQL(sql);
    }
    public void Delete(String strID){
        String sql="delete from words where _id='"+strID+"'";

        SQLiteDatabase db = helper.getReadableDatabase();
        db.execSQL(sql);
    }

    public void UpdateUseSql(String strId, String strWord, String strMeaning, String strSample) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql="update words set word=?,meaning=?,sample=? where _id=?";
        db.execSQL(sql, new String[]{strWord, strMeaning, strSample,strId});
    }
    public void Update(String strId, String strWord, String strMeaning, String strSample) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql="update words set word=?,meaning=?,sample=? where _id=?";
        db.execSQL(sql, new String[]{strWord, strMeaning, strSample,strId});
    }
    //查找
    public ArrayList<Map<String, String>> SearchUseSql(String strWordSearch) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql="select * from words where word like ? order by word desc";
        Cursor c=db.rawQuery(sql,new String[]{"%"+strWordSearch+"%"});

        return ConvertCursorToWordList(c);
    }
    public ArrayList<Map<String, String>> Search(String strWordSearch) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql="select * from words where word like ? order by word desc";
        Cursor c=db.rawQuery(sql,new String[]{"%"+strWordSearch+"%"});

        return ConvertCursorToWordList(c);
    }



}
