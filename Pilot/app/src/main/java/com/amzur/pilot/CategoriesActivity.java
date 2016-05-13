package com.amzur.pilot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.amzur.pilot.adapters.CategoriesAdapter;
import com.amzur.pilot.myretrofit.Listener;
import com.amzur.pilot.myretrofit.RetrofitService;
import com.amzur.pilot.pojos.Categories;
import com.amzur.pilot.pojos.JsonParserForAll;
import com.amzur.pilot.utilities.PreferenceData;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;

/**
 * This Activity displays list of categories.
 * Created by KRamesh on 06-05-2016.
 */
public class CategoriesActivity extends AppCompatActivity {
    //To display categories.
    public RecyclerView rvCategories;
    //Adapter to set data to the recyclerview;
    public CategoriesAdapter categoriesAdapter;
    public SwipeRefreshLayout refreshLayout;
    public Categories categories;
    //flag used to quit the application on clicking back button twice.
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        initializeUIComponents();
        // setAdapterToRecyclerView();

    }

    /**
     * This method sets adapter to recycler view to display categories.
     */

    public void setAdapterToRecyclerView() {

        categoriesAdapter = new CategoriesAdapter(CategoriesActivity.this, categories.Categories);
        rvCategories.setAdapter(categoriesAdapter);
    }


    /**
     * Get categories for the user
     */
    public void getCategories(int page) {
        Call<ResponseBody> call = MyApplication.getSerivce().getCategories();
        call.enqueue(new Listener(new RetrofitService() {
            @Override
            public void onSuccess(String result, int pos, Throwable t) {
                if (pos == 0) {
                    categories = new JsonParserForAll().parseResponseToCategories(result);
                    if (categories != null) {
                        refreshLayout.setVisibility(View.VISIBLE);
                        findViewById(R.id.tvNoCategories).setVisibility(View.GONE);
                        setAdapterToRecyclerView();
                    } else {
                        showNoCategoriesFound();
                    }
                } else {
                    showNoCategoriesFound();
                }
            }


        }, null, true, this));
    }

    private void showNoCategoriesFound() {
        refreshLayout.setVisibility(View.GONE);
        findViewById(R.id.tvNoCategories).setVisibility(View.VISIBLE);
    }


    /**
     * This method initializes all the ui elements in the xml files.
     */
    public void initializeUIComponents() {
        rvCategories = (RecyclerView) findViewById(R.id.rvCategories);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCategories(1);
                refreshLayout.setRefreshing(false);
            }
        });
        setTitle("Categories");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CategoriesActivity.this, 2);
        rvCategories.setLayoutManager(gridLayoutManager);
        getCategories(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, R.id.button, Menu.NONE, "Logout").setIcon(R.drawable.ic_power_settings_new_white_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.button) {
            PreferenceData.signOut(CategoriesActivity.this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    /**
     * This method refresh the page when no data found textview is clicked.
     * @param view object of no data found Textview.
     */
    public void refreshPage(View view) {
        getCategories(1);
    }
}
