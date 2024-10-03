package com.example.gwabo.adapter;// GroceryAdapter.java
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gwabo.ProductDetailActivity;
import com.example.gwabo.R;
import com.example.gwabo.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {

    private List<Product> groceryList;
    private Context context;

    public GroceryAdapter(List<Product> groceryList, Context context) {
        this.groceryList = groceryList;
        this.context = context;
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grocery, parent, false);
        return new GroceryViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        Product grocery = groceryList.get(position);
        holder.groceryName.setText(grocery.getName());
        holder.groceryPrice.setText("$ " + grocery.getPrice());
        // Load image using Picasso
        Glide.with(context)
                .load(grocery.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.groceryImage);
        Log.d("ProductImage", "Image URL: " + grocery.getImage());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("product", grocery);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        Log.d("GroceryAdapter", "getItemCount: " + groceryList.size());
        return groceryList.size();
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
        TextView groceryName, groceryPrice;
        ImageView groceryImage;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
            groceryName = itemView.findViewById(R.id.grocery_name);
            groceryPrice = itemView.findViewById(R.id.grocery_price);
            groceryImage = itemView.findViewById(R.id.grocery_image);
        }
    }
}
