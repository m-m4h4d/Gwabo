package com.example.gwabo.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final String CART_PREFS = "cart_prefs";
    private static final String CART_KEY = "cart_key";

    private static List<Product> cart = new ArrayList<>();

    // Load cart from SharedPreferences
    public static void loadCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        String cartJson = prefs.getString(CART_KEY, null);
        if (cartJson != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Product>>() {}.getType();
            cart = gson.fromJson(cartJson, type);
        }
    }

    // Save cart to SharedPreferences
    public static void saveCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String cartJson = gson.toJson(cart);
        editor.putString(CART_KEY, cartJson);
        editor.apply();
    }

    public static void addProductToCart(Product product, Context context) {
        // Check if the product already exists in the cart
        boolean productExists = false;

        for (Product cartItem : cart) {
            if (cartItem.getId() == product.getId()) {  // Assuming you have a unique ID for products
                // Product already in the cart, so increase the quantity
                cartItem.setQuantity(cartItem.getQuantity() + product.getQuantity());
                productExists = true;
                Toast.makeText(context, "Increased " + product.getName() + " quantity in cart", Toast.LENGTH_SHORT).show();
                Log.d("CartManager", "Increased quantity of " + product.getName() + " to " + cartItem.getQuantity());
                break;
            }
        }

        if (!productExists) {
            // Product not in the cart, add it as a new entry
            cart.add(product);
            Log.d("CartManager", "Added " + product.getName() + " to cart");
        }
        saveCart(context);  // Save the cart whenever an item is added
    }

    public static List<Product> getCart() {
        return new ArrayList<>(cart);
    }

    public static void removeProductFromCart(Product product, Context context) {
        cart.remove(product);
        saveCart(context);  // Save the cart whenever an item is removed
    }

    public static int getCartSize() {
        return cart.size();
    }
}
