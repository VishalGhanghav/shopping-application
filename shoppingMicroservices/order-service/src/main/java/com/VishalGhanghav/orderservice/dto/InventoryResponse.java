package com.VishalGhanghav.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {

	//skuCode data store here
	private String skuCode;
	private boolean isInSTock;
}
