package com.shop.product_service.service;

import com.google.protobuf.Empty;
import com.shop.product.Product;
import com.shop.product.ProductServiceGrpc;
import com.shop.product.ProductServiceGrpc.ProductServiceImplBase;
import com.shop.product_service.repository.ProductRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService extends ProductServiceImplBase {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void getAllProduct(Empty request, StreamObserver<Product.GetAllProductResponse> responseObserver) {
        List<com.shop.product_service.entity.Product> getAllProducts = productRepository.findAll();

        com.shop.product.Product.GetAllProductResponse.Builder builder = com.shop.product.Product.GetAllProductResponse.newBuilder();

        for (com.shop.product_service.entity.Product product : getAllProducts) {
            com.shop.product.Product.ProductResponse response = com.shop.product.Product.ProductResponse.newBuilder()
                    .setId(product.getId())
                    .setTitle(product.getTitle())
                    .setDescription(product.getDescription())
                    .setPrice(product.getPrice())
                    .setStock(product.getStock())
                    .build();

            builder.addProductResponse(response);
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
