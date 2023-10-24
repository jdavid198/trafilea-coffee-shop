package com.trafilea.test.coffeeshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trafilea.test.coffeeshop.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
}