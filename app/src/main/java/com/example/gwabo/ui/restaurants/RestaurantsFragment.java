package com.example.gwabo.ui.restaurants;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gwabo.R;
import com.example.gwabo.adapter.RestaurantsAdapter;
import com.example.gwabo.model.Restaurant;
import com.example.gwabo.network.ApiService;
import com.example.gwabo.RestaurantActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantsFragment extends Fragment {

    private List<Restaurant> restaurantList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_restaurants, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.restaurantRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        Gson gson = new GsonBuilder().setLenient().create();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wavemediatech.ca/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        restaurantList = new ArrayList<>();
        RestaurantsAdapter restaurantsAdapter = new RestaurantsAdapter(restaurantList, getContext(), restaurant -> {
            Intent intent = new Intent(getActivity(), RestaurantActivity.class);
            intent.putExtra("restaurant", restaurant);
            startActivity(intent);
        });
        recyclerView.setAdapter(restaurantsAdapter);

        apiService.getRestaurants().enqueue(new Callback<List<Restaurant>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<Restaurant>> call, @NonNull Response<List<Restaurant>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    restaurantList.clear();
                    restaurantList.addAll(response.body());
                    restaurantsAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to get restaurants", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Restaurant>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }
}
