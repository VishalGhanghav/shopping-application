package com.VishalGhanghav.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.VishalGhanghav.orderservice.dto.InventoryResponse;
import com.VishalGhanghav.orderservice.dto.OrderLineItemsDto;
import com.VishalGhanghav.orderservice.dto.OrderRequest;
import com.VishalGhanghav.orderservice.event.OrderPlacedEvent;
import com.VishalGhanghav.orderservice.model.Order;
import com.VishalGhanghav.orderservice.model.OrderLineItems;
import com.VishalGhanghav.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional//Spring framework automatically creates and commit the transactions
@Slf4j
public class OrderService {
	//We can inject the orderRepository in our Service
	//We can either create a constructor manually for injection or we can use
	//@Required Args Constructor
	private final OrderRepository orderRepository;
	private final WebClient.Builder webClientBuilder;
	//To add distributed tracing
	private final Tracer tracer;
	//Inject kafka template class 
	private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

	//We will get the order request from controller with request body having order request
	public String placeOrder(OrderRequest orderRequest) {
		Order order=new Order();
		order.setOrderNumber(UUID.randomUUID().toString());

		List<OrderLineItems> orderLineItems=orderRequest.getOrderLineItemsDtoList()//list<OrderLineItemsDto>
				.stream()//stream<OrderLineItemsDto>
				.map(this::mapToDto)//List<orderLIneItems>
				.toList();
		order.setOrderLineItemsList(orderLineItems);

		//Collect all skucodes from order objects
		List<String> skuCodes=order.getOrderLineItemsList().stream()
				.map(orderLineItem->orderLineItem.getSkuCode())
				.toList();

		//Just before calling inv service add distributed tracing logic
		//Add complete logic of calling inventory service in span try catch
		Span inventoryServiceLookup=tracer.nextSpan().name("InventoryServiceLookup");
		try(Tracer.SpanInScope spanInScope=tracer.withSpan(inventoryServiceLookup.start())){
			log.info("Calling Inventory Service");

			//Call Inventory Service and place an order if product is in stock
			//Onstead of boolean .I am expecting List<In
			InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
					.uri("http://inventory-service/api/inventory",
							uriBuilder->uriBuilder.queryParam("skuCode", skuCodes).build())
					.retrieve()
					.bodyToMono(InventoryResponse[].class)
					.block();

			//create stream from array to use all match function
			boolean allProductsinStock=Arrays.stream(inventoryResponseArray)
					.allMatch(inventoryResponse->inventoryResponse.isInSTock());
			if(allProductsinStock) {
				orderRepository.save(order);
				//Kafka code
				//Send order Number as a message to the queue.We know which was
				//the order no. that was created
				//kafkaTemplate.send("notficationTopic",order.getOrderNumber() )
				
				//Instead of this we will create a OrderPlaceEvent and send orderNumber as json
				kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()) );
				return "Order Placed Successfully";
			}else {
				throw new IllegalArgumentException("Product is not in stock ,please try again");
			}
		}finally{
			inventoryServiceLookup.end();
		}




	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems=new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}
}
