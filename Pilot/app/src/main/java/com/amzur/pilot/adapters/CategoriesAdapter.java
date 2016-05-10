package com.amzur.pilot.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amzur.pilot.ItemsActivity;
import com.amzur.pilot.R;

/**
 * This adapter sets data to the recycler view in Categories activity.
 * Created by MRamesh on 06-05-2016.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    //Instance of CategoriesActivity.
    Activity categoriesActivity;
    //static image ids to display categories.
    int[] CATEGORY_IMAGE_IDS = new int[]{R.drawable.mobiles, R.drawable.laptops, R.drawable.clothing, R.drawable.furniture, R.drawable.mobiles, R.drawable.books};
    //static categories names.
    String[] CATEGORY_NAMES = new String[]{"Mobiles", "Laptops", "Clothing & Accessories", "Furniture", "Jewelery", "Books"};
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(categoriesActivity, ItemsActivity.class);
            intent.putExtra("category_name", "mobiles");
            categoriesActivity.startActivity(intent);
        }
    };

    public CategoriesAdapter(Activity activity) {
        categoriesActivity = activity;

    }

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = categoriesActivity.getLayoutInflater().inflate(R.layout.adap_categories_item, parent, false);
        return new CategoriesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoriesViewHolder holder, int position) {

        holder.ivCategories.setImageResource(CATEGORY_IMAGE_IDS[position]);
        holder.tvCategoryName.setText(CATEGORY_NAMES[position]);
        holder.ivCategories.setOnClickListener(onClickListener);

    }

    @Override
    public int getItemCount() {
        return CATEGORY_IMAGE_IDS.length;
    }

    /**
     * describes an item view and metadata about its place within the RecyclerView.
     */
    public class CategoriesViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCategories;
        TextView tvCategoryName;

        public CategoriesViewHolder(View itemView) {
            super(itemView);
            ivCategories = (ImageView) itemView.findViewById(R.id.ivCategory);
            tvCategoryName = (TextView) itemView.findViewById(R.id.textview_category_name);
        }
    }
}
