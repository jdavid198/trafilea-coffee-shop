package com.trafilea.test.coffeeshop.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trafilea.test.coffeeshop.domain.entities.Cart;
import com.trafilea.test.coffeeshop.domain.enums.StatusEnum;

public interface CartRepository extends JpaRepository<Cart, Long>{
	public Cart findByUserIDAndStatus(Long userID,StatusEnum status);
}