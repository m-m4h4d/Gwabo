package com.example.gwabo.ui.home;

import android.annotation.SuppressLint;
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
import com.example.gwabo.adapter.ProductAdapter;
import com.example.gwabo.model.Product;
import com.example.gwabo.network.ApiService;
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

public class HomeFragment extends Fragment {

    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the RecyclerView
        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewProducts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));  // 2 columns for grid layout

        // Initialize the product list
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, getContext());
        recyclerView.setAdapter(productAdapter);

        // Initialize Retrofit and load products
        loadProductsFromApi();

        return root;
    }

    private void loadProductsFromApi() {
        // Set up Retrofit
        Gson gson = new GsonBuilder().setLenient().create();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wavemediatech.ca/") // Replace with your domain
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Fetch products from the API
        apiService.getProducts().enqueue(new Callback<List<Product>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Clear the current product list and add new products
                    productList.clear();
                    productList.addAll(response.body());
                    productAdapter.notifyDataSetChanged();  // Refresh the adapter
                } else {
                    Toast.makeText(getContext(), "Failed to get products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
