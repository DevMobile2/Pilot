package com.amzur.pilot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.amzur.pilot.utilities.NetworkUtils;
import com.amzur.pilot.utilities.Utils;

/**
 * Created by MRamesh on 12-05-2016.
 *
 */
public class SplashScreen extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if(NetworkUtils.isInternetOn(this)) {
            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }else {
            Utils.showErrorAlert("No Network", "Please check interconnection and open again", this, new Utils.ErrorAlertCompleted() {
                @Override
                public void OkaySelected() {
                    finish();
                }
            });
        }
}
}
