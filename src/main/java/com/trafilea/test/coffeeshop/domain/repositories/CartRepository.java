package com.trafilea.test.coffeeshop.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trafilea.test.coffeeshop.domain.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
}