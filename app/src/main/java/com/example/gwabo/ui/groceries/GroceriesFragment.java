package com.example.gwabo.ui.groceries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.gwabo.R;
import com.example.gwabo.adapter.GroceryAdapter;
import com.example.gwabo.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GroceriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private GroceryAdapter adapter;
    private List<Product> groceriesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_groceries, container, false);

        recyclerView = root.findViewById(R.id.groceries_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        groceriesList = loadGroceries();
        adapter = new GroceryAdapter(groceriesList, getContext());
        recyclerView.setAdapter(adapter);

        return root;
    }

    private List<Product> loadGroceries() {
        List<Product> groceries = new ArrayList<>();
        groceries.add(new Product(1, "Tomatoes", R.drawable.tomato, 2.50, 10, "Fresh and juicy tomatoes"));
        groceries.add(new Product(2, "Malta", R.drawable.malta, 3.00, 5, "Crisp and flavorful malt"));
        groceries.add(new Product(3, "APKI", R.drawable.apki, 4.20, 8, "Delicious spices"));
        groceries.add(new Product(4, "Atieke", R.drawable.attieke, 1.99, 15, "Fresh spices"));
        groceries.add(new Product(5, "African Khebab Pepper", R.drawable.pepper, 4.33, 12, "Spicy and flavorful"));
        groceries.add(new Product(6, "Canned Beef", R.drawable.can, 6.99, 20, "Canned and ready-to-eat"));

        return groceries;
    }
}
