package com.amzur.pilot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.amzur.pilot.adapters.CategoriesAdapter;
import com.amzur.pilot.customviews.EndlessRecyclerViewScrollListener;
import com.amzur.pilot.myretrofit.Listener;
import com.amzur.pilot.myretrofit.RetrofitService;
import com.amzur.pilot.utilities.PreferenceData;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Call;

/**
 * This Activity displays list of categories.
 * Created by KRamesh on 06-05-2016.
 */
public class CategoriesActivity extends AppCompatActivity {
    //To display categories.
   public RecyclerView rvCategories;
    //Adapter to set data to the recyclerview;
   public  CategoriesAdapter categoriesAdapter;
    //flag used to quit the application on clicking back button twice.
    boolean doubleBackToExitPressedOnce = false;
  public  SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        initializeUIComponents();
        setAdapterToRecyclerView();
        doLogin();
    }

    /**
     * This method sets adapter to recycler view to display categories.
     */

    private void setAdapterToRecyclerView() {

        categoriesAdapter=new CategoriesAdapter(CategoriesActivity.this);

        rvCategories.setAdapter(categoriesAdapter);


    }



    /**
     * Get categories for the user*/
    public void getCategories(int page)
    {
        Call<ResponseBody> call=MyApplication.getSerivce().getCategories();
        call.enqueue(new Listener(new RetrofitService() {
            @Override
            public void onSuccess(String result, int pos, Throwable t) {
              if(pos==0)
              {

              }
            }
        },null,true,this));
    }

    public void doLogin()
    {
        JSONObject loginObject=new JSONObject();
        try {
            loginObject.put("email","ramesh.korada@gmail.com");
            loginObject.put("deviceId", Settings.Secure.getString(MyApplication.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID));
            loginObject.put("deviceType","ANDROID");
            loginObject.put("deviceToken","ljkjbflksjdflkjsjdfkjdsbnfkjskfjbskldbfksb");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<ResponseBody> call=MyApplication.getSerivce().authLogin(loginObject.toString(),"application/json");
        call.enqueue(new Listener(new RetrofitService() {
            @Override
            public void onSuccess(String result, int pos, Throwable t) {
                if(pos==0)
                {
                    PreferenceData.putString(PreferenceData.PREF_API_KEY,result);
                }
            }
        },"",true,CategoriesActivity.this));
    }

    /**
     * This method initializes all the ui elements in the xml files.
     */
    public void initializeUIComponents() {
        rvCategories= (RecyclerView) findViewById(R.id.rvCategories);
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });
        setTitle("Categories");

        int number_of_columns=1;
        int width=PreferenceData.getScreenWidth(CategoriesActivity.this);
        Log.i("screen width", String.valueOf(width));
        if(width>500 && width <1200)
            number_of_columns=2;
        else if(width>1200)
            number_of_columns=3;
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(number_of_columns,1);
        rvCategories.setLayoutManager(staggeredGridLayoutManager);
        rvCategories.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                getCategories(page);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,R.id.button,Menu.NONE,"Logout").setIcon(R.drawable.ic_power_settings_new_white_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.button) {
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
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
