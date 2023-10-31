package com.example.sarifle;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.sarifle.database.DatabaseHelper3;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class register extends AppCompatActivity {

    TextInputEditText name,phone;
    RadioGroup radioGroup;
    RadioButton radioButton1,radioButton2;
    Button save;
    ImageView imgView,imgViewEdit;

    ImageButton Button;
    ConstraintLayout topcon3;
    LinearLayout layimg,Layout;
    DatabaseHelper3 myDB3;
    private static String sarifle;
    String encodedImage1,encodedImage2;
    Uri uri1;
    Bitmap bitmap1;
    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        save = findViewById(R.id.save);
        Button = findViewById(R.id.tton33);
        imgView=findViewById(R.id.imgViewprofile);
        imgViewEdit=findViewById(R.id.imgViewEdit);
        topcon3=findViewById(R.id.topcon3);
        layimg=findViewById(R.id.layimg);
        Layout=findViewById(R.id.linearLayout);
        myDB3 = new DatabaseHelper3(register.this);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ORIENTATION_LANDSCAPE();
        } else {
            ORIENTATION_PORTRAIT();
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB3.delete();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                String names = name.getText().toString();
                String phones = phone.getText().toString();
                String selectedOption = selectedRadioButton.getText().toString();
                myDB3.addData(names,phones,selectedOption,sarifle,encodedImage1);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                View view = getCurrentFocus();
                if (view != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                Toast.makeText(register.this, getResources().getString(R.string.save), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), home.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }

        });
        storeDataInArrays();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            }
        });

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), home.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }

        });

        imgViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

            }
        });

        if(Objects.equals(encodedImage2, "placeholder_image_path")){
            imgView.setImageResource(R.drawable.iconuser);
        }
        else if(Objects.equals(encodedImage2, "")) {
            imgView.setImageResource(R.drawable.iconuser);
        }
        else
        {
            imgView.setImageBitmap(parseStringToBitmap(encodedImage2));
        }


    }

    public Bitmap parseStringToBitmap(String encodedString) {
        try {
            byte[] decodedBytes = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            uri1 = data.getData();
            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri1);
                imgView.setImageBitmap(bitmap1);
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1);
                encodedImage1 = Base64.encodeToString(byteArrayOutputStream1.toByteArray(), Base64.DEFAULT);
            } catch (Exception e) {

            }
        }


    }

    void storeDataInArrays(){
        Cursor cursor = myDB3.readAllData();
        if(cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                name.setText(cursor.getString(0));
                phone.setText(cursor.getString(1));
                String check=cursor.getString(2);
                RadioButton radioButton;
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    radioButton = (RadioButton) radioGroup.getChildAt(i);
                    if (radioButton.getText().toString().equals(check)) {
                        radioButton.setChecked(true);
                        break;
                    }
                }
                sarifle = cursor.getString(3);
                encodedImage2 = cursor.getString(4);
            }

        }

    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), home.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
    public void ORIENTATION_PORTRAIT() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(topcon3);

        constraintSet.connect(layimg.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        constraintSet.connect(layimg.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 118);
        constraintSet.connect(layimg.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(layimg.getId(), ConstraintSet.BOTTOM, Layout.getId(), ConstraintSet.TOP, 29);


        constraintSet.applyTo(topcon3);
    }

    public void ORIENTATION_LANDSCAPE() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(topcon3);

        constraintSet.clear(layimg.getId(), ConstraintSet.END);
        constraintSet.connect(layimg.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        constraintSet.connect(layimg.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(layimg.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);


        constraintSet.applyTo(topcon3);
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