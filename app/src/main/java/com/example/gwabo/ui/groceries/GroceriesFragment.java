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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gwabo.R;
import com.example.gwabo.adapter.CategoryAdapter;
import com.example.gwabo.adapter.GroceryAdapter;
import com.example.gwabo.model.Product;
import com.example.gwabo.network.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GroceriesFragment extends Fragment {

    private RecyclerView recyclerView, categoryRecyclerView;
    private GroceryAdapter groceryAdapter;
    private List<Product> productList;
    private List<String> categoryList;
    private CategoryAdapter categoryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_groceries, container, false);

        // Initialize RecyclerView
        recyclerView = root.findViewById(R.id.groceries_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Initialize Gson
        Gson gson = new GsonBuilder().setLenient().create();

        // Initialize Retrofit
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wavemediatech.ca/") // Replace with your domain
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        productList = new ArrayList<>();
        groceryAdapter = new GroceryAdapter(productList, getContext());
        recyclerView.setAdapter(groceryAdapter);

        categoryRecyclerView = root.findViewById(R.id.categories);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categoryList, getContext(), this::filterProductsByCategory);
        categoryRecyclerView.setAdapter(categoryAdapter);

        loadGroceries(apiService);

        return root;
    }

    private void loadGroceries(ApiService apiService) {
        apiService.getProducts().enqueue(new Callback<List<Product>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                        productList.clear();
                        productList.addAll(response.body());
                        Set<String> categoriesSet = new HashSet<>();
                        for (Product product : productList) {
                            categoriesSet.add(product.getCategoryName());
                        }
                        List<String> uniqueCategories = new ArrayList<>(categoriesSet);
                        categoryAdapter.updateCategoryList(uniqueCategories);
                        categoryAdapter.notifyDataSetChanged();
                        categoryList.clear();
                        categoryList.addAll(categoriesSet);
                        groceryAdapter.notifyDataSetChanged();
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

    @SuppressLint("NotifyDataSetChanged")
    private void filterProductsByCategory(String selectedCategoryName) {
        Toast.makeText(getContext(), "Selected Category: " + selectedCategoryName, Toast.LENGTH_SHORT).show();

        List<Product> filteredList = new ArrayList<>();
        for (Product product : productList) {
            if (product.getCategoryName().equals(selectedCategoryName)) {
                filteredList.add(product);
            }
        }

        groceryAdapter.updateProductList(filteredList);
        groceryAdapter.notifyDataSetChanged();
    }
}
