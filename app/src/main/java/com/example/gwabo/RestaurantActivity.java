package com.example.gwabo;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.gwabo.R;
import com.example.gwabo.model.Restaurant;

import java.util.Objects;

public class RestaurantActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ImageView iconBack = findViewById(R.id.icon_back);
        iconBack.setOnClickListener(view -> finish());

        TextView pageTitle = findViewById(R.id.page_title);
        String pageTitleText = getIntent().getStringExtra("PAGE_TITLE");
        if (pageTitleText != null) {
            pageTitle.setText(pageTitleText);
        }

        Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");
        // Set up UI elements with restaurant details
    }
}
