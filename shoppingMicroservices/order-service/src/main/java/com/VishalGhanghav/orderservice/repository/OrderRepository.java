package com.VishalGhanghav.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.VishalGhanghav.orderservice.model.Order;

//In order to save the order in database.We do this.
public interface OrderRepository extends JpaRepository<Order, Long>{

}
