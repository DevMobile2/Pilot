package com.amzur.pilot;

import android.content.Intent;

import com.amzur.pilot.config.OutlineShadow;
import com.amzur.pilot.utilities.PreferenceData;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

/**
 * Created by MRamesh on 11-05-2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, shadows = OutlineShadow.class, sdk = 21)
public class CategoriesActivityTest {
    CategoriesActivity categoriesActivity;

    @Before
    public void setUp() {
        PreferenceData.initPrefs(ShadowApplication.getInstance().getApplicationContext());
        Intent intent = new Intent(ShadowApplication.getInstance().getApplicationContext(), CategoriesActivity.class);
        categoriesActivity = Robolectric.buildActivity(CategoriesActivity.class).withIntent(intent).create().get();
        Assert.assertNotNull(categoriesActivity);
    }

    @Test
    public void testInitializeUIComponents() {
        categoriesActivity.initializeUIComponents();
        Assert.assertNotNull(categoriesActivity.rvCategories);
        Assert.assertNotNull(categoriesActivity.refreshLayout);
        Assert.assertEquals(categoriesActivity.getTitle(), "Categories");
    }
}
