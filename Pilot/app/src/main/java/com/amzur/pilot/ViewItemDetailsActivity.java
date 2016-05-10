package com.amzur.pilot;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amzur.pilot.utilities.PreferenceData;

/**
 * This class displays item details of a selected item.
 */

public class ViewItemDetailsActivity extends AppCompatActivity {
    //selected item image view.
    ImageView ivItem;
    //to display company name
    TextView tvCompanyName;
    //Toolbar of the Activity.
    Toolbar toolbar;
    //collapsingToolbarLayout of the activity.
    CollapsingToolbarLayout collapsingToolbarLayout;
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeUIElements();
        getDataFromPreviousActivity();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(onClickListener);
    }

    /**
     * This method initializes all the ui elements.
     */
    private void initializeUIElements() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ivItem = (ImageView) findViewById(R.id.imageview_item);
        tvCompanyName = (TextView) findViewById(R.id.tvCompanyName);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        int height = PreferenceData.getScreenHeight(ViewItemDetailsActivity.this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height / 2);
        ivItem.setLayoutParams(layoutParams);

    }

    /**
     * This method reads item details  from the previous activity.
     */
    public void getDataFromPreviousActivity() {
        Bundle b = getIntent().getBundleExtra("itemDetails");
        if (b != null) {
            if (b.getInt("imageId") != 0)
                ivItem.setImageResource(b.getInt("imageId"));
            if (b.getString("companyName") != null) {
                collapsingToolbarLayout.setTitle(b.getString("companyName"));
                collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(ViewItemDetailsActivity.this, R.color.blue_app));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, R.id.button, Menu.NONE, "Logout").setIcon(R.drawable.ic_power_settings_new_white_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.button)
            PreferenceData.signOut(ViewItemDetailsActivity.this);
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
