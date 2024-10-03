package com.example.gwabo.ui.groceries;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gwabo.R;
import com.example.gwabo.adapter.GroceryAdapter;
import com.example.gwabo.model.Product;
import com.example.gwabo.network.ApiService;
import com.google.gson.Gson;

import java.lang.ref.Reference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GroceriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private GroceryAdapter adapter;
    private List<Product> productList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_groceries, container, false);

        // Initialize RecyclerView
        recyclerView = root.findViewById(R.id.groceries_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://darkblue-grouse-797601.hostingersite.com/") // Replace with your domain
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Make the network request to get the products
        apiService.getProducts().enqueue(new Callback<List<Product>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Get the product list from the response
                    Log.d("APIResponse", new Gson().toJson(response.body()));
                    productList = response.body();
                    for (Product product : productList) {
                        Log.d("ProductImage", "Image URL: " + product.getImageUrl());  // Assuming getImageUrl() returns the image URL
                    }
                    // Initialize the adapter and set it to the RecyclerView
                    adapter = new GroceryAdapter(productList, getContext());
                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(getContext(), "Failed to get products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }
}
