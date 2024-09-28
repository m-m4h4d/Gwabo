package com.example.gwabo.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gwabo.ProductDetailActivity;
import com.example.gwabo.R;
import com.example.gwabo.adapter.ProductAdapter;
import com.example.gwabo.model.Product;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the RecyclerView
        recyclerView = root.findViewById(R.id.recyclerViewProducts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));  // 2 columns for grid layout

        // Load product data
        productList = loadProductsFromDatabase();

        // Set the adapter
        adapter = new ProductAdapter(productList, product -> {
            // Handle product click
            Intent intent = new Intent(getContext(), ProductDetailActivity.class);
            intent.putExtra("product", product);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        return root;
    }

    private List<Product> loadProductsFromDatabase() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Tomatoes", R.drawable.tomato, 14.99, 10, "The tomato fruit is globular or ovoid. Botanically, the fruit exhibits all of the common characteristics of berries; a simple fleshy fruit that encloses its seed in the pulp."));
        products.add(new Product(2, "Malta", R.drawable.malta, 10.99, 5, "Delicious spices"));
        products.add(new Product(3, "APKI", R.drawable.apki, 12.99, 8, "Hair products"));
        products.add(new Product(4, "Atieke", R.drawable.attieke, 15.99, 12, "Cosmetics items"));
        return products;
    }
}
