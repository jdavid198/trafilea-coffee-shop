package com.trafilea.test.coffeeshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trafilea.test.coffeeshop.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}