package com.example.gwabo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gwabo.model.CartManager;
import com.example.gwabo.model.Product;

import java.util.Locale;
import java.util.Objects;

public class ProductDetailActivity extends AppCompatActivity {

    private final int quantity = 1;

    @SuppressLint({"MissingInflatedId", "SetTextI18n", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ImageView iconBack = findViewById(R.id.icon_back);
        iconBack.setOnClickListener(view -> finish());

        TextView pageTitle = findViewById(R.id.page_title);
        String pageTitleText = getIntent().getStringExtra("PAGE_TITLE");
        if (pageTitleText != null) {
            pageTitle.setText(pageTitleText);
        }

        CartManager.loadCart(this);

        // Initialize views
        ImageView productImage = findViewById(R.id.product_image);
        TextView productName = findViewById(R.id.product_name);
        TextView productDescription = findViewById(R.id.product_description);
        TextView productPrice = findViewById(R.id.product_price);
        TextView productStock = findViewById(R.id.product_stock);
        Button addToCartButton = findViewById(R.id.add_to_cart_button);
        TextView instock = findViewById(R.id.in_stock);

        // Get product from intent
        Product product = (Product) getIntent().getSerializableExtra("product");

        // Populate the UI
        if (product != null) {
            productName.setText(product.getName());
            productDescription.setText(product.getDescription());
            productPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
            productStock.setText(String.valueOf(product.getStock()));
            Log.d("ProductImage", "Image URL: " + product.getImageUrl());
            Glide.with(this)
                    .load(product.getImageUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(productImage);
            if (product.getStock() > 0) {
                instock.setText("In Stock");
                instock.setTextColor(getResources().getColor(R.color.green));
            } else {
                instock.setText("Out of Stock");
                instock.setTextColor(getResources().getColor(R.color.main_700));
            }
        }

        // Implement functionality to add to cart
        addToCartButton.setOnClickListener(view -> {
            if (product != null) {
                // Set initial quantity to 1 if not set
                if (product.getQuantity() == 0) {
                    product.setQuantity(quantity);
                }
                CartManager.addProductToCart(product, this);
                Toast.makeText(ProductDetailActivity.this, product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProductDetailActivity.this, "Failed to add product to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
