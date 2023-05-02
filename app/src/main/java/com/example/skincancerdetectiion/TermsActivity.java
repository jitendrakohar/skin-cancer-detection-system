package com.example.skincancerdetectiion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TermsActivity extends AppCompatActivity {
    private TextView termsText;
    private Button acceptButton;
    private Button rejectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        boolean termsAccepted = prefs.getBoolean(constants.TERMS_AND_CONDITION, false);

        if (termsAccepted) {
            // Launch the terms and conditions activity
            Intent intent = new Intent(TermsActivity.this, inputactivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_terms);
            // Rest of your code goes here


            termsText = findViewById(R.id.terms_text);
            acceptButton = findViewById(R.id.accept_button);
            rejectButton = findViewById(R.id.reject_button);

            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // User has accepted the terms and conditions, save this information in SharedPreferences
                    SharedPreferences.Editor editor = getSharedPreferences("myPrefs", MODE_PRIVATE).edit();
                    editor.putBoolean(constants.TERMS_AND_CONDITION, true);
                    editor.apply();

                    // Launch the main activity
                    Intent intent = new Intent(TermsActivity.this, inputactivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            rejectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // User has rejected the terms and conditions, close the app
                    finishAffinity();
                }
            });
        }




    }
}
