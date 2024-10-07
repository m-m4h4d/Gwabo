package com.example.gwabo.network; // Use the correct package name for your project

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import com.example.gwabo.model.Product; // Import your Product model class

public interface ApiService {
    // Define your API call, using the correct endpoint
    @GET("db_access.php") // This is the endpoint on your server
    Call<List<Product>> getProducts();
    Call<List<String>> getCategories();// This method fetches a list of products
}
