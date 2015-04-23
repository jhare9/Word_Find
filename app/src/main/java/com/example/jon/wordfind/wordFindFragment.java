package com.example.jon.wordfind;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jon on 3/17/2015.
 */
public class wordFindFragment extends Fragment {

    private WordFindView word_find_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_find_fragment,container,false);
        word_find_view = (WordFindView) view.findViewById(R.id.word_find_view);
        return view;
    }

    public  WordFindView getWordFindView(){
            return word_find_view;
    }

}
