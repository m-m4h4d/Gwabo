package com.example.gwabo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gwabo.R;
import com.example.gwabo.model.Restaurant;

public class RestaurantActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");
        // Set up UI elements with restaurant details
    }
}
