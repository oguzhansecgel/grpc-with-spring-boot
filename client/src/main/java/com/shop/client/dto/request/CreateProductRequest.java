package com.shop.client.dto.request;

public record CreateProductRequest(
        String title,
        String description,
        Double price,
        int stock
) {
}
