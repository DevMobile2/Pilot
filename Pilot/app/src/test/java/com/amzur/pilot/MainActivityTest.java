package com.amzur.pilot;

import android.content.Intent;

import org.apache.tools.ant.Main;
import org.junit.Assert;
import org.junit.Before;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowApplication;

/**
 * Created by MRamesh on 13-05-2016.
 */
public class MainActivityTest {
    MainActivity activity;
    @Before
    public void setUP(){
        Intent intent=new Intent(ShadowApplication.getInstance().getApplicationContext(),MainActivity.class);
        activity= Robolectric.buildActivity(MainActivity.class).withIntent(intent).create().get();
        Assert.assertNotNull(activity);
    }

}
