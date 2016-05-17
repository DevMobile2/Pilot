package com.amzur.pilot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.amzur.pilot.adapters.ItemsAdapter;
import com.amzur.pilot.customviews.EndlessRecyclerViewScrollListener;
import com.amzur.pilot.myretrofit.Listener;
import com.amzur.pilot.myretrofit.RetrofitService;
import com.amzur.pilot.pojos.ItemsPojo;
import com.amzur.pilot.pojos.JsonParserForAll;
import com.amzur.pilot.utilities.PreferenceData;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;


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
    //object of swipe refresh layout;
    SwipeRefreshLayout refreshLayout;
    //category id came from the previous activity.
    int CAT_ID;
    //used for adding single item view option in the menu dynamically.
    MenuItem toggle;
    int numOfColums = 2;
    //object of the class containing same fields as the response from the server.
    ItemsPojo items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_bottom_to_top, R.anim.fade_top_to_bottom);
        setContentView(R.layout.activity_items);
        Bundle b = getIntent().getExtras();
        categoryName = b.getString("category_name");

        CAT_ID = b.getInt("category_id", 0);
        if (CAT_ID == 0) {
            Toast.makeText(this, "Invalid category", Toast.LENGTH_LONG).show();
            finish();
        }

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initViews();
        setTitle(categoryName);
        initializeRecyclerView(numOfColums);

    }

    /**
     * This method sets layout manager to the recyclerview,get items from the server and set items details to the recyclerview.
     *
     * @param numberOfColumns number of columns to be displayed in grid layout.
     */
    private void initializeRecyclerView(int numberOfColumns) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(ItemsActivity.this, numberOfColumns);
        rvItems.setLayoutManager(gridLayoutManager);
        rvItems.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                //getItems();
            }
        });
        setAdapterToRecyclerView(numberOfColumns);
        getItems();

    }

    /**
     * Initialize views
     */
    public void initViews() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        rvItems = (RecyclerView) findViewById(R.id.rvItems);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                getItems();
            }
        });

    }

    /**
     * This method sets data to the recycler view.
     *
     * @param itemHeightIndicator value refers to number items in a row
     */
    private void setAdapterToRecyclerView(int itemHeightIndicator) {

        itemsAdapter = new ItemsAdapter(ItemsActivity.this, categoryName, itemHeightIndicator);

        rvItems.setAdapter(itemsAdapter);
        rvItems.setHasFixedSize(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, R.id.button, Menu.NONE, "Logout").setIcon(R.drawable.ic_power_settings_new_white_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        toggle = menu.add(Menu.NONE, R.id.auto, Menu.NONE, "").setIcon(R.drawable.ic_desktop_windows_white_24dp);
        toggle.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.button)
            PreferenceData.signOut(ItemsActivity.this);
        if (item.getItemId() == android.R.id.home)
            finish();
        if (item.getItemId() == R.id.auto) {
            if (numOfColums == 2) {
                numOfColums = 1;
                toggle.setIcon(R.drawable.ic_border_all_white_24dp);
            } else {
                numOfColums = 2;
                toggle.setIcon(R.drawable.ic_desktop_windows_white_24dp);
            }
            initializeRecyclerView(numOfColums);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * This method gets items data from the server.
     */
    public void getItems() {
        Call<ResponseBody> call = MyApplication.getSerivce().getItems(PreferenceData.getString(PreferenceData.PREF_API_KEY),CAT_ID);
        call.enqueue(new Listener(new RetrofitService() {
            @Override
            public void onSuccess(String result, int pos, Throwable t) {

                if (pos == 0) {

               items = new JsonParserForAll().parseResponseToItems(result);
                    if (items != null) {
                        if (items.items.size() > 0) {
                            refreshLayout.setVisibility(View.VISIBLE);
                            findViewById(R.id.tvNoItems).setVisibility(View.GONE);
                            itemsAdapter.addItems(items.items);
                        }
                        else {
                            showNoItemsFound();
                        }
                    } else {
                        showNoItemsFound();
                    }

                } else {
                    showNoItemsFound();
                }
            }
        }, "Loading", true, ItemsActivity.this));
    }

    private void showNoItemsFound() {
        refreshLayout.setVisibility(View.GONE);
        findViewById(R.id.tvNoItems).setVisibility(View.VISIBLE);
    }

    public void refreshPage(View view) {
        getItems();
    }
}
