package com.trafilea.test.coffeeshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trafilea.test.coffeeshop.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
}