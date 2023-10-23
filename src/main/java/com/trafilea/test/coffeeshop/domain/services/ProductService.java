package com.trafilea.test.coffeeshop.domain.services;

import com.trafilea.test.coffeeshop.domain.entities.Product;

public interface ProductService{
	public Product findById(Long productId);
}