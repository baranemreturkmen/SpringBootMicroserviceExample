package com.javaet.productservice.repository;

import com.javaet.productservice.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    //void saveAll(List<Product> products);

    //List<Product> findAll();
}
