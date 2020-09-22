package com.example.wordbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;

import com.example.wordbook.dummy.Words;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WordListFragment.OnFragmentInteractionListener,WordDetailFragment.OnFragmentInteractionListener{
    WordsDBHelper helper;

    public static List<Activity> activityList = new LinkedList();
    private static final String TAG = "text";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.caidan,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.all:{
                RefreshWordItemFragment();
                break;
            }
            case R.id.search:{
                SearchDialog();
                break;
            }
            case R.id.add:{
                InsertDialog();
                break;
            }
            case R.id.help:{
                Intent intent = new Intent(MainActivity.this,Help.class);
                startActivity(intent);
                break;
            }
            case R.id.exit:{
                exit();
            }
        }
        return false;
    }
    public void exit(){

        for(Activity act:activityList){

            act.finish();

        }

        System.exit(0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityList.add(this);
        helper = new WordsDBHelper(this);
        helper.getReadableDatabase();
        //SQLiteDatabase db= SQLiteDatabase.openOrCreateDatabase("mydb",null);


        helper.close();

    }

    @Override
    public void onWordItemClick(String id) {
        if(isLand()){
            ChangeWordDetailFragment(id);
        }else{
            Intent intent = new Intent(MainActivity.this,WordDetailActivity.class);
            intent.putExtra(WordDetailFragment.ARG_ID,id);
            startActivity(intent);
        }
    }

    @Override
    public void onDeleteDialog(String strID) {
        DeleteDialog(strID);
    }

    @Override
    public void onUpdateDialog(String strID) {
            OperationDB operationDB = OperationDB.getOperations();
            if(operationDB != null && strID != null){
                Words.WordDescription item = operationDB.getSingleWord(strID);
                if(item != null)
                    UpdateDialog(strID,item.word,item.meaning,item.sample);

            }
    }

    @Override
    public void OnWordDetailClick(Uri uri) {

    }

    private boolean isLand(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            return true;
        }
        return false;
    }


    private void ChangeWordDetailFragment(String id){
        Bundle arguments = new Bundle();
        arguments.putString(WordDetailFragment.ARG_ID, id);

        WordDetailFragment fragment = new WordDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().replace(R.id.worddetail, fragment).commit();
    }

    //新增对话框
    public void InsertDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.insert, null);
        new AlertDialog.Builder(this)
                .setTitle("新增单词")//标题
                .setView(tableLayout)//设置视图
                //确定按钮及其动作
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strWord = ((EditText) tableLayout.findViewById(R.id.txtWord)).getText().toString();
                        String strMeaning = ((EditText) tableLayout.findViewById(R.id.txtMeaning)).getText().toString();
                        String strSample = ((EditText) tableLayout.findViewById(R.id.txtSample)).getText().toString();

                        Log.v("test",strWord+":"+strMeaning+":"+strSample);
                        //既可以使用Sql语句插入，也可以使用使用insert方法插入
                        // InsertUserSql(strWord, strMeaning, strSample);
                        OperationDB wordsDB=OperationDB.getOperations();
                        wordsDB.Insert(strWord, strMeaning, strSample);

                        //单词已经插入到数据库，更新显示列表
                        RefreshWordItemFragment();
                    }
                })
                //取消按钮及其动作
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {           }
                })
                .create()//创建对话框
                .show();//显示对话框
    }

    //删除对话框
    private void DeleteDialog(final String strId) {
        new AlertDialog.Builder(this).setTitle("删除单词")
                .setMessage("是否真的删除单词?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                        //既可以使用Sql语句删除，也可以使用使用delete方法删除
                        OperationDB wordsDB=OperationDB.getOperations();
                        wordsDB.Delete(strId);
                        //单词已经删除，更新显示列表
                        RefreshWordItemFragment();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create().show();
    }

    //修改对话框
    private void UpdateDialog(final String strId, final String strWord, final String strMeaning, final String strSample) {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.insert, null);
        ((EditText) tableLayout.findViewById(R.id.txtWord)).setText(strWord);
        ((EditText) tableLayout.findViewById(R.id.txtMeaning)).setText(strMeaning);
        ((EditText) tableLayout.findViewById(R.id.txtSample)).setText(strSample);
        new AlertDialog.Builder(this).setTitle("修改单词")//标题
            .setView(tableLayout)//设置视图
        // 确定按钮及其动作
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String strNewWord = ((EditText) tableLayout.findViewById(R.id.txtWord)).getText().toString();

                    String strNewMeaning = ((EditText) tableLayout.findViewById(R.id.txtMeaning)).getText().toString();
                    String strNewSample = ((EditText) tableLayout.findViewById(R.id.txtSample)).getText().toString();
                    //既可以使用Sql语句更新，也可以使用使用update方法更新
                    OperationDB wordsDB=OperationDB.getOperations();
                    wordsDB.UpdateUseSql(strId, strNewWord, strNewMeaning, strNewSample);
                    //单词已经更新，更新显示列表
                    RefreshWordItemFragment();
                }

    })
    //取消按钮及其动作
    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    }).create()//创建对话框
      .show();//显示对话框

    }

    //查找对话框
    private void SearchDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater()
                .inflate(R.layout.searchterm, null);
        new AlertDialog.Builder(this)
                .setTitle("查找单词")//标题
                .setView(tableLayout)//设置视图
                //确定按钮及其动作
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String txtSearchWord = ((EditText) tableLayout.findViewById(R.id.searchWord))
                                .getText().toString();

                        //单词已经插入到数据库，更新显示列表
                        RefreshWordItemFragment(txtSearchWord);
                    }
                })
                //取消按钮及其动作
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .create()//创建对话框
                .show();//显示对话框
    }

    /**
     * 更新单词列表
     */
    private void RefreshWordItemFragment() {
        WordListFragment wordItemFragment = (WordListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.wordslist);
        wordItemFragment.refreshWordsList();
    }

    /**
     * 更新单词列表
     */
    private void RefreshWordItemFragment(String strWord) {
        WordListFragment wordItemFragment = (WordListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.wordslist);
        wordItemFragment.refreshWordsList(strWord);

    }



}

