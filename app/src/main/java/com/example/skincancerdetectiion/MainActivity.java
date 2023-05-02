package com.example.skincancerdetectiion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    TextView name, mail;
    private boolean doubleBackToExitPressedOnce = false;
    ImageView headerImage;
    preferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user has accepted the terms and conditions
        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        boolean termsAccepted = prefs.getBoolean(constants.TERMS_AND_CONDITION, false);

        if (!termsAccepted) {
            // Launch the terms and conditions activity
            Intent intent = new Intent(MainActivity.this, TermsActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_main);
            // Rest of your code goes here
        }
        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_home()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        View headerView = navigationView.getHeaderView(0);
        preferenceManager = new preferenceManager(this);
        name = headerView.findViewById(R.id.headerName);
        mail = headerView.findViewById(R.id.headerGmail);
        headerImage = headerView.findViewById(R.id.headerImage);

        headerImage.setImageResource(R.drawable.me);
        int widthpx = getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._40sdp);
        int heightpx = getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._40sdp);
// Create a new LayoutParams object with the desired width and height
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(widthpx, heightpx);
        headerImage.setLayoutParams(layoutParams);


        name.setText(preferenceManager.getString(constants.NAME, ""));
        mail.setText(preferenceManager.getString(constants.GMAIL, ""));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_home()).commit();
                break;
            case R.id.nav_settings:
                     break;
            case R.id.nav_share:
                shareApp();
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_share()).commit();
                break;
            case R.id.nav_report:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new reportfragment()).commit();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_about()).commit();
                break;
            case R.id.nav_logout:
                logout();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                exitApp();
                return;
            }
            doublebackpressed();
        }
    }


    public void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareSubject = "Check out this app!";
        String shareMessage = "I found this great app and wanted to share it with you: " +
                "https://play.google.com/store/apps/details?id=" + getPackageName();
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "Share using"));
    }

    public void logout() {
        preferenceManager.logout();
        Intent i = new Intent(MainActivity.this, inputactivity.class);
        startActivity(i);
    }

    // Inside an activity
    public void exitApp() {
        finishAffinity(); // Finishes all activities in the task
    }

    public void doublebackpressed() {

            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);



    }


}