package com.toufikhasan.massagebook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.massagebook.R;

public class WellCome extends AppCompatActivity {

    private ProgressBar progressBar;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_come);
        // Full Image View
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        // Font Family Setup
        TextView text1 = findViewById(R.id.welcomeName);
        TextView text2 = findViewById(R.id.writerName);
        TextView text3 = findViewById(R.id.ukktiName);
        Typeface bangleFont = Typeface.createFromAsset(getAssets(), "font/Bangla.ttf");
        Typeface massageFont = Typeface.createFromAsset(getAssets(), "font/massageFont.ttf");
        text1.setTypeface(massageFont);
        text2.setTypeface(massageFont);
        text3.setTypeface(bangleFont);



        // ProgressBar Animation
        progressBar = (ProgressBar) findViewById(R.id.progressId);

        Thread thread = new Thread(this::doWorking);
        thread.start();
    }
    public void doWorking(){
        int progress;
        for (progress = 0; progress <= 100; progress = progress + 1){
            try {
                Thread.sleep(20);
                progressBar.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        runApp();
    }
    public void runApp(){
        startActivity(new Intent(WellCome.this,MainActivity.class));
        finish();
    }

}