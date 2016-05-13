package com.amzur.pilot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amzur.pilot.myretrofit.Listener;
import com.amzur.pilot.myretrofit.RetrofitService;
import com.amzur.pilot.pojos.ItemPojo;
import com.amzur.pilot.pojos.JsonParserForAll;
import com.amzur.pilot.utilities.PreferenceData;
import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Call;

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
           goHome();
        }
    };
    int ITEM_ID=1;
    ItemPojo item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeUIElements();
        getDataFromPreviousActivity();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(onClickListener);
        }

        Log.i("ITEM",ITEM_ID+"");
        if(ITEM_ID==0)
        {
            Toast.makeText(this,"Not a valid thing",Toast.LENGTH_LONG).show();
            finish();
        }else
        getItemDetails();
    }


    /**
     * on buy pressed performs buy operation*
     */
     public void buyClick(View v)
     {
         JSONObject object=new JSONObject();
         try {
             object.put("itemId",ITEM_ID);
             Call<ResponseBody> call=MyApplication.getSerivce().buyItem(object.toString(), "application/json");
             call.enqueue(new Listener(new RetrofitService() {
                 @Override
                 public void onSuccess(String result, int pos, Throwable t) {

                     if(pos==0)
                     {
                         try {
                             JSONObject object=new JSONObject(result);
                             if(item!=null)
                             item.quantity=object.getInt("quantity");
                           //  updateUi();
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }
                 }
             },"buying...",true,this));

         } catch (JSONException e) {
             e.printStackTrace();
         }

     }

    /**
     * Get item details*/
    public void getItemDetails()
    {
        Call<ResponseBody> call=MyApplication.getSerivce().getItemDetails(ITEM_ID);
        call.enqueue(new Listener(new RetrofitService() {
            @Override
            public void onSuccess(String result, int pos, Throwable t) {
                if(pos==0)
                {
                    item=new JsonParserForAll().parseItemResponse(result);
                    if(item!=null)
                    {
                        updateUi();
                        checkItemsQuantity();
                    }
                }
            }
        },"getting...",true,this));
    }

    /**
     * Update the ui using the response we got from server*/
    public void updateUi()
    {
        ((TextView)findViewById(R.id.tvUnitPrice)).setText(item.unitPrice+"");
        ((TextView)findViewById(R.id.tvItemName)).setText(item.itemName);
        ((TextView)findViewById(R.id.tvCategoryName)).setText(item.categoryName);
        ((TextView)findViewById(R.id.tvDescription)).setText(item.description);
        ((TextView)findViewById(R.id.tvSpecifications)).setText(item.specifications);
        ((TextView)findViewById(R.id.tvSeller)).setText(item.seller);

        ((TextView)findViewById(R.id.tvCondition)).setText(item.itemCondition);

        Picasso.with(this).load(item.imageUrl).placeholder(R.drawable.ecommerce_placeholder).error(R.drawable.error_image).into((ImageView) findViewById(R.id.imageview_item));
    }

    public void checkItemsQuantity()
    {
        ((TextView)findViewById(R.id.tvQuantity)).setText(item.quantity+"");
        if(item.quantity<1)
        {
            Button buy= (Button) findViewById(R.id.btBuy);
            buy.setText("SOLD OUT");
            buy.setEnabled(false);
        }
    }
    /**
     * This method initializes all the ui elements.
     */
    private void initializeUIElements() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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

                Picasso.with(this)
                .load(b.getString("imageUrl"))
                .placeholder(R.drawable.ecommerce_placeholder)
                .error(R.drawable.error_image)
                .into(ivItem);

            if (b.getString("companyName") != null) {
                collapsingToolbarLayout.setTitle(b.getString("companyName"));
                collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(ViewItemDetailsActivity.this, R.color.blue_app));
            }
            ITEM_ID=b.getInt("id",0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, R.id.fab, Menu.NONE, "Home").setIcon(R.drawable.ic_home_white_36dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, R.id.button, Menu.NONE, "Logout").setIcon(R.drawable.ic_power_settings_new_white_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.button:PreferenceData.signOut(ViewItemDetailsActivity.this);
                break;
            case R.id.fab:goHome();
                break;
            case android.R.id.home:finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * Navigate to home page*/
    public void goHome()
    {
        Intent in=new Intent(ViewItemDetailsActivity.this,CategoriesActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
    }
}
