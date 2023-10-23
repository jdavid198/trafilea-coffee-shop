package com.trafilea.test.coffeeshop.interfaces.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.trafilea.test.coffeeshop.application.dtos.ItemDto;
import com.trafilea.test.coffeeshop.application.dtos.QuantityDto;
import com.trafilea.test.coffeeshop.builder.CartBuilder;
import com.trafilea.test.coffeeshop.builder.ItemBuilder;
import com.trafilea.test.coffeeshop.domain.services.CartService;
import com.trafilea.test.coffeeshop.domain.services.ItemService;
import com.trafilea.test.coffeeshop.interfaces.dto.ResponseMessageDto;

@ExtendWith(MockitoExtension.class)
public class CartControllerTest {
	
	@Mock
	private CartService cartService;
	@Mock
	private ItemService itemService;	
	@InjectMocks
	private CartController cartController;

	@Nested
	class create{
		@Test
		public void createWithError() throws Exception {
			// Arrange
			when(cartService.create(any())).thenThrow(new Exception("Error de prueba"));
			// Act
			ResponseEntity<?> responseEntity = cartController.create(CartBuilder.getCartDto());
			// Assert
			ResponseMessageDto ResponseMessageDto=(ResponseMessageDto) responseEntity.getBody();
			assertEquals("Error de prueba",ResponseMessageDto.getMessage());
		}
		@Test
		public void createNotError() throws Exception {
			// Arrange
			Long cartId=1L;
			when(cartService.create(any())).thenReturn(cartId);
			// Act
			ResponseEntity<?> responseEntity = cartController.create(CartBuilder.getCartDto());
			// Assert
			ResponseMessageDto ResponseMessageDto=(ResponseMessageDto) responseEntity.getBody();
			assertEquals("The cart is created successfully, the cart id is:" + cartId, ResponseMessageDto.getMessage());
		}
	}
	
	@Nested
	class addItemsToCart{
		@Test
		public void createWithError() throws Exception {
			// Arrange
			Long cartId=1L;
			List<ItemDto> itemDtos=ItemBuilder.getItemDtos();
			doThrow(new Exception("Error de prueba")).when(itemService).addItemsToCart(any(),any());
			// Act
			ResponseEntity<?> responseEntity = cartController.addItemsToCart(cartId, itemDtos);
			// Assert
			ResponseMessageDto ResponseMessageDto=(ResponseMessageDto) responseEntity.getBody();
			assertEquals("Error de prueba",ResponseMessageDto.getMessage());
		}
		@Test
		public void createNotError() throws Exception {
			// Arrange
			Long cartId=1L;
			List<ItemDto> itemDtos=ItemBuilder.getItemDtos();
			// Act
			ResponseEntity<?> responseEntity = cartController.addItemsToCart(cartId, itemDtos);
			// Assert
			ResponseMessageDto ResponseMessageDto=(ResponseMessageDto) responseEntity.getBody();
			assertEquals("The products are added to the cart", ResponseMessageDto.getMessage());
		}
	}

	@Nested
	class updateQuantityByCartProduct{
		@Test
		public void createWithError() throws Exception {
			// Arrange
			Long cartId=1L;
			Long productId=1L;
			QuantityDto quantityDto=QuantityDto.builder().quantity(3).build();
			
			doThrow(new Exception("Error de prueba")).when(itemService).updateQuantityByCartProduct(any(),any(),any());
			// Act
			ResponseEntity<?> responseEntity = cartController.updateQuantityByCartProduct(cartId, productId, quantityDto);
			// Assert
			ResponseMessageDto ResponseMessageDto=(ResponseMessageDto) responseEntity.getBody();
			assertEquals("Error de prueba",ResponseMessageDto.getMessage());
		}
		@Test
		public void createNotError() throws Exception {
			// Arrange
			Long cartId=1L;
			Long productId=1L;
			QuantityDto quantityDto=QuantityDto.builder().quantity(3).build();
			// Act
			ResponseEntity<?> responseEntity = cartController.updateQuantityByCartProduct(cartId, productId, quantityDto);
			// Assert
			ResponseMessageDto ResponseMessageDto=(ResponseMessageDto) responseEntity.getBody();
			assertEquals("The quantity is updated successfully", ResponseMessageDto.getMessage());
		}
	}
	
}
