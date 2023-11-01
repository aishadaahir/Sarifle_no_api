package com.example.sarifle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sarifle.database.DatabaseHelper2;
import com.example.sarifle.database.DatabaseHelper3;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class home extends AppCompatActivity {

//    final View androidRobotView = findViewById(R.id.view);
    public static String data = "";
    Button shelling,dollar;
    ImageButton Button,call,message,imageButton;
    ConstraintLayout layout,layout2,layout3,layout4,topcon;
//    ScrollView scroll;
    ConstraintLayout scroll;
    TextView text1,text2,ph1,ph2,empty;
    View v1,v2;
    DatabaseHelper2 myDB2;
    DatabaseHelper3 myDB3;
    ArrayList<String> Id,Name,Sarifle,Tell,Tell2,Accountnum,Datereg;

    RecyclerView recview;

    Adapter2 Adapter;
    Guideline vCenter,hCenter,hCenter2,hCenter3;
    public static int profile;
    public static int share;
    String myString;

    private static String lang;
    private static String Activity;
    private boolean isLanguageChanged = false;
    private int colorResource;

    @Override
    protected void attachBaseContext(Context newBase) {
        // Perform your action here before onCreate
        myDB3 = new DatabaseHelper3(newBase);
        Cursor cursor = myDB3.read2();
        if(cursor.getCount() == 0){
            lang="";

        }else{
            while (cursor.moveToNext()){
                lang = cursor.getString(0);
            }
        }
        if(Objects.equals(lang, "English")){
            lang="";
        }
        if(Objects.equals(lang, "")){
            lang="";
        }
        if (Objects.equals(lang, "Somali")) {
            lang="So";
        }
        switchLanguage(lang,newBase);
        super.attachBaseContext(newBase);
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button = findViewById(R.id.tton33);
        shelling = findViewById(R.id.shelling);
        dollar = findViewById(R.id.dollar);
        call = findViewById(R.id.call);
        message = findViewById(R.id.message);
        layout = findViewById(R.id.constraintLayout);
        layout3 = findViewById(R.id.constraintLayout2);
        layout4 = findViewById(R.id.constraintLayouts3);
        topcon = findViewById(R.id.topcon);
        layout2 = findViewById(R.id.conlay2);
        scroll = findViewById(R.id.scroll);
        recview = findViewById(R.id.resiew);
        text1 = findViewById(R.id.textSarifle);
        text2 = findViewById(R.id.textaccount);
        empty = findViewById(R.id.empty);
        ph1 = findViewById(R.id.textaccount2);
        ph2 = findViewById(R.id.textaccount3);
        vCenter = findViewById(R.id.guidelineVerticalCenter);
        hCenter = findViewById(R.id.guidelineHorizontalCenter);
        hCenter2 = findViewById(R.id.guidelineHorizontalCenter2);
        hCenter3 = findViewById(R.id.guidelineHorizontalCenter3);
        ph1.setVisibility(View.GONE);
        ph2.setVisibility(View.GONE);
        shelling.setVisibility(View.GONE);
        dollar.setVisibility(View.GONE);
        imageButton = findViewById(R.id.imageButton);
        v1 = findViewById(R.id.view1);
        v2 = findViewById(R.id.view2);

        colorResource = getResources().getColor(R.color.light_teal);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ORIENTATION_LANDSCAPE();
        } else {
            ORIENTATION_PORTRAIT();
        }

        Animation buttonScaleAnimation = AnimationUtils.loadAnimation(this, R.anim.button_scale);
        buttonScaleAnimation.setDuration(1000);

        buttonScaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                call.setVisibility(View.GONE);
                message.setVisibility(View.GONE);
                layout2.setVisibility(View.GONE);
                layout3.setVisibility(View.GONE);
                layout4.setVisibility(View.GONE);
                scroll.setVisibility(View.GONE);
                recview.setVisibility(View.GONE);
                imageButton.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layout.setVisibility(View.GONE);

                Intent intent = new Intent(home.this, MainActivity4.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button.setVisibility(View.GONE);
                layout.startAnimation(buttonScaleAnimation);
            }

        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageApp();
            }

        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApp();
            }

        });
        shelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, ActivitySend.class);
                intent.putExtra("Name",text1.getText() );
                intent.putExtra("Account",text2.getText() );
                intent.putExtra("code","277" );
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }

        });
        dollar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, ActivitySend.class);
                intent.putExtra("Name",text1.getText() );
                intent.putExtra("Account",text2.getText() );
                intent.putExtra("code","377" );
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }

        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(home.this, imageButton);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        int itemId = menuItem.getItemId();
                        if (itemId == R.id.profile) {
                            startActivity(new Intent(getApplicationContext(), register.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();
                            return true;
                        } else if (itemId == R.id.share) {
                            showShareMenu();
                            return true;
                        }
                        // Add conditions for other menu items as needed
                        return false;

                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }

        });

        myDB2 = new DatabaseHelper2(home.this);
        myDB3 = new DatabaseHelper3(home.this);
        Id = new ArrayList<>();
        Name = new ArrayList<>();
        Sarifle = new ArrayList<>();
        Tell = new ArrayList<>();
        Tell2 = new ArrayList<>();
        Accountnum = new ArrayList<>();
        Datereg = new ArrayList<>();
        storeDataInArrays();
        getData();

    }


    private void getData() {
        Cursor cursor = myDB3.read();
        if(cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                data = cursor.getString(0);
            }

        }
        filltxt(data);


        Adapter = new Adapter2(home.this,this, Id,Name,Sarifle,Tell,Tell2,Accountnum,Datereg,data,myDB3,myDB2,colorResource
//                ,new Adapter2.ItemClicklistner() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onItem(int position,Intent intent) {
//
//                String id = intent.getExtras().getString("ID");
//                myDB2.deleteOneRow(id);
//
//                myDB2.deleteOneRow(id);
//
////                recyclerView.setAdapter(null);
//                Toast.makeText(home.this, "Sarifle removed from list", Toast.LENGTH_SHORT).show();
//                myDB2 = new DatabaseHelper2(home.this);
//                Id = new ArrayList<>();
//                Name = new ArrayList<>();
//                Sarifle = new ArrayList<>();
//                Tell = new ArrayList<>();
//                Tell2 = new ArrayList<>();
//                Accountnum = new ArrayList<>();
//                Datereg = new ArrayList<>();
//                Adapter.notifyDataSetChanged();
//                storeDataInArrays();
//                getData();
//                filltxt(data);
//
//
//            }
//
//        }
        ,new Adapter2.ItemClicklistner2() {
            @Override
            public void onItem(int position,Intent intent,String data) {
                text1.setText(intent.getExtras().getString("Sarifle"));
                text2.setText(intent.getExtras().getString("Accountnum"));
                ph1.setText(intent.getExtras().getString("Tell"));
                ph2.setText(intent.getExtras().getString("Tell2"));
                shelling.setVisibility(View.VISIBLE);
                dollar.setVisibility(View.VISIBLE);
                String sarifle=intent.getExtras().getString("Sarifle");
                myDB3.updateclo4(sarifle);


            }

        });
        recview.setAdapter(Adapter);
        recview.setLayoutManager(new LinearLayoutManager(home.this));
    }

    private void filltxt(String data){
        Cursor cursor2 = myDB2.readsarifle(data);
        if(cursor2.getCount() == 0){
            text1.setText(getResources().getString(R.string.emptytext));
            text2.setText("");
            ph1.setText("");
            ph2.setText("");
            shelling.setVisibility(View.GONE);
            dollar.setVisibility(View.GONE);
        }else{
            while (cursor2.moveToNext()){
                text1.setText(cursor2.getString(0));
                text2.setText(cursor2.getString(1));
                ph1.setText(cursor2.getString(2));
                ph2.setText(cursor2.getString(3));
                shelling.setVisibility(View.VISIBLE);
                dollar.setVisibility(View.VISIBLE);
            }

        }

    }



    void storeDataInArrays(){
        Cursor cursor = myDB2.readAllData();
        if(cursor.getCount() == 0){
            SQLiteDatabase db = myDB2.getWritableDatabase();
            myDB2.insertInitialData(db);
            db.close();
            Cursor cursor2 = myDB2.readAllData();
            if(cursor2.getCount() == 0){

            }else{
                empty.setVisibility(View.GONE);
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.VISIBLE);
                while (cursor2.moveToNext()){
                    Id.add(cursor2.getString(1));
                    Name.add(cursor2.getString(2));
                    Sarifle.add(cursor2.getString(3));
                    Tell.add(cursor2.getString(4));
                    Tell2.add(cursor2.getString(5));
                    Accountnum.add(cursor2.getString(6));

                }

            }

        }else{
            empty.setVisibility(View.GONE);
            v1.setVisibility(View.VISIBLE);
            v2.setVisibility(View.VISIBLE);
            while (cursor.moveToNext()){
                Id.add(cursor.getString(1));
                Name.add(cursor.getString(2));
                Sarifle.add(cursor.getString(3));
                Tell.add(cursor.getString(4));
                Tell2.add(cursor.getString(5));
                Accountnum.add(cursor.getString(6));

            }

        }


    }

    private void callApp2(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + "619993034"));
        startActivity(intent);
    }
    private void callApp(){
        if(ph1.getText()==""&&ph2.getText()==""){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(getResources().getString(R.string.head))
                    .setMessage(getResources().getString(R.string.emptytext))
                    .show();
        }
        else{
            String t1= ph1.getText().toString().trim();
            String t2= ph2.getText().toString().trim();
            CharSequence[] items = new CharSequence[] {
                    t1,t2
            };
            System.out.println(items[0]);
            System.out.println(items[1]);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(getResources().getString(R.string.head))
                    .setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String number= (String) items[which];
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + number));
                            startActivity(intent);

                        }
                    })
                    .show();
        }

    }
    private void messageApp() {
        if(ph1.getText()==""&&ph2.getText()==""){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(getResources().getString(R.string.head))
                    .setMessage(getResources().getString(R.string.emptytext))
                    .show();
        }
        else{
            String t1= ph1.getText().toString().trim();
            String t2= ph2.getText().toString().trim();
            CharSequence[] items = new CharSequence[] {
                    t1,t2
            };
            System.out.println(items[0]);
            System.out.println(items[1]);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(getResources().getString(R.string.head))
                    .setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String phoneNumber = (String) items[which];
                            String shareBody = "Asc " + text1.getText();
                            Uri uri = Uri.parse("smsto:" + phoneNumber);
                            Intent sharingIntent = new Intent(Intent.ACTION_SENDTO, uri);
                            sharingIntent.setPackage("com.whatsapp");
//                            sharingIntent.putExtra("sms_body", shareBody);
                            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                            int requestCode = 123; // Choose your own request code

                            try {
                                startActivityForResult(sharingIntent, requestCode);
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(home.this, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    })
                    .show();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) { // Use the same request code as before
            // Perform any necessary actions after sharing
        }
    }

    public void showShareMenu() {
        String message = "share app";

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);

        Intent chooserIntent = Intent.createChooser(shareIntent, "Share app");

        // Verify if there are apps available to handle the intent
        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooserIntent);
        } else {
            Toast.makeText(this, "No app found to handle this action", Toast.LENGTH_SHORT).show();
        }
    }

    private void switchLanguage(String lang, Context context) {

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

    }

    public void onBackPressed() {
        //do nothing
        super.onBackPressed();
    }
    public void ORIENTATION_PORTRAIT() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(topcon);

        constraintSet.connect(scroll.getId(), ConstraintSet.TOP, layout2.getId(), ConstraintSet.BOTTOM, 8);
        constraintSet.connect(scroll.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        constraintSet.connect(scroll.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(scroll.getId(), ConstraintSet.BOTTOM, hCenter2.getId(), ConstraintSet.TOP, 24);

        constraintSet.connect(layout3.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 12);
        constraintSet.connect(layout3.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 11);

        constraintSet.connect(imageButton.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        constraintSet.connect(imageButton.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);


        constraintSet.connect(layout2.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        constraintSet.connect(layout2.getId(), ConstraintSet.TOP, hCenter3.getId(), ConstraintSet.BOTTOM, 13);
        constraintSet.connect(layout2.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(layout2.getId(), ConstraintSet.BOTTOM, scroll.getId(), ConstraintSet.TOP, 8);

        constraintSet.applyTo(topcon);
    }

    public void ORIENTATION_LANDSCAPE() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(topcon);

        constraintSet.connect(layout2.getId(), ConstraintSet.END, hCenter.getId(), ConstraintSet.END, 0);
        constraintSet.connect(layout2.getId(), ConstraintSet.TOP, hCenter.getId(), ConstraintSet.TOP, 13);
        constraintSet.connect(layout2.getId(), ConstraintSet.START, hCenter.getId(), ConstraintSet.START, 0);
        constraintSet.connect(layout2.getId(), ConstraintSet.BOTTOM, hCenter.getId(), ConstraintSet.BOTTOM, 13);


        constraintSet.clear(scroll.getId(), ConstraintSet.TOP);
        constraintSet.connect(scroll.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        constraintSet.clear(scroll.getId(), ConstraintSet.BOTTOM);
        constraintSet.clear(scroll.getId(), ConstraintSet.START);

        constraintSet.connect(layout4.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 12);

        constraintSet.connect(layout3.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 12);
        constraintSet.connect(layout3.getId(), ConstraintSet.END, scroll.getId(), ConstraintSet.START, 11);


        constraintSet.connect(imageButton.getId(), ConstraintSet.END, scroll.getId(), ConstraintSet.START, 0);

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