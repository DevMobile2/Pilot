package com.amzur.pilot;

import android.app.Activity;

/**
 * Created by RameshK on 10-05-2016.
 *
 */
public class Application extends android.app.Application {

    private static android.app.Application instance;



    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public static synchronized android.app.Application getInstance() {
        return instance;
    }


}
