package com.example.gwabo.model;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private int id;
    private String name;
    private String image_url;

    public Restaurant(int id, String name, String image_url) {
        this.id = id;
        this.name = name;
        this.image_url = image_url;
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
        return image_url;
    }

    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }

}
