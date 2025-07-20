package com.shop.client.client;

import com.google.protobuf.Empty;
import com.shop.product.Product;
import com.shop.product.ProductServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceClient {

    private final ProductServiceGrpc.ProductServiceBlockingStub stub;

    public ProductServiceClient(
            @Value("${grpc.product.host}") String host,
            @Value("${grpc.product.port}") int port) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        this.stub = ProductServiceGrpc.newBlockingStub(channel);
    }

    public List<Product.ProductResponse> getAllProducts() {
        return stub.getAllProduct(Empty.getDefaultInstance()).getProductResponseList();
    }

    public Empty createProduct(Product.CreateProductRequest request){
        return stub.createProduct(request);
    }

    public Empty updateProductStock(Product.ProductStockRequest request){
        return stub.updateProductStock(request);
    }

    public Empty deleteProduct(Product.ProductRequest request){
        return stub.deleteProduct(request);
    }
}
