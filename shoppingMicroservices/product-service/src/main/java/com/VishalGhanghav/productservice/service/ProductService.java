package com.VishalGhanghav.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.VishalGhanghav.productservice.dto.ProductRequest;
import com.VishalGhanghav.productservice.dto.ProductResponse;
import com.VishalGhanghav.productservice.model.Product;
import com.VishalGhanghav.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//As it is a service class .Write @Service annotation
@Service
@RequiredArgsConstructor//This will create product repo constructor.ie constructor injection
@Slf4j
public class ProductService {

	private final ProductRepository productRepository;
	
	public void createProduct(ProductRequest productRequest) {
		//Normally we would use Builder design pattern to build this product.
		//Here we have shortcut
		Product product=Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.build();
		//To save in database,inject product repository.Service annotion provide save functionality.
		
		productRepository.save(product);
		//To add some logs use .SLF4j
		//log.info("Product"+product.getId()+" is Saved");
		//or
		log.info("Product {} is saved",product.getId());
	}

	public List<ProductResponse> getAllProducts() {
		List<Product> products=productRepository.findAll();
		//Map each product into a product response object and then convert it to list and return that list
		return products.stream().map(product-> mapToProductResponse(product)).toList();
		//or we can use method reference products.stream().map(this::mapToProductResponse(product)).toList();
	}

	private ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.build();
	}
}
