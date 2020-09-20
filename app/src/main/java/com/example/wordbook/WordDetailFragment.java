package com.example.wordbook;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wordbook.dummy.Words;

public class WordDetailFragment extends Fragment {
    static final String ARG_ID = "id";
    String mID;
    OnFragmentInteractionListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnFragmentInteractionListener)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener{
        public void OnWordDetailClick(Uri uri);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mID = getArguments().getString(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_word_detail, container, false);
        OperationDB operationDB = OperationDB.getOperations();
        if(operationDB!=null && mID!=null){
            TextView textViewWord=(TextView)view.findViewById(R.id.word);
            TextView textViewWordMeaning=(TextView)view.findViewById(R.id.wordmeaning);
            TextView textViewWordSample=(TextView)view.findViewById(R.id.wordsample);
            Words.WordDescription item = operationDB.getSingleWord(mID);
            if(item!=null){
                textViewWord.setText(item.word);
                textViewWordMeaning.setText(item.meaning);
                textViewWordSample.setText(item.sample);
            }else{
                textViewWord.setText("");
                textViewWordMeaning.setText("");
                textViewWordSample.setText("");
            }
        }
        return view;
    }

}
