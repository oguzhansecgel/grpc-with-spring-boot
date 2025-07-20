package com.shop.client.controller;

import com.shop.client.client.ProductServiceClient;
import com.shop.client.dto.request.CreateProductRequest;
import com.shop.client.dto.request.UpdateProductStockRequest;
import com.shop.product.Product;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductServiceClient productServiceClient;
    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        List<Product.ProductResponse> products = productServiceClient.getAllProducts();
        List<ProductDto> response = products.stream()
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

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") long productId) {
        try {
            logger.warn("Deleting product with id {}", productId);
            Product.ProductRequest.Builder builder = Product.ProductRequest.newBuilder();
            builder.setId(productId);
            productServiceClient.deleteProduct(builder.build());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/update-stock")
    public ResponseEntity<Void> updateProductStock(@RequestBody UpdateProductStockRequest request) {
        try {
            logger.info("incoming request for update-stock endpoint: {} ", request);

            Product.ProductStockRequest.Builder builder = Product.ProductStockRequest.newBuilder();
            builder.setId(request.id());
            builder.setStock(request.stock());
            productServiceClient.updateProductStock(builder.build());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createProduct(@RequestBody CreateProductRequest productDto) {
        try {
            logger.info("incoming request for creat endpoint: {} ", productDto);
            Product.CreateProductRequest request = Product.CreateProductRequest.newBuilder()
                    .setTitle(productDto.title())
                    .setDescription(productDto.description())
                    .setPrice(productDto.price())
                    .setStock(productDto.stock())
                    .build();

            productServiceClient.createProduct(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
