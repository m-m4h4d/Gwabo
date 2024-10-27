package com.example.gwabo.model;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private int id;
    private String name;
    private String imageUrl;

    public Restaurant(int id, String name, String imageUrl, double price, int stock, String description, String category, int quantity) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Restaurant(int id, String name, int image, double price, int stock, String description, String category) {
        this.id = id;
        this.name = name;
    }

    public Restaurant() {
        // Empty constructor required for Firebase
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
