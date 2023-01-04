package com.VishalGhanghav.productservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.VishalGhanghav.productservice.dto.ProductRequest;
import com.VishalGhanghav.productservice.dto.ProductResponse;
import com.VishalGhanghav.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;

//As we are Exposing a rest api.We have to add restcontroller annotation
//and Request mapping annotation with value /api/product ie our link
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor//For constructor injection of product service
public class ProductController {
	//TO inject product service
	private final ProductService productService;
	
	
	//As it creates product I will be sending post request for this ie.PostMapping
	//Response status I will send as created
	//In the request I will recieve all product info like id,name desc,etc
	//Product Request class is like a dto which will store info in database
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createProduct(@RequestBody ProductRequest productRequest) {
		//The logic should be in service layer(not in controller)
		productService.createProduct(productRequest);
		//Now if we need to send response .Then create dto class for productResponse
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ProductResponse> getAllProducts(){
		return productService.getAllProducts();
	}
}
