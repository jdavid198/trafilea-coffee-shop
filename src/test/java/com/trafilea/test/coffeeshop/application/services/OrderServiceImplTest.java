package com.trafilea.test.coffeeshop.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trafilea.test.coffeeshop.application.dtos.OrderDto;
import com.trafilea.test.coffeeshop.application.dtos.OrderTotalsDto;
import com.trafilea.test.coffeeshop.builder.CartBuilder;
import com.trafilea.test.coffeeshop.builder.ItemBuilder;
import com.trafilea.test.coffeeshop.builder.OrderBuilder;
import com.trafilea.test.coffeeshop.domain.entities.Item;
import com.trafilea.test.coffeeshop.domain.repositories.OrderRepository;
import com.trafilea.test.coffeeshop.domain.services.CartService;
import com.trafilea.test.coffeeshop.domain.services.ItemService;
import com.trafilea.test.coffeeshop.domain.services.SpecialRuleService;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
	
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private CartService cartService;
	@Mock
	private ItemService itemService;
	@Mock
	private SpecialRuleService specialRuleService;
	@InjectMocks
	private OrderServiceImpl OrderServiceImpl;

	
	@Nested
	class create{
		@Test
		public void cartIdNull() throws Exception {
			// Arrange
			OrderDto orderDto=OrderBuilder.getOrderDto();
			orderDto.setCartId(null);
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				OrderServiceImpl.create(orderDto);
			});
			// Assert
			assertEquals("You must send the cart.", exception.getMessage());
		}
		@Test
		public void cartNotFound() throws Exception {
			// Arrange
			OrderDto orderDto=OrderBuilder.getOrderDto();
			when(cartService.findById(any())).thenReturn(null);
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				OrderServiceImpl.create(orderDto);
			});
			// Assert
			assertEquals("You must send a valid cart.", exception.getMessage());
		}
		@Test
		public void orderFound() throws Exception {
			// Arrange
			OrderDto orderDto=OrderBuilder.getOrderDto();
			when(cartService.findById(any())).thenReturn(CartBuilder.getCart());
			when(orderRepository.findByCart(any())).thenReturn(OrderBuilder.getOrder());
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				OrderServiceImpl.create(orderDto);
			});
			// Assert
			assertTrue(exception.getMessage().contains("There is already an order created for that cart.The order is"));
		}
		@Test
		public void itemsNotFound() throws Exception {
			// Arrange
			OrderDto orderDto=OrderBuilder.getOrderDto();
			when(cartService.findById(any())).thenReturn(CartBuilder.getCart());
			when(orderRepository.findByCart(any())).thenReturn(null);
			when(itemService.findByCart(any())).thenReturn(null);
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				OrderServiceImpl.create(orderDto);
			});
			// Assert
			assertEquals("The items have not been added to the cart", exception.getMessage());
		}
		@Test
		public void createOrder() throws Exception {
			// Arrange
			OrderDto orderDto=OrderBuilder.getOrderDto();
			when(cartService.findById(any())).thenReturn(CartBuilder.getCart());
			when(orderRepository.findByCart(any())).thenReturn(null);
			
			List<Item> items= ItemBuilder.getItems();
			Item item=items.get(0);
			item.setDiscounts(5D);
			
			when(itemService.findByCart(any())).thenReturn(items);
			when(specialRuleService.ruleCategoryCoffee(any())).thenReturn(true);
			when(specialRuleService.ruleCategoryEquipment(any())).thenReturn(true);
			when(specialRuleService.ruleCategoryAccessories(any())).thenReturn(true);
			// Act
			OrderTotalsDto orderTotalsDto=OrderServiceImpl.create(orderDto);
			// Assert
			assertNotNull(orderTotalsDto);
		}
	}
	
}
