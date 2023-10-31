package com.example.sarifle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Tab1Fragment extends Fragment {
    private TextView textView;

    private String tab1Text;

    public Tab1Fragment(String tab1Text) {
        this.tab1Text = tab1Text;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        textView = view.findViewById(R.id.textView);

        if (tab1Text != null) {
                textView.setText(tab1Text);
            }
        return view;
    }

    public void updateText(String text) {
        if (textView != null) {
            textView.setText(text);
        }
    }

    // Rest of the class implementation...
}
