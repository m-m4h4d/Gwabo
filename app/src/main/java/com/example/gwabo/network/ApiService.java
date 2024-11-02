package com.example.gwabo.network;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

import com.example.gwabo.model.Category;
import com.example.gwabo.model.Product;
import com.example.gwabo.model.Restaurant;

public interface ApiService {
    @GET("get_products.php")
    Call<List<Product>> getProducts();
    @GET("get_restaurants.php")
    Call<List<Restaurant>> getRestaurants();
    @GET("get_categories.php")
    Call<List<Category>> getCategories();
}
