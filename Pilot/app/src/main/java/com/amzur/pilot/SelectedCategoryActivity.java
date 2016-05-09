package com.amzur.pilot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.amzur.pilot.adapters.SelectedCategoryAdapter;
import com.facebook.login.LoginManager;

/**
 * Created by MRamesh on 06-05-2016.
 * This Activity dispalys items of selected category
 */
public class SelectedCategoryActivity extends AppCompatActivity {
    // to display items of selected category.
    RecyclerView recyclerviewSelectedCategory;
    //adapter to set data to the recyclerview
    SelectedCategoryAdapter selectedCategoryAdapter;
    //name of the selected category.
    String category_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_selected_category);
        Bundle b = getIntent().getExtras();
        category_name = b.getString("category_name");
        setTitle(category_name);
        setAdapterToRecyclerView();
    }

    /**
     * This method sets data to the recyclerview.
     */
    private void setAdapterToRecyclerView() {
        recyclerviewSelectedCategory = (RecyclerView) findViewById(R.id.recyclerview_selected_category);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        selectedCategoryAdapter = new SelectedCategoryAdapter(SelectedCategoryActivity.this, category_name);
        recyclerviewSelectedCategory.setLayoutManager(gridLayoutManager);
        recyclerviewSelectedCategory.setAdapter(selectedCategoryAdapter);
        recyclerviewSelectedCategory.setHasFixedSize(true);
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
