package com.example.skincancerdetectiion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class splashScreen extends AppCompatActivity {
    Boolean doubleBackToExitPressedOnce= false;
    ImageView img;
    TextView tv, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        img = findViewById(R.id.myView);
        tv2 = (TextView) findViewById(R.id.tv2);
        Animation topanimation = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        img.startAnimation(topanimation);
        setSplash();
        //7B0BAC
        //FF7851


    }

    public void setSplash() {
        new Handler().postDelayed(() -> {
            Intent i = new Intent(splashScreen.this, TermsActivity.class);
            startActivity(i);
        }, 5000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSplash();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            exitApp();
            return;
        }
        doublebackpressed();
    }

    public void doublebackpressed() {

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
    public void exitApp() {
        finishAffinity(); // Finishes all activities in the task
    }

}