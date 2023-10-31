package com.example.sarifle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Tab2Fragment extends Fragment {

    private TextView textView;

    public Tab2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        textView = view.findViewById(R.id.textView);
        return view;
    }

    public void updateText(String newText) {
        if (textView != null) {
            textView.setText(newText);
        }
    }
}
