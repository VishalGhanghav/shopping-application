package com.VishalGhanghav.orderservice.controller;


import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.VishalGhanghav.orderservice.dto.OrderRequest;
import com.VishalGhanghav.orderservice.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class orderController {
	
	//inject the orderService using constructor injection or @RequiredOrderSevrice
	private final OrderService orderService;

	//It will need some request body created to place order
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@CircuitBreaker(name = "inventory",fallbackMethod = "fallbackMethod")
	@TimeLimiter(name = "inventory")
	@Retry(name = "inventory")
	public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
		System.out.println(orderRequest.getOrderLineItemsDtoList()+"h");
		return CompletableFuture.supplyAsync((() -> orderService.placeOrder(orderRequest)));
	}
	
	public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest,RuntimeException runtimeException) {
		//SOme status message that will be there in circuit breaker to show to user.
		return CompletableFuture.supplyAsync(() ->"Oops! Something went wrong ,please order after some time" );
	}
}
