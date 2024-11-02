package com.example.gwabo.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gwabo.R;
import com.example.gwabo.adapter.CategoryAdapter;
import com.example.gwabo.adapter.ProductAdapter;
import com.example.gwabo.model.Category;
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
    private List<Product> displayedProducts;
    private List<Category> categoryList;
    private CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the RecyclerView
        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewProducts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));  // 2 columns for grid layout

        // Initialize the product list
        productList = new ArrayList<>();
        displayedProducts = new ArrayList<>();
        productAdapter = new ProductAdapter(displayedProducts, getContext());

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

        RecyclerView categoryRecyclerView = root.findViewById(R.id.categoryTabs);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(categoryList, getContext(), new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(Integer categoryId) {
                filterProductsByCategory(categoryId);
            }

            @Override
            public void onCategoryClear() {
                resetProductFilter();
            }
        });
        categoryRecyclerView.setAdapter(categoryAdapter);
        recyclerView.setAdapter(productAdapter);

        // Initialize Retrofit and load products
        loadProductsFromApi(apiService);
        loadCategories(apiService);

        return root;
    }

    private void loadProductsFromApi(ApiService apiService) {
        // Fetch products from the API
        apiService.getProducts().enqueue(new Callback<List<Product>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Clear the current product list and add new products
                    productList.clear();
                    displayedProducts.clear();
                    productList.addAll(response.body());
                    displayedProducts.addAll(productList);
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

    private void loadCategories(ApiService apiService) {
        apiService.getCategories().enqueue(new Callback<List<Category>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryAdapter.updateCategoryList(response.body());
                    categoryAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to get categories", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void filterProductsByCategory(Integer categoryId) {
        displayedProducts.clear();
        if (categoryId == null) {
            displayedProducts.addAll(productList);
        } else {
            for (Product product : productList) {
                if (product.getCategory_id() == categoryId) {
                    displayedProducts.add(product);
                }
            }
        }
        productAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void resetProductFilter() {
        displayedProducts.clear();
        displayedProducts.addAll(productList);
        productAdapter.notifyDataSetChanged();
    }
}
