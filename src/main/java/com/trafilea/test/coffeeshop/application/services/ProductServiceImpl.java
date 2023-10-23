package com.trafilea.test.coffeeshop.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.trafilea.test.coffeeshop.domain.entities.Product;
import com.trafilea.test.coffeeshop.domain.repositories.ProductRepository;
import com.trafilea.test.coffeeshop.domain.services.ProductService;

import lombok.RequiredArgsConstructor;

@Scope("singleton")
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
	
	private static final String MSG_MUST_SEND_PRODUCT = "You must send the product.";
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product findById(Long productId) throws Exception {
		if (productId==null) {
			throw new Exception(MSG_MUST_SEND_PRODUCT);
		}
		Optional<Product>optional=productRepository.findById(productId);
		if (optional==null || optional.isEmpty()) {
			return null;
		}
		return optional.get();
	}
	
}
