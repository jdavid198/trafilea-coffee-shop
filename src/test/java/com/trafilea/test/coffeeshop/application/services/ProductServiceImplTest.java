package com.trafilea.test.coffeeshop.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trafilea.test.coffeeshop.builder.ProductBuilder;
import com.trafilea.test.coffeeshop.domain.entities.Product;
import com.trafilea.test.coffeeshop.domain.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
	
	@Mock
	private ProductRepository productRepository;
	@InjectMocks
	private ProductServiceImpl productServiceImpl;

	
	@Nested
	class findById{
		@Test
		public void productIdNull() {
			// Arrange
			Long productId=null;
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				productServiceImpl.findById(productId);
			});
			// Assert
			assertEquals("You must send the product.", exception.getMessage());
		}
		@Test
		public void optionalNull() throws Exception {
			// Arrange
			Long productId=1L;
			when(productRepository.findById(any())).thenReturn(null);
			// Act
			Product product=productServiceImpl.findById(productId);
			// Assert
			assertNull(product);
		}
		@Test
		public void findCart() throws Exception {
			// Arrange
			Long productId=1L;
			when(productRepository.findById(any())).thenReturn(Optional.of(ProductBuilder.getProduct()));
			// Act
			Product product=productServiceImpl.findById(productId);
			// Assert
			assertNotNull(product);
		}
	}
	
}
