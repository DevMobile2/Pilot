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
import com.amzur.pilot.pojos.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * This adapter sets data to the recycler view in Categories activity.
 * Created by MRamesh on 06-05-2016.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    //Instance of CategoriesActivity.
    Activity categoriesActivity;
    ArrayList<Category> categories;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Category category= (Category) v.getTag();
            Intent intent = new Intent(categoriesActivity, ItemsActivity.class);
            if(category!=null) {
                intent.putExtra("category_name", category.categoryName);
                intent.putExtra("category_id", category.categoryid);
            }
            categoriesActivity.startActivity(intent);
        }
    };

    public CategoriesAdapter(Activity activity,ArrayList<Category> mCategories) {
        categoriesActivity = activity;
        categories=mCategories;
    }

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = categoriesActivity.getLayoutInflater().inflate(R.layout.adap_categories_item, parent, false);
        return new CategoriesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoriesViewHolder holder, int position) {

        Category category=categories.get(position);
        if(category!=null) {
            Picasso.with(categoriesActivity).load(category.imageUrl).placeholder(R.drawable.ecommerce_placeholder).error(R.drawable.error_image).into(holder.ivCategories);
            holder.tvCategoryName.setText(category.categoryName);
            holder.ivCategories.setTag(category);
            holder.ivCategories.setOnClickListener(onClickListener);
        }

    }

    @Override
    public int getItemCount() {
        return categories!=null?categories.size():0;
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
