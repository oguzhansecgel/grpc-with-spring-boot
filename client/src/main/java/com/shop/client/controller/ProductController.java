package com.shop.client.controller;

import com.shop.client.client.ProductServiceClient;
import com.shop.product.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductServiceClient productServiceClient;

    public ProductController(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        List<Product.ProductResponse> products = productServiceClient.getAllProducts();
        List<ProductDto> response  = products.stream()
                .map(product ->
                        new ProductDto(
                            product.getId(),
                            product.getTitle(),
                            product.getDescription(),
                            product.getPrice(),
                            product.getStock()
                        ))
                .collect(Collectors.toList());

        return response;
    }
}
