package com.sheild.abhinavchinta.csed;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhinavchinta.csed.R;


public class BlankFragment extends Fragment {

    private TextView textView;
    public BlankFragment() {
        // Required empty public constructor

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        textView = (TextView)getView().findViewById(R.id.text);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

}
