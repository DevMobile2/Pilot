package com.amzur.pilot.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amzur.pilot.R;
import com.amzur.pilot.ViewItemDetailsActivity;

/**
 * Created by MRamesh on 06-05-2016.
 */
public class SelectedCategoryAdapter extends RecyclerView.Adapter<SelectedCategoryAdapter.SelectedCategoryViewHolder> {
    //Instance of SelectedCategoryActivity.
    Activity activity;
    //name of the category to display items in recyclerview.
    String category_name;
    //static image ids to display categories.
    int[] CATEGORY_IMAGEVIEW_IDS = new int[]{R.drawable.mobiles, R.drawable.laptops, R.drawable.mobiles, R.drawable.laptops, R.drawable.mobiles, R.drawable.laptops};
    //static categories names.
    String[] CATEGORY_NAMES=new String[]{"Mobiles","Laptops","Clothing & Accessories","Furniture","Jewelery","Books"};
    String[] ITEM_COSTS=new String[]{"RS 10000"," RS15000","RS 12000","RS 13000","RS 14000","RS 15000"};
    public SelectedCategoryAdapter(Activity activity,String category_name){
        this.activity=activity;
        this.category_name=category_name;

    }
    @Override
    public SelectedCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v=activity.getLayoutInflater().inflate(R.layout.selected_category_item_view,parent,false);
        return new SelectedCategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SelectedCategoryViewHolder holder, final int position) {
        if(category_name.equals("mobiles")){
            holder.textview_cost.setText(ITEM_COSTS[position]);
            holder.textview_company_name.setText(CATEGORY_NAMES[position]);
            holder.imageview_selected_category.setImageResource(CATEGORY_IMAGEVIEW_IDS[position]);
        }
        holder.cardview_each_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity,ViewItemDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("image_id",CATEGORY_IMAGEVIEW_IDS[position]);
                bundle.putString("comapnay_name",CATEGORY_NAMES[position]);
                bundle.putString("cost",ITEM_COSTS[position]);
                intent.putExtra("item_details",bundle);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return CATEGORY_NAMES.length;
    }
    /**
     * describes an item view and metadata about its place within the RecyclerView.
     */
    public class SelectedCategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview_selected_category;
        TextView textview_cost,textview_company_name;
        CardView cardview_each_item;
        Button options;
        public SelectedCategoryViewHolder(View itemView) {
            super(itemView);
            imageview_selected_category= (ImageView) itemView.findViewById(R.id.imageview_selected_category);
            textview_company_name= (TextView) itemView.findViewById(R.id.textview_company_name);
             textview_cost = (TextView) itemView.findViewById(R.id.textview_cost);
            cardview_each_item=(CardView) itemView.findViewById(R.id.selected_category_cardview);
            options= (Button) itemView.findViewById(R.id.menu_icon);
        }
    }
}
