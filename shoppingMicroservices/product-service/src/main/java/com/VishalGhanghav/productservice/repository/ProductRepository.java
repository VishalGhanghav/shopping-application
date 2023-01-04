package com.VishalGhanghav.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.VishalGhanghav.productservice.model.Product;
//We will be storing data in key value format in mongodb.Product :key ,value :id
public interface ProductRepository extends MongoRepository<Product,String > {

}
