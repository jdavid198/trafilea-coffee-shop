package com.trafilea.test.coffeeshop.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trafilea.test.coffeeshop.builder.OrderBuilder;
import com.trafilea.test.coffeeshop.dtos.OrderDto;
import com.trafilea.test.coffeeshop.exceptions.ApiException;
import com.trafilea.test.coffeeshop.services.OrderService;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
	
	@Mock
	private OrderService orderService;
	@InjectMocks
	private OrderController orderController;

	@Nested
	class create{
		@Test
		public void createWithError() throws Exception {
			// Arrange
			when(orderService.create(any())).thenThrow(new ApiException(HttpStatus.BAD_REQUEST,"Error de prueba"));
			// Act
			ResponseEntity<?> responseEntity = orderController.create(OrderBuilder.getOrderRequestDto());
			// Assert
			assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
		}
		@Test
		public void createNotError() throws Exception {
			// Arrange
			OrderDto orderTotalsDto=OrderDto.builder()
											.orderID(1L)
											.totalDiscounts(0D)
											.totalOrder(0D)
											.totalProducts(0D)
											.totalShipping(0D)
											.build();
			when(orderService.create(any())).thenReturn(orderTotalsDto);
			// Act
			ResponseEntity<?> responseEntity = orderController.create(OrderBuilder.getOrderRequestDto());
			// Assert
			assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
		}
	}
	
}
