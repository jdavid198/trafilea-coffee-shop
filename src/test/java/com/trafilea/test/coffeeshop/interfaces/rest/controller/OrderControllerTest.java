package com.trafilea.test.coffeeshop.interfaces.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.trafilea.test.coffeeshop.application.dtos.OrderTotalsDto;
import com.trafilea.test.coffeeshop.builder.OrderBuilder;
import com.trafilea.test.coffeeshop.domain.services.OrderService;
import com.trafilea.test.coffeeshop.interfaces.dto.ResponseMessageDto;

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
			when(orderService.create(any())).thenThrow(new Exception("Error de prueba"));
			// Act
			ResponseEntity<?> responseEntity = orderController.create(OrderBuilder.getOrderDto());
			// Assert
			ResponseMessageDto ResponseMessageDto=(ResponseMessageDto) responseEntity.getBody();
			assertEquals("Error de prueba",ResponseMessageDto.getMessage());
		}
		@Test
		public void createNotError() throws Exception {
			// Arrange
			OrderTotalsDto orderTotalsDto=OrderTotalsDto.builder()
											.orderID(1L)
											.totalDiscounts(0D)
											.totalOrder(0D)
											.totalProducts(0D)
											.totalShipping(0D)
											.build();
			when(orderService.create(any())).thenReturn(orderTotalsDto);
			// Act
			ResponseEntity<?> responseEntity = orderController.create(OrderBuilder.getOrderDto());
			// Assert
			ResponseMessageDto ResponseMessageDto=(ResponseMessageDto) responseEntity.getBody();
			assertEquals("The order is created successfully, the order id is:"+1, ResponseMessageDto.getMessage());
		}
	}
	
}
