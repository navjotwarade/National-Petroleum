package com.example.navjot.teamproject272;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

/*
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

                pref.getString("username", null); // getting String
                pref.getString("password", null); // getting String



  */
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

                String user=pref.getString("username", null); // getting String
                String pass=pref.getString("password", null); // getting String
                System.out.println("user:"+user+"pass:"+pass);
                if(user==null&& pass==null) {
                    Intent i = new Intent(SplashScreen.this, extra.class);
                    startActivity(i);
                }
                else{
                    Intent j = new Intent(SplashScreen.this, tableActivity.class);
                    startActivity(j);
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }



}
