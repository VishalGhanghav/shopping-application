package com.VishalGhanghav.inventoryservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.VishalGhanghav.inventoryservice.dto.InventoryResponse;
import com.VishalGhanghav.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;
	
	
	//http://localhost:8082/api/inventory/iqoo_z3,iqoo_z3_black:Path Variable
	
	//http://localhost:8082/api/inventory?skuCode:iqoo_z3&skuCode=iqoo_z3_black: Request Param
	//So request param is better
	
	//This method takes a sku code and returns true if it's present.
	//Here we will use pathVariable not requestbody
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
		//Here we query database for to check inventory.That's done in service
		return inventoryService.isInStock(skuCode);
		//To make this work we need to have data in database.
		//SO we create bean in INventoryServiceApplication
	}
}
