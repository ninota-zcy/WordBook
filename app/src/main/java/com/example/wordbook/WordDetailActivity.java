package com.example.wordbook;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class WordDetailActivity extends AppCompatActivity implements WordDetailFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }
//        if(savedInstanceState == null){
//            WordDetailFragment detailFragment = new WordDetailFragment();
//            detailFragment.setArguments(getIntent().getExtras());
//            getFragmentManager().beginTransaction().add(android.R.id.content, detailFragment).commit();
//
//        }
    }

    @Override
    public void OnWordDetailClick(Uri uri) {

    }
}
