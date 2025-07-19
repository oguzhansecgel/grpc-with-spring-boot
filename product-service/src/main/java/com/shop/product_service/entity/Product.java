package com.shop.product_service.entity;

import jakarta.persistence.*;

@Table(name = "products")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private Double price;
    private int stock;

    public Product() {
    }

    public Product(long id, String title, String description, Double price, int stock) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
