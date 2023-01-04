package com.VishalGhanghav.inventoryservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.VishalGhanghav.inventoryservice.dto.InventoryResponse;
import com.VishalGhanghav.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor//For constructor injection
@Slf4j
public class InventoryService {
	//Here I will query repo.SO use constructor injection of inventoryRepo
	private final InventoryRepository inventoryRepository;
	
	@Transactional(readOnly = true)//creates and commit transactions at runtime
	@SneakyThrows
	public List<InventoryResponse> isInStock(List<String> skuCode) {
		//We don't have method to find skuCode from jpa.Hence we will 
		//create method and at runtime jpa itself creates it for us
		//return inventoryRepository.findBySkuCode(skuCode).isPresent();
		//isPresent is method of optional.Return it
		//Not using this anymore
		
		/*//Had added for testing circuit breaker and distributed tracing
		 * //Find By SkuCode should return list of INventory 
		log.info("Wait Started");
		Thread.sleep(10000);
		log.info("Wait Ended");*/
		return inventoryRepository.findBySkuCodeIn(skuCode)
				.stream()
				.map(inventory-> 
					InventoryResponse.builder()
					.skuCode(inventory.getSkuCode())
					.isInSTock(inventory.getQuantity()>0)
					.build()
				).toList();
	}
}
