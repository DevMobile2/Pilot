package com.amzur.pilot.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amzur.pilot.R;
import com.amzur.pilot.SelectedCategoryActivity;

/**
 * Created by MRamesh on 06-05-2016.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    //Instance of CategoriesActivity.
    Activity categoriesActivity;
    //static image ids to display categories.
    int[] CATEGORY_IMAGEVIEW_IDS = new int[]{R.drawable.mobiles, R.drawable.laptops, R.drawable.clothing, R.drawable.furniture, R.drawable.mobiles, R.drawable.books};
    //static categories names.
    String[] CATEGORY_NAMES=new String[]{"Mobiles","Laptops","Clothing & Accessories","Furniture","Jewelery","Books"};

    public CategoriesAdapter(Activity activity) {
        categoriesActivity = activity;

    }

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = categoriesActivity.getLayoutInflater().inflate(R.layout.categories_item_view, parent, false);
        return new CategoriesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoriesViewHolder holder, int position) {

        holder.categorie_imageview.setImageResource(CATEGORY_IMAGEVIEW_IDS[position]);
        holder.category_name.setText(CATEGORY_NAMES[position]);
        holder.category_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(categoriesActivity,SelectedCategoryActivity.class);
                intent.putExtra("category_name","mobiles");
                categoriesActivity.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return CATEGORY_IMAGEVIEW_IDS.length;
    }

    /**
     * describes an item view and metadata about its place within the RecyclerView.
     */
    public class CategoriesViewHolder extends RecyclerView.ViewHolder {
        ImageView categorie_imageview;
        TextView category_name;
        CardView category_view;
        public CategoriesViewHolder(View itemView) {
            super(itemView);
            categorie_imageview = (ImageView) itemView.findViewById(R.id.imageview_category);
            category_name=(TextView) itemView.findViewById(R.id.textview_category_name);
            category_view= (CardView) itemView.findViewById(R.id.cardview_category);
        }
    }
}
