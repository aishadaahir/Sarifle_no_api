package com.example.sarifle;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;

import com.google.android.material.textfield.TextInputEditText;

public class ActivitySend extends AppCompatActivity {

    ConstraintLayout topcon,conlay2,layout;
    TextInputEditText money;
    Button save;
    ImageButton Button;
    TextView text1,text2,text3;
    Guideline vCenter;
    LinearLayout LinearLayout;
    public static String code = "";
    public static String lacag = "";
    public static String send = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        money = findViewById(R.id.money);
        save = findViewById(R.id.save);
        Button = findViewById(R.id.tton33);
        text1 = findViewById(R.id.textSarifle);
        text2 = findViewById(R.id.textaccount);
        text3 = findViewById(R.id.senttext);
        topcon = findViewById(R.id.topcon2);
        conlay2 = findViewById(R.id.conlay2);
        LinearLayout = findViewById(R.id.LinearLayout);
        layout = findViewById(R.id.constraintLayout);
        vCenter = findViewById(R.id.guidelineVerticalCenter);
        text1.setText(this.getIntent().getExtras().getString("Name"));
        text2.setText(this.getIntent().getExtras().getString("Account"));
        code = this.getIntent().getExtras().getString("code");
        text3.setText("*"+code+"*"+text2.getText()+"*money#");


        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ORIENTATION_LANDSCAPE();
        } else {
            ORIENTATION_PORTRAIT();
        }

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), home.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }

        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                View view = getCurrentFocus();
                if (view != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                lacag = money.getText().toString();
                if(lacag.equals("")){
                    Toast.makeText(ActivitySend.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
                else{
                    send ="*"+code+"*"+text2.getText()+"*"+lacag+"#";
                    String encodedUssdCode = Uri.encode(send);
                    String ussdUri = "tel:" + encodedUssdCode;
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(ussdUri));
                    startActivity(intent);
                }

            }

        });
    }
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), home.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
    public void ORIENTATION_PORTRAIT() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(topcon);

        constraintSet.connect(conlay2.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.BOTTOM, 0);
        constraintSet.connect(conlay2.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        constraintSet.clear(conlay2.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(conlay2.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);

        constraintSet.connect(LinearLayout.getId(), ConstraintSet.TOP, conlay2.getId(), ConstraintSet.BOTTOM, 0);
        constraintSet.connect(LinearLayout.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        constraintSet.clear(LinearLayout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(LinearLayout.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);

        constraintSet.applyTo(topcon);
    }

    public void ORIENTATION_LANDSCAPE() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(topcon);

        constraintSet.connect(conlay2.getId(), ConstraintSet.TOP, vCenter.getId(), ConstraintSet.BOTTOM, 0);
        constraintSet.connect(conlay2.getId(), ConstraintSet.END, vCenter.getId(), ConstraintSet.END, 0);
        constraintSet.connect(conlay2.getId(), ConstraintSet.BOTTOM, vCenter.getId(), ConstraintSet.TOP, 0);
        constraintSet.connect(conlay2.getId(), ConstraintSet.START, vCenter.getId(), ConstraintSet.START, 0);

        constraintSet.connect(LinearLayout.getId(), ConstraintSet.TOP, conlay2.getId(), ConstraintSet.BOTTOM, 0);
        constraintSet.connect(LinearLayout.getId(), ConstraintSet.END, vCenter.getId(), ConstraintSet.END, 0);
        constraintSet.connect(LinearLayout.getId(), ConstraintSet.BOTTOM, vCenter.getId(), ConstraintSet.TOP, 0);
        constraintSet.connect(LinearLayout.getId(), ConstraintSet.START, vCenter.getId(), ConstraintSet.START, 0);


        constraintSet.applyTo(topcon);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Update the UI based on the new configuration
        updateUIForOrientation(newConfig.orientation);
    }
    private void updateUIForOrientation(int orientation) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ORIENTATION_LANDSCAPE();

        }
        else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            ORIENTATION_PORTRAIT();

        }
    }
}