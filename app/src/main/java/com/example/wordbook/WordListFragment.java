package com.example.wordbook;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import com.example.wordbook.dummy.Words;

import java.util.ArrayList;
import java.util.Map;

public class WordListFragment extends ListFragment {
    private OnFragmentInteractionListener mListener;

    @Override
    public void onAttach( Context context) {
        super.onAttach(context);
        mListener = (OnFragmentInteractionListener)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener{
        public void onWordItemClick(String id);
        public void onDeleteDialog(String strID);
        public void onUpdateDialog(String strID);
    }

    public void refreshWordsList(){
        OperationDB operationDB = OperationDB.getOperations();
        if(operationDB != null){
            ArrayList<Map<String,String>> items = operationDB.getAllWord();
            System.out.println("dsl"+items.size());
//            for(int i=0; i<items.size(); i++){
//                System.out.println("dsl"+items.get(i));
//            }
//疑难
            SimpleAdapter adapter = new SimpleAdapter(getActivity(),items,R.layout.item,
                    new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD},
                    new int[]{R.id.textId, R.id.textViewWord});
            setListAdapter(adapter);
        }else{
            Toast.makeText(getActivity(),"Not found",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //刷新单词列表
        refreshWordsList();
    }


    //更新单词列表，从数据库中找到同strWord向匹配的单词，然后在列表中显示出来
    public void refreshWordsList(String strWord) {
        OperationDB operationDB = OperationDB.getOperations();
        if (operationDB != null) {
            ArrayList<Map<String, String>> items = operationDB.SearchUseSql(strWord);
            if(items.size()>0){

                SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.item,
                        new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD},
                        new int[]{R.id.textId, R.id.textViewWord});

                setListAdapter(adapter);
            }else{
                Toast.makeText(getActivity(),"Not found",Toast.LENGTH_LONG).show();
            }
        }

    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
       if (null != mListener) {
           //通知Fragment所在的Activity，用户单击了列表的position项
           TextView textView = (TextView) v.findViewById(R.id.textId);
           if (textView != null) {
               //将单词ID传过去
               mListener.onWordItemClick(textView.getText().toString());
           }
       }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
    //为列表注册上下文菜单
        ListView mListView = (ListView) view.findViewById(android.R.id.list);
        mListView.setOnCreateContextMenuListener(this);
        registerForContextMenu(mListView);
        return view;
    }
        @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            getActivity().getMenuInflater().inflate(R.menu.contextmenu_wordslistview, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        TextView textId = null;
        TextView textWord = null;
        TextView textMeaning = null;
        TextView textSample = null;
        AdapterView.AdapterContextMenuInfo info = null;
        View itemView = null;
        switch (item.getItemId()) {
            case R.id.delete:
                  //删除单词
                   info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                   itemView = info.targetView;
                   textId = (TextView) itemView.findViewById(R.id.textId);
                   if (textId != null) {
                       String strId = textId.getText().toString();
                       mListener.onDeleteDialog(strId);
                   }
                   break;
                   case R.id.update:
                       //修改单词
                       info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                       itemView = info.targetView;
                       textId = (TextView) itemView.findViewById(R.id.textId);
                       if (textId != null) {
                           String strId = textId.getText().toString();
                           mListener.onUpdateDialog(strId);
                       }
                       break;
        }    return true;
    }



    }
