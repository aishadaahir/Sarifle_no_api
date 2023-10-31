package com.example.sarifle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.sarifle.database.DatabaseHelper3;
import com.scwang.wave.MultiWaveHeader;

import java.util.Locale;
import java.util.Objects;

public class splash_screen extends AppCompatActivity {
    MultiWaveHeader multiWaveHeader;
    DatabaseHelper3 myDB3;
    private static String lang;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        multiWaveHeader = findViewById(R.id.multiWaveHeader);
        // Assuming you have a MultiWaveHeader instance named 'multiWaveHeader'

        int totalTime = 5 * 1000; // 10 seconds in milliseconds
        long interval = 1000; // 1 second interval
        float increment = 0.1f;


        new CountDownTimer(totalTime, interval) {
            double progress = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                // Increment the progress value
                progress=progress+0.15;
                // Set the progress value of the MultiWaveHeader
                multiWaveHeader.setProgress((float) progress);
            }

            @Override
            public void onFinish() {
                SecondTimer();
                // Timer finished, perform any necessary actions
            }
        }.start();
    }

    private void switchLanguage(String lang, Context context) {

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

    }

    private void SecondTimer() {
        // Set the values for the second timer
        int secondTotalTime = 1 * 1000; // 10 seconds in milliseconds
        long secondInterval = secondTotalTime / 100; // Interval to reach 100 progress in 100 steps

        CountDownTimer secondTimer = new CountDownTimer(secondTotalTime, secondInterval) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                finish();
                // Second timer finished, perform any necessary actions
            }
        };

        // Start the second timer
        secondTimer.start();
    }
}
