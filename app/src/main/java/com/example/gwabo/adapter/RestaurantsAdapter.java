package com.example.gwabo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gwabo.R;
import com.example.gwabo.model.Restaurant;

import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {

    private final List<Restaurant> restaurantList;
    private final Context context;
    private final OnRestaurantClickListener listener;

    public interface OnRestaurantClickListener {
        void onRestaurantClick(Restaurant restaurant);
    }

    public RestaurantsAdapter(List<Restaurant> restaurantList, Context context, OnRestaurantClickListener listener) {
        this.restaurantList = restaurantList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);

        // Load restaurant image
        Glide.with(context)
                .load(restaurant.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(holder.restaurantImage);

        holder.itemView.setOnClickListener(v -> listener.onRestaurantClick(restaurant));
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView restaurantImage;

        ViewHolder(View itemView) {
            super(itemView);
            restaurantImage = itemView.findViewById(R.id.restaurantImage);
        }
    }
}
