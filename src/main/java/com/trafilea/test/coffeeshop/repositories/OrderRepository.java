package com.trafilea.test.coffeeshop.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trafilea.test.coffeeshop.entities.Cart;
import com.trafilea.test.coffeeshop.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	public Optional<Order> findByCart(Cart cart);
}