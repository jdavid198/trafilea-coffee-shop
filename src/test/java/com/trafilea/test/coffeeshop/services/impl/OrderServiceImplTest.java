package com.trafilea.test.coffeeshop.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trafilea.test.coffeeshop.builder.CartBuilder;
import com.trafilea.test.coffeeshop.builder.ItemBuilder;
import com.trafilea.test.coffeeshop.builder.OrderBuilder;
import com.trafilea.test.coffeeshop.controller.dtos.OrderRequestDto;
import com.trafilea.test.coffeeshop.dtos.OrderDto;
import com.trafilea.test.coffeeshop.entities.Item;
import com.trafilea.test.coffeeshop.exceptions.ApiException;
import com.trafilea.test.coffeeshop.mappers.CartMapper;
import com.trafilea.test.coffeeshop.repositories.CartRepository;
import com.trafilea.test.coffeeshop.repositories.ItemRepository;
import com.trafilea.test.coffeeshop.repositories.OrderRepository;
import com.trafilea.test.coffeeshop.services.CalculatorService;
import com.trafilea.test.coffeeshop.services.RuleService;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
	
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private CartRepository cartRepository;
	@Mock
	private ItemRepository itemRepository;
	@Mock
	private CartMapper cartMapper;
	@Mock
	private RuleService ruleService;
	@Mock
	private CalculatorService calculatorService;
	@InjectMocks
	private OrderServiceImpl OrderServiceImpl;

	
	@Nested
	class create{
		@Test
		public void cartNotFound() throws Exception {
			// Arrange
			OrderRequestDto orderDto=OrderBuilder.getOrderRequestDto();
			when(cartRepository.findById(any())).thenReturn(Optional.empty());
			// Act
			ApiException apiException = assertThrows(ApiException.class, () -> {
				OrderServiceImpl.create(orderDto);
			});
			// Assert
			assertEquals("You must send a valid cart.", apiException.getException());
		}
		@Test
		public void orderFound() throws Exception {
			// Arrange
			OrderRequestDto orderDto=OrderBuilder.getOrderRequestDto();
			when(cartRepository.findById(any())).thenReturn(Optional.of(CartBuilder.getCart()));
			when(orderRepository.findByCart(any())).thenReturn(Optional.of(OrderBuilder.getOrder()));
			// Act
			ApiException apiException = assertThrows(ApiException.class, () -> {
				OrderServiceImpl.create(orderDto);
			});
			// Assert
			assertTrue(apiException.getException().contains("There is already an order created for that cart.The order is"));
		}
		@Test
		public void itemsNotFound() throws Exception {
			// Arrange
			OrderRequestDto orderDto=OrderBuilder.getOrderRequestDto();
			when(cartRepository.findById(any())).thenReturn(Optional.of(CartBuilder.getCart()));
			when(orderRepository.findByCart(any())).thenReturn(Optional.empty());
			when(itemRepository.findByCart(any())).thenReturn(null);
			// Act
			ApiException apiException = assertThrows(ApiException.class, () -> {
				OrderServiceImpl.create(orderDto);
			});
			// Assert
			assertEquals("The items have not been added to the cart", apiException.getException());
		}
		@Test
		public void createOrder() throws Exception {
			// Arrange
			OrderRequestDto orderDto=OrderBuilder.getOrderRequestDto();
			when(cartRepository.findById(any())).thenReturn(Optional.of(CartBuilder.getCart()));
			when(orderRepository.findByCart(any())).thenReturn(Optional.empty());
			
			List<Item> items= ItemBuilder.getItems();
			Item item=items.get(0);
			item.setDiscounts(5D);
			
			when(itemRepository.findByCart(any())).thenReturn(items);
			when(calculatorService.calculateOrder(any())).thenReturn(OrderBuilder.getOrderDto());
			when(ruleService.ruleCategoryCoffee(any())).thenReturn(true);
			// Act
			OrderDto orderTotalsDto=OrderServiceImpl.create(orderDto);
			// Assert
			assertNotNull(orderTotalsDto);
		}
	}
	
}
