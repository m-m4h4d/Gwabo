package com.example.gwabo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gwabo.model.Product;
import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.Objects;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productDescription, productPrice, productStock, instock;
    private Button addToCartButton;
    private int quantity = 1;

    @SuppressLint({"MissingInflatedId", "SetTextI18n", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Initialize views
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productDescription = findViewById(R.id.product_description);
        productPrice = findViewById(R.id.product_price);
        productStock = findViewById(R.id.product_stock);
        addToCartButton = findViewById(R.id.add_to_cart_button);
        instock = findViewById(R.id.in_stock);

        // Get product from intent
        Product product = (Product) getIntent().getSerializableExtra("product");

        // Populate the UI
        if (product != null) {
            productName.setText(product.getName());
            productDescription.setText(product.getDescription());
            productPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
            productStock.setText(String.valueOf(product.getStock()));
            Log.d("ProductImage", "Image URL: " + product.getImage());
            //productImage.setImageResource(product.getImage());
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
            // Implement logic to add the item to the cart
            // You can use a database or shared preferences to store the cart data
        });
    }
}
