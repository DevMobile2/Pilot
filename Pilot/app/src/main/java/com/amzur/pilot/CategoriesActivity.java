package com.amzur.pilot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.amzur.pilot.adapters.CategoriesAdapter;
import com.facebook.login.LoginManager;

/**
 * This Activity displays list of categories.
 * Created by MRamesh on 06-05-2016.
 */
public class CategoriesActivity extends AppCompatActivity {
    //To display categories.
    RecyclerView recyclerViewCategories;
    //Adapter to set data to the recyclerview;
    CategoriesAdapter categoriesAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        initializeUIComponents();
        setAdapterToRecyclerView();
    }

    /**
     * This method sets adapter to recyclerview to display categories.
     */

    private void setAdapterToRecyclerView() {
        categoriesAdapter=new CategoriesAdapter(CategoriesActivity.this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(CategoriesActivity.this);
        recyclerViewCategories.setLayoutManager(layoutManager);
        recyclerViewCategories.setAdapter(categoriesAdapter);


    }

    /**
     * This method initializes all the ui elements in the xml files.
     */
    private void initializeUIComponents() {
        recyclerViewCategories= (RecyclerView) findViewById(R.id.recyclerview_categories);
        setTitle("Categories");
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
