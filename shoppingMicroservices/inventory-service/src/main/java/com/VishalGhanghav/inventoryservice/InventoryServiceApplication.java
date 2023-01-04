package com.VishalGhanghav.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.VishalGhanghav.inventoryservice.model.Inventory;
import com.VishalGhanghav.inventoryservice.repository.InventoryRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	//We need to have data in database to make this service work
	//Add inventoryRepo using method injection
	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		//As part of this bean we will create some values in t_inventory table.
		
		return args->{
			Inventory inventory=new Inventory();
			inventory.setSkuCode("iqoo_z3");
			inventory.setQuantity(100);
			
			Inventory inventory2=new Inventory();
			inventory2.setSkuCode("iqoo_z3_red");
			inventory2.setQuantity(0);
			
			Inventory inventory3=new Inventory();
			inventory3.setSkuCode("iqoo_z3_black");
			inventory3.setQuantity(1);
			
			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory2);
			inventoryRepository.save(inventory3);
		};
	}
}
