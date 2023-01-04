package com.VishalGhanghav.productservice.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
//Also we will add lombok annotations to generate getter setter methods,constructors,etc
//As we are defining this Product in Mongo Db as Document use document annotaion
@Document(value="product")
@AllArgsConstructor
@NoArgsConstructor
@lombok.Builder
@lombok.Data
public class Product {
	//Id annotation will tel my database that this is unique id
	@Id
	private String id;
	private String name;
	private String description;
	private BigDecimal price;
}
