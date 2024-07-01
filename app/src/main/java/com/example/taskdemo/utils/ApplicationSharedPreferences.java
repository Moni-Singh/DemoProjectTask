package com.example.taskdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.taskdemo.model.response.Product;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class ApplicationSharedPreferences {
    private static final String APPLICATION_PREFERENCES_NAME = "sharedPrefs";
    private static final String KEY_TOKEN = "Token";
    private static final String KEY_BOOKMARKED_IDS = "bookmarked_ids";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public ApplicationSharedPreferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
        this.gson = new Gson();
    }

    // Method to save the token
    public void saveToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    // Method to retrieve the token
    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public void clearToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_TOKEN);
        editor.apply();
    }

    // Method to save a product
    public void saveProduct(Product product) {
        String json = gson.toJson(product);
        editor.putString(Constants.PRODUCT + product.getId(), json);

        // Save the list of bookmarked product IDs
        Set<String> bookmarkedIds = sharedPreferences.getStringSet(KEY_BOOKMARKED_IDS, new HashSet<>());
        bookmarkedIds.add(String.valueOf(product.getId()));
        editor.putStringSet(KEY_BOOKMARKED_IDS, bookmarkedIds);

        editor.apply();
    }

    public boolean isProductBookmarked(int productId) {
        Set<String> bookmarkedProducts = sharedPreferences.getStringSet(Constants.PRODUCT , new HashSet<>());
        return bookmarkedProducts.contains(String.valueOf(productId));
    }

    // Method to retrieve a product by ID
    public Product getProductById(String productId) {
        String json = sharedPreferences.getString(Constants.PRODUCT + productId, null);
        return json != null ? gson.fromJson(json, Product.class) : null;
    }

    // Method to retrieve all bookmarked products
    public Set<Product> getAllBookmarkedProducts() {
        Set<String> bookmarkedIds = sharedPreferences.getStringSet(KEY_BOOKMARKED_IDS, new HashSet<>());
        Set<Product> products = new HashSet<>();
        for (String id : bookmarkedIds) {
            Product product = getProductById(id);
            if (product != null) {
                products.add(product);
            }
        }
        return products;
    }

    // Method to remove a bookmarked product
    public void removeBookmarkedProduct(Product product) {
        editor.remove(Constants.PRODUCT + product.getId());
        Set<String> bookmarkedIds = sharedPreferences.getStringSet(KEY_BOOKMARKED_IDS, new HashSet<>());
        bookmarkedIds.remove(String.valueOf(product.getId()));
        editor.putStringSet(KEY_BOOKMARKED_IDS, bookmarkedIds);

        editor.apply();
    }

    // Method to add a product to bookmarks
    public void addBookmarkedProduct(Product product) {
        saveProduct(product);
    }
}
