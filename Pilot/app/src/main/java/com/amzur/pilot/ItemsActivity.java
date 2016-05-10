package com.amzur.pilot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.amzur.pilot.adapters.ItemsAdapter;
import com.amzur.pilot.utilities.PreferenceData;


/**
 * Created by MRamesh on 06-05-2016.
 * This Activity displays items of a selected category.
 */
public class ItemsActivity extends AppCompatActivity {
    // to display items of selected category.
    RecyclerView rvItems;
    //adapter to set data to the recycler view
   ItemsAdapter itemsAdapter;
    //name of the selected category.
    String categoryName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Bundle b = getIntent().getExtras();
        categoryName = b.getString("category_name");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle(categoryName);
        setAdapterToRecyclerView();
    }

    /**
     * This method sets data to the recycler view.
     */
    private void setAdapterToRecyclerView() {
        int numberOfColumns=1;
        rvItems = (RecyclerView) findViewById(R.id.rvItems);
        int width=PreferenceData.getScreenWidth(ItemsActivity.this);
        if(width>500 && width <1200)
            numberOfColumns=2;
        else if(width>1200)
            numberOfColumns=3;
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(numberOfColumns, 1);
        itemsAdapter = new ItemsAdapter(ItemsActivity.this, categoryName);
        rvItems.setLayoutManager(gridLayoutManager);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,R.id.button,Menu.NONE,"Logout").setIcon(R.drawable.ic_power_settings_new_white_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.button)
            PreferenceData.signOut(ItemsActivity.this);
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
