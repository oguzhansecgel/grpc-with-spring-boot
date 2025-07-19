package com.shop.client.controller;

public class ProductDto {
    private long id ;
    private String title;
    private String description;
    private double price;
    private int stock;

    public ProductDto() {
    }

    public ProductDto(long id, String title, String description, double price, int stock) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
