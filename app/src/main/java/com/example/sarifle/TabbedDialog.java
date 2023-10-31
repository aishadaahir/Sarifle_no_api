package com.example.sarifle;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class TabbedDialog extends DialogFragment implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyPagerAdapter pagerAdapter;

    private String tab1Text;
    private String tab2Text;
    private String btn1;
    private String btn2;
    private boolean isFirstRun = true;
    Button button6,button7;
    private DialogCallback dialogCallback;

    public TabbedDialog(String tab1Text, String tab2Text,String btn1, String btn2,DialogCallback callback) {
        this.tab1Text = tab1Text;
        this.tab2Text = tab2Text;
        this.btn1 = btn1;
        this.btn2 = btn2;
        this.dialogCallback = callback;
    }
    public interface DialogCallback {
        void onOk();
        void onCancel();
//        void onCancel2();
        void onSkip();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setContentView(R.layout.dialog_sample);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        tabLayout = getDialog().findViewById(R.id.tabLayout);
        viewPager = getDialog().findViewById(R.id.viewPager);
        button6 = getDialog().findViewById(R.id.button6);
        button7 = getDialog().findViewById(R.id.button7);
        button6.setText(btn1);
        button7.setText(btn2);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogCallback != null) {
                    if(btn2=="Skip"){
                        dialogCallback.onSkip();
                    }
                    else {
                        dialogCallback.onOk();
                    }

                }
                dismiss();
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogCallback != null) {
                    dialogCallback.onCancel();
                }
                dismiss();
            }
        });

        // Set up the ViewPager with a custom adapter
        pagerAdapter = new MyPagerAdapter(getChildFragmentManager(),tab1Text);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.setTabTextColors(Color.BLACK, Color.WHITE);


    }




    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        tabLayout.setTabTextColors(Color.BLACK, Color.WHITE);
        int position = tab.getPosition();
        if (position == 0) {
            // Get the reference to the Tab1Fragment from the adapter
            Tab1Fragment tab1Fragment = (Tab1Fragment) pagerAdapter.getFragment(position);
            if (tab1Fragment != null) {
                // Call a method in Tab1Fragment to change the text
                tab1Fragment.updateText(tab1Text);
            }
        }
        if (position == 1) {
            // Get the reference to the Tab1Fragment from the adapter
            Tab2Fragment tab2Fragment = (Tab2Fragment) pagerAdapter.getFragment(position);
            if (tab2Fragment != null) {
                // Call a method in Tab1Fragment to change the text
                tab2Fragment.updateText(tab2Text);
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        // Optional: Implement if needed
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        // Optional: Implement if needed
    }

    private static class MyPagerAdapter extends FragmentPagerAdapter {

        private static final int NUM_TABS = 2;
        private final androidx.fragment.app.Fragment[] fragments = new androidx.fragment.app.Fragment[NUM_TABS];
        private String tab1Text; // Add a variable to store the text for Tab1Fragment

        // ...

        public MyPagerAdapter(FragmentManager fragmentManager,String tab1Text) {
            super(fragmentManager);
            this.tab1Text=tab1Text;
        }

        // ...

        @Override
        public androidx.fragment.app.Fragment getItem(int position) {
            // Return the appropriate fragment for each tab position
            switch (position) {
                case 0:
                    fragments[position] = new Tab1Fragment(tab1Text);
//                    updateTab1Text("String text");
                    return fragments[position];
                case 1:
                    fragments[position] = new Tab2Fragment();
                    return fragments[position];
                default:
                    return null;
            }
        }



        @Override
        public CharSequence getPageTitle(int position) {
            // Return the title for each tab
            switch (position) {
                case 0:
                    return "English";
                case 1:
                    return "Somali";
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_TABS;
        }
        public androidx.fragment.app.Fragment getFragment(int position) {
            return fragments[position];
        }

    }
}