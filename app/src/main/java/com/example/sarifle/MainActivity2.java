package com.example.sarifle;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sarifle.database.DatabaseHelper3;
import com.google.android.material.textfield.TextInputEditText;
import com.scwang.wave.MultiWaveHeader;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity2 extends AppCompatActivity implements TabbedDialog.DialogCallback {

    private TabbedDialog tabbedDialog;
    private static final long SPLASH_DURATION = 8000;
    private static final int REQUEST_CALL_PHONE_PERMISSION = 1;
    private int permissionRequestCount = 0;
    private boolean count = false;
    DatabaseHelper3 myDB;
    View view;
    ConstraintLayout container,imageButton;
    TextInputEditText name,phone;
    RadioGroup radioGroup;
    RadioButton radioButton1,radioButton2;
    Button save;
    ImageView imgView,imgViewEdit;
    ConstraintLayout topcon4,topcon5;
    LinearLayout layimg,Layout;
    MultiWaveHeader multiWaveHeader;
    String encodedImage1="",encodedImage2;
    Uri uri1;
    Bitmap bitmap1;
    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        multiWaveHeader = findViewById(R.id.multiWaveHeader);
        LayoutInflater inflater = getLayoutInflater();
        view = inflater.inflate(R.layout.activity_register, null);
        container = findViewById(R.id.container);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioButton1 = view.findViewById(R.id.radioButton1);
        radioButton2 = view.findViewById(R.id.radioButton2);
        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        myDB = new DatabaseHelper3(MainActivity2.this);
        save = view.findViewById(R.id.save);
        imgView=view.findViewById(R.id.imgViewprofile);
        imgViewEdit=view.findViewById(R.id.imgViewEdit);
        topcon4=view.findViewById(R.id.topcon4);
        topcon5=findViewById(R.id.topcon5);
        layimg=view.findViewById(R.id.layimg);
        Layout=view.findViewById(R.id.linearLayout2);
        imageButton = findViewById(R.id.skip);
        imageButton.setVisibility(View.GONE);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ORIENTATION_LANDSCAPE();
        } else {
            ORIENTATION_PORTRAIT();
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                String names = name.getText().toString();
                String phones = phone.getText().toString();
                if(!names.equals("") && !phones.equals("") && !String.valueOf(selectedId).equals("-1")){
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    String selectedOption = selectedRadioButton.getText().toString();
                    if(encodedImage1.isEmpty()){
                        encodedImage1="placeholder_image_path";
                        Log.e("errre",encodedImage1);
                    }
                    else if (encodedImage1=="") {
                        encodedImage1="placeholder_image_path";
                        Log.e("errre",encodedImage1);
                    }
                    myDB.delete();
                    myDB.addData(names,phones,selectedOption,"",encodedImage1);
                    Toast.makeText(MainActivity2.this, getResources().getString(R.string.save), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), home.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity2.this, getResources().getString(R.string.makesure), Toast.LENGTH_SHORT).show();
                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                View view = getCurrentFocus();
                if (view != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }

        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count=true;
                TabbedDialog tabbedDialog = new TabbedDialog(
                        getResources().getString(R.string.skip1),
                        getResources().getString(R.string.skip2),
                        "Cancel",
                        "Skip",
                        MainActivity2.this
                );

                // Show the TabbedDialog
                FragmentManager fragmentManager = getSupportFragmentManager();
                tabbedDialog.show(fragmentManager, "tabbed_dialog");

            }

        });


        requestCallPhonePermission();

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

    private void checkprof(){
        Cursor cursor2 = myDB.readAllData();
        if(cursor2.getCount() == 0){
            SQLiteDatabase db = myDB.getWritableDatabase();
            // Call onUpgrade to recreate the table
            myDB.onUpgrade(db, 0, 0);
            // Close the database
            db.close();
            container.addView(view);
            imageButton.setVisibility(View.VISIBLE);

        }else{
            startActivity(new Intent(getApplicationContext(), home.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }

    }



    private void requestCallPhonePermission() {
        String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE};
        List<String> permissionList = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        if (!permissionList.isEmpty()) {
            String[] permissionsToRequest = permissionList.toArray(new String[0]);
            ActivityCompat.requestPermissions(this, permissionsToRequest, REQUEST_CALL_PHONE_PERMISSION);
        } else {
            checkprof();

        }
    }

    private void showPermissionRationaleDialog() {

        TabbedDialog tabbedDialog = new TabbedDialog(
                getResources().getString(R.string.permition1),
                getResources().getString(R.string.permition2),
                "Cancel",
                "Ok",
                MainActivity2.this
        );

        // Show the TabbedDialog
        FragmentManager fragmentManager = getSupportFragmentManager();
        tabbedDialog.show(fragmentManager, "tabbed_dialog");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PHONE_PERMISSION) {

            if (grantResults.length > 0) {
                boolean allPermissionsGranted = true;
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        allPermissionsGranted = false;
                        break;
                    }
                }

                if (allPermissionsGranted) {
                    checkprof();
                    // Both permissions are granted
                    // Proceed with your logic here
                } else {

                    permissionRequestCount++;


                    if (permissionRequestCount < 2) {
                        // Request the permission again
                        showPermissionRationaleDialog();
//                        requestCallPhonePermission();

                    } else {
                        // Show a message to the user and exit the app
                        Toast.makeText(this, "Permission denied. Unable to proceed with this app. uninstall then install again", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    // Permission denied
                    // Handle the scenario where one or both permissions are not granted
                }
            }
        }
    }

    @Override
    public void onOk() {
        requestCallPhonePermission();

    }
    @Override
    public void onCancel() {
        if (count) {
            onCancel2();
            count=false;
        } else {
            finish();
        }

    }

    @Override
    public void onSkip() {
        myDB.addData("", "", "", "", "placeholder_image_path");
        startActivity(new Intent(getApplicationContext(), home.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    public void onCancel2() {
        if (tabbedDialog != null) {
            tabbedDialog.dismiss();
        }

    }

    public void ORIENTATION_PORTRAIT() {
        layimg.setVisibility(View.VISIBLE);
    }

    public void ORIENTATION_LANDSCAPE() {
        layimg.setVisibility(View.GONE);
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