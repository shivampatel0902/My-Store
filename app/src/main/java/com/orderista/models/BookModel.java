package com.orderista.models;

import com.google.gson.annotations.SerializedName;

public class BookModel {
    @SerializedName("id")
    private String id;

    @SerializedName("category")
    private String category;

    @SerializedName("name")
    private String name;

    @SerializedName("author")
    private String author;

    @SerializedName("description")
    private String description;


    @SerializedName("image")
    private String image;

    @SerializedName("published_year")
    private String publishedYear;

    @SerializedName("price")
    private String price;

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getPublishedYear() {
        return publishedYear;
    }

    public String getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPublishedYear(String publishedYear) {
        this.publishedYear = publishedYear;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
