package com.trafilea.test.coffeeshop.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trafilea.test.coffeeshop.domain.entities.Cart;
import com.trafilea.test.coffeeshop.domain.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	public Order findByCart(Cart cart);
}