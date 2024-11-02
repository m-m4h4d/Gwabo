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
import com.example.gwabo.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> categoryList;
    private final Context context;
    private final Integer selectedCategoryId = null; // To track the selected category
    private int selectedPosition = -1;
    private final OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(Integer categoryId);
        void onCategoryClear();
    }

    public CategoryAdapter(List<Category> categoryList, Context context, OnCategoryClickListener listener) {
        this.categoryList = categoryList != null ? categoryList : new ArrayList<>();
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
        Category category = categoryList.get(position);
        holder.categoryButton.setText(category.getName());

        if (selectedPosition == position) {
            holder.categoryButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.main_500));
            holder.categoryButton.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            holder.categoryButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.transparent));
            holder.categoryButton.setTextColor(ContextCompat.getColor(context, R.color.text));
        }

        holder.categoryButton.setOnClickListener(view -> {
            if (selectedPosition == position) {
                selectedPosition = -1;
                listener.onCategoryClear(); // Trigger the listener for category clear
            } else {
                selectedPosition = position;
                listener.onCategoryClick(category.getId());
            }
            notifyDataSetChanged(); // Refresh the UI
        });
    }

    @Override
    public int getItemCount() {
        return categoryList != null ? categoryList.size() : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateCategoryList(List<Category> newCategories) {
        this.categoryList = newCategories != null ? newCategories : new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button categoryButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryButton = itemView.findViewById(R.id.category);
        }
    }
}
