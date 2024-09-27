package com.example.gwabo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gwabo.adapter.ProductAdapter;
import com.example.gwabo.model.Product;
import com.example.gwabo.R;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Load products from the database
        productList = loadProductsFromDatabase();
        productAdapter = new ProductAdapter(getContext(), productList);
        recyclerView.setAdapter(productAdapter);

        return view;
    }

    private List<Product> loadProductsFromDatabase() {
        List<Product> products = new ArrayList<>();
        // Add database logic here to retrieve products from your SQLite database
        // For now, let's assume we have hardcoded data
        products.add(new Product(1, "Tomatoes", "https://example.com/tomato.png"));
        products.add(new Product(2, "Malta", "https://example.com/malta.png"));
        products.add(new Product(3, "APKI", "https://example.com/apki.png"));
        products.add(new Product(4, "Atieke", "https://example.com/atieke.png"));

        return products;
    }
}
