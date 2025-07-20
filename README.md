# Grpc with Spring Boot

### Client Api Response
#### GetAllProduct
```bash
curl -plaintext -H "Content-Type: application/json" -d '{}' "http://localhost:8081/api/products"
```

### Product Service Grpcurl
```bash 
grpcurl.exe -plaintext -d "{}" localhost:1111 com.shop.product.ProductService/GetAllProduct
```