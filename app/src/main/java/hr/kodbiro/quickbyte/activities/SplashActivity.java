/*
 * Copyright © 2014 Marko Filipović sifilis@kset.org
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2, as published by Sam Hocevar
 */

package hr.kodbiro.quickbyte.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by marko on 27.8.2014..
 * Splash screen activity
 */
public class SplashActivity extends Activity {

    private static final long SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // activity timeout handler
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // start main activity
                Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
