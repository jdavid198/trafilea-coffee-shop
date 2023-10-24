package com.trafilea.test.coffeeshop.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trafilea.test.coffeeshop.entities.Cart;
import com.trafilea.test.coffeeshop.enums.StatusEnum;

public interface CartRepository extends JpaRepository<Cart, Long>{
	public Optional<Cart> findByUserIDAndStatus(Long userID,StatusEnum status);
}