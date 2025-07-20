# Grpc with Spring Boot

### Client Api Response
#### GetAllProduct
```bash
curl -X GET -H "Accept: application/json" "http://localhost:8081/api/products"
```

#### CreateProduct
```bash
curl -X POST http://localhost:8081/api/products/create \
  -H "Content-Type: application/json" \
  -d '{
  "title": "Ultra Gaming Mouse",
  "description": "Yuksek hassasiyetli oyuncu faresi",
  "price": 899.99,
  "stock": 150
}'
```

#### UpdateProductStock
```bash
curl -X POST http://localhost:8081/api/products/update-stock \
  -H "Content-Type: application/json" \
  -d '{
    "id":1,
    "stock":2
    }'
```

#### DeleteProduct
```bash
curl -X DELETE http://localhost:8081/api/products/delete-product/1
```

### Product Service Grpcurl
```bash 
grpcurl.exe -plaintext -d "{}" localhost:1111 com.shop.product.ProductService/GetAllProduct
```