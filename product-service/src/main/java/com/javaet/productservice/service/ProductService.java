package com.javaet.productservice.service;

import com.javaet.productservice.dto.ProductRequest;
import com.javaet.productservice.dto.ProductResponse;
import com.javaet.productservice.entity.Product;
import com.javaet.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework. stereotype. Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService{
    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        /*The only difference between microservices-parent/product-service and
        * this project is jdk. This project use jdk17 other project use jdk20
        * why this happened question is big mystery :)*/
        List<Product> products = new ArrayList<>();
        products.add(product);

        productRepository.saveAll(products);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }
    private ProductResponse mapToProductResponse (Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
