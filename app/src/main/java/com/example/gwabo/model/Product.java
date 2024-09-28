package com.example.gwabo.model;

public class Product {
    private int id;
    private String name;
    private String imageUrl;
    private int image;

    public Product(int id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Product(int id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Product() {
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

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }
}
