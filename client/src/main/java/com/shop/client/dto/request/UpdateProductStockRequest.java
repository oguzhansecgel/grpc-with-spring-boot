package com.shop.client.dto.request;

public record UpdateProductStockRequest(
        long id,
        int stock
) {
}
