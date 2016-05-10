package com.amzur.pilot.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
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
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.SelectedCategoryViewHolder> {
    //Instance of SelectedCategoryActivity.
    Activity activity;
    //name of the category to display items in recycler view.
    String categoryName;
    //static image ids to display items in the categories.
    int[] ITEM_IDS = new int[]{R.drawable.mobiles, R.drawable.laptops, R.drawable.mobiles, R.drawable.laptops, R.drawable.mobiles, R.drawable.laptops};
    //static item names.
    String[] ITEM_NAMES = new String[]{"Mobiles", "Laptops", "Clothing & Accessories", "Furniture", "Jewelery", "Books"};
    //static item costs.
    String[] ITEM_COSTS = new String[]{"RS 10000", " RS15000", "RS 12000", "RS 13000", "RS 14000", "RS 15000"};
    /**
     * Listener for the onclick events.
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            switch (viewId) {
                case R.id.ivCategory:
                    int position = (int) v.getTag();
                    Intent intent = new Intent(activity, ViewItemDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("imageId", ITEM_IDS[position]);
                    bundle.putString("companyName", ITEM_NAMES[position]);
                    bundle.putString("cost", ITEM_COSTS[position]);
                    intent.putExtra("itemDetails", bundle);
                    activity.startActivity(intent);
                    break;
                case R.id.menu_icon:
                    PopupMenu popupMenu = new PopupMenu(activity, v);
                    popupMenu.getMenu().add(Menu.NONE, android.R.id.button1, Menu.NONE, "BUY");
                    popupMenu.show();
                    break;
            }
        }
    };

    public ItemsAdapter(Activity activity, String categoryName) {
        this.activity = activity;
        this.categoryName = categoryName;

    }

    @Override
    public SelectedCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = activity.getLayoutInflater().inflate(R.layout.adap_items_item, parent, false);
        return new SelectedCategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SelectedCategoryViewHolder holder, final int position) {
        if (categoryName.equals("mobiles")) {
            holder.tvCost.setText(ITEM_COSTS[position]);
            holder.tvCompanyName.setText(ITEM_NAMES[position]);
            holder.ivItem.setImageResource(ITEM_IDS[position]);
            if(position==3)
                holder.tvSoldOut.setVisibility(View.VISIBLE);
            else
                holder.tvSoldOut.setVisibility(View.GONE);
        }
        holder.ivItem.setTag(position);
        holder.ivItem.setOnClickListener(onClickListener);
        holder.options.setOnClickListener(onClickListener);

    }

    @Override
    public int getItemCount() {
        return ITEM_NAMES.length;
    }

    /**
     * describes an item view and metadata about its place within the RecyclerView.
     */
    public class SelectedCategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItem;
        TextView tvCost, tvCompanyName,tvSoldOut;
        Button options;

        public SelectedCategoryViewHolder(View itemView) {
            super(itemView);
            ivItem = (ImageView) itemView.findViewById(R.id.ivCategory);
            tvCompanyName = (TextView) itemView.findViewById(R.id.tvCompanyName);
            tvCost = (TextView) itemView.findViewById(R.id.tvCost);
            options = (Button) itemView.findViewById(R.id.menu_icon);
            tvSoldOut= (TextView) itemView.findViewById(R.id.tvSold);
        }
    }
}
