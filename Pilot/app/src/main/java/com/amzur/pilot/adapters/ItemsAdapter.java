package com.amzur.pilot.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amzur.pilot.R;
import com.amzur.pilot.ViewItemDetailsActivity;
import com.amzur.pilot.pojos.ItemPojo;
import com.amzur.pilot.utilities.PreferenceData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MRamesh on 06-05-2016.
 *
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.SelectedCategoryViewHolder> {
    //Instance of SelectedCategoryActivity.
    Activity activity;
    //name of the category to display items in recycler view.
    String categoryName;
    //static image ids to display items in the categories.
    boolean itemImageHeightIndicator;

    ArrayList<ItemPojo> items;
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

                    bundle.putString("imageUrl", items.get(position).imageUrl);
                    bundle.putInt("id", items.get(position).itemId);
                    bundle.putString("category_name",categoryName);
                    intent.putExtra("itemDetails", bundle);

                    ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, v, "ROBOT");

                    activity.startActivity(intent, compat.toBundle());
                    break;
                case R.id.menu_layout:
                case R.id.menu_icon:
                    PopupMenu popupMenu = new PopupMenu(activity, v);
                    popupMenu.getMenu().add(Menu.NONE, android.R.id.button1, Menu.NONE, "BUY");
                    popupMenu.show();
                    break;
            }
        }
    };

    public ItemsAdapter(Activity activity, String categoryName, int itemHeightIndicator) {
        this.activity = activity;
        this.categoryName = categoryName;
        items = new ArrayList<>();
        if(itemHeightIndicator==1)
            itemImageHeightIndicator=true;
    }

    public void addItems(List<ItemPojo> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override
    public SelectedCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = activity.getLayoutInflater().inflate(R.layout.adap_items_item, parent, false);
        return new SelectedCategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SelectedCategoryViewHolder holder, final int position) {

        ItemPojo item = items.get(position);
        if (item != null) {
            holder.tvCompanyName.setText(item.itemName);
            holder.tvUnitPrice.setText("$"+item.price);
            Picasso.with(activity)
                    .load(item.imageUrl)
                    .placeholder(R.drawable.ecommerce_placeholder)
                    .error(R.drawable.error_image).
                    into(holder.ivItem);

            if (item.quantity == 0) {
                // holder.menu.setVisibility(View.GONE);
                holder.tvSoldOut.setVisibility(View.VISIBLE);
            } else {
                // holder.menu.setVisibility(View.VISIBLE);
                holder.tvSoldOut.setVisibility(View.GONE);
            }

            holder.ivItem.setTag(position);
            holder.ivItem.setOnClickListener(onClickListener);
            holder.menu.setOnClickListener(onClickListener);
            holder.options.setOnClickListener(onClickListener);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * describes an item view and metadata about its place within the RecyclerView.
     */
    public class SelectedCategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItem;
        TextView tvCompanyName, tvSoldOut,tvUnitPrice;
        Button options;
        View menu;

        public SelectedCategoryViewHolder(View itemView) {
            super(itemView);
            ivItem = (ImageView) itemView.findViewById(R.id.ivCategory);
            tvCompanyName = (TextView) itemView.findViewById(R.id.tvCompanyName);
            menu = itemView.findViewById(R.id.menu_layout);
            options = (Button) itemView.findViewById(R.id.menu_icon);
            tvSoldOut = (TextView) itemView.findViewById(R.id.tvSold);
            tvUnitPrice=(TextView)itemView.findViewById(R.id.tvUnitPrice);
            if(itemImageHeightIndicator){
               int height;
                int width=PreferenceData.getScreenWidth(activity);
                if(width>600)
                    height=600;
                else
                height=width;
                FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(width,height);
                ivItem.setLayoutParams(layoutParams);
            }
        }
    }
}
