package com.amzur.pilot;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;

public class ViewItemDetailsActivity extends AppCompatActivity {
    //selected item imageview.
    ImageView imageviewItem;
    //to display company name
    TextView item_company_name;
    //Toolbar of the Activity.
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeUIElements();
        getDataFromPreviousActivity();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * This method initializes all the ui elements.
     */
    private void initializeUIElements() {
        imageviewItem = (ImageView) findViewById(R.id.imageview_item);
     item_company_name= (TextView) findViewById(R.id.item_company_name);
        Display display = getWindowManager().getDefaultDisplay();
        int imageview_height = display.getHeight();
        Log.i("height",imageview_height+"");
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, imageview_height/2);
        imageviewItem.setLayoutParams(layoutParams);

    }

    public void getDataFromPreviousActivity() {
        Bundle b = getIntent().getBundleExtra("item_details");
        if (b != null) {
            if (b.getInt("image_id") != 0)
                imageviewItem.setImageResource(b.getInt("image_id"));
            if(b.getString("comapnay_name")!=null)
               toolbar.setTitle(b.getString("comapnay_name"));



        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,R.id.button,Menu.NONE,"Logout").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.button)
            LoginManager.getInstance().logOut();
        return super.onOptionsItemSelected(item);
    }
}
