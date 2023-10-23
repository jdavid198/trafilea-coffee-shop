package com.trafilea.test.coffeeshop.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trafilea.test.coffeeshop.domain.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
}