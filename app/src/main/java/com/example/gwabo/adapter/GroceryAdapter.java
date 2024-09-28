package com.example.gwabo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gwabo.ProductDetailActivity;
import com.example.gwabo.R;
import com.example.gwabo.model.Product;

import java.util.List;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {

    private List<Product> groceriesList;
    private Context context;

    public GroceryAdapter(List<Product> groceriesList, Context context) {
        this.groceriesList = groceriesList;
        this.context = context;
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grocery, parent, false);
        return new GroceryViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        Product product = groceriesList.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText("$ " + product.getPrice());
        holder.productImage.setImageResource(product.getImage());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("product", product);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return groceriesList.size();
    }

    public static class GroceryViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.textView7);
            productPrice = itemView.findViewById(R.id.textView20);
            productImage = itemView.findViewById(R.id.imageView18);
        }
    }
}
