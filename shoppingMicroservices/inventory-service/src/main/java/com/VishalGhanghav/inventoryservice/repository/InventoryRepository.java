package com.VishalGhanghav.inventoryservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.VishalGhanghav.inventoryservice.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{

	//We use Optional to check if present or not
	//Optional<Inventory> findBySkuCode(List<String> skuCode);

	List<Inventory> findBySkuCodeIn(List<String> skuCode);

}
