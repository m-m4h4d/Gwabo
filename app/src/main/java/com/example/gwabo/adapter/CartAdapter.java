package com.example.gwabo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gwabo.R;
import com.example.gwabo.model.Product;
import com.example.gwabo.model.CartManager;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<Product> cartItems;
    private final Context context;

    public CartAdapter(List<Product> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartItems.get(position);

        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.format("$%.2f", product.getPrice()));
        holder.productQty.setText(String.format("Qty: %d", product.getQuantity()));
        holder.productStock.setText(product.getStock() > 0 ? "In Stock" : "Out of Stock");

        Glide.with(context)
                .load(product.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.productImage);

        // Handle remove from cart button
        holder.removeButton.setOnClickListener(v -> {
            CartManager.removeProductFromCart(product, context);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName, productPrice, productQty, productStock;
        Button removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQty = itemView.findViewById(R.id.product_qty);
            productStock = itemView.findViewById(R.id.product_stock);
            removeButton = itemView.findViewById(R.id.remove_button);
        }
    }
}