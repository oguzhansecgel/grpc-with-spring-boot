package com.shop.product_service.service;

import com.google.protobuf.Empty;
import com.shop.product.Product;
import com.shop.product.ProductServiceGrpc;
import com.shop.product.ProductServiceGrpc.ProductServiceImplBase;
import com.shop.product_service.repository.ProductRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService extends ProductServiceImplBase {

    private final ProductRepository productRepository;
    private Logger logger = LoggerFactory.getLogger(ProductService.class);

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

    @Override
    public void updateProductStock(Product.ProductStockRequest request, StreamObserver<Empty> responseObserver) {
        try {
            Optional<com.shop.product_service.entity.Product> existProduct = productRepository.findById(request.getId());
            if (existProduct.isEmpty()) {
                logger.warn("Product with id {} not found", request.getId());

                responseObserver.onError(
                        Status.NOT_FOUND
                                .withDescription("Ürün bulunamadı: id=" + request.getId())
                                .asRuntimeException()
                );
                return;
            }
            com.shop.product_service.entity.Product product = existProduct.get();
            product.setStock(request.getStock());
            productRepository.save(product);
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("updateProductStock() -> Hata oluştu: {}", e.getMessage(), e);
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Ürün stoğu güncellenirken sunucu hatası")
                            .augmentDescription(e.getMessage())
                            .withCause(e)
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void createProduct(Product.CreateProductRequest request, StreamObserver<Empty> responseObserver) {
        try {
            com.shop.product_service.entity.Product product = new com.shop.product_service.entity.Product();
            product.setTitle(request.getTitle());
            product.setDescription(request.getDescription());
            product.setPrice(request.getPrice());
            product.setStock(request.getStock());

            productRepository.save(product);

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

        } catch (Exception e) {
            logger.error("createProduct() -> Hata oluştu: {}", e.getMessage(), e);
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Ürün oluşturulurken sunucu hatası")
                            .augmentDescription(e.getMessage())
                            .withCause(e)
                            .asRuntimeException()
            );
        }
    }
}
