package com.shop.product_service;

import com.shop.product_service.service.ProductService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GrpcConfig {

    @Value("${grpc.port}")
    private int port;

    @Bean
    public Server grpcServer(ProductService productService) throws IOException {
        Server server = ServerBuilder.forPort(port)
                .addService(productService)
                .build()
                .start();


        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));

        return server;
    }
}
