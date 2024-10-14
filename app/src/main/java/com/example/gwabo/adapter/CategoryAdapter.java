package com.example.gwabo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gwabo.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<String> categoryList;
    private Context context;
    private int selectedPosition = -1; // To track the selected category
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(String categoryName);
    }

    public CategoryAdapter(List<String> categoryList, Context context, OnCategoryClickListener listener) {
        this.categoryList = categoryList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_button, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String categoryName = categoryList.get(position);
        holder.categoryButton.setText(categoryName);

        // Update UI for selected category
        if (selectedPosition == position) {
            holder.categoryButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.main_500));
            holder.categoryButton.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            holder.categoryButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.transparent));
            holder.categoryButton.setTextColor(ContextCompat.getColor(context, R.color.text));
        }

        holder.categoryButton.setOnClickListener(view -> {
            selectedPosition = position;
            notifyDataSetChanged(); // Refresh the UI
            listener.onCategoryClick(categoryName); // Trigger the listener for category selection
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void updateCategoryList(List<String> newCategories) {
        this.categoryList = newCategories;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button categoryButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryButton = itemView.findViewById(R.id.category);
        }
    }
}
