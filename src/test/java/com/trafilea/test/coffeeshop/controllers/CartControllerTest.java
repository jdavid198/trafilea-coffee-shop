package com.trafilea.test.coffeeshop.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trafilea.test.coffeeshop.builder.CartBuilder;
import com.trafilea.test.coffeeshop.builder.ItemBuilder;
import com.trafilea.test.coffeeshop.controller.dtos.ItemRequestDto;
import com.trafilea.test.coffeeshop.controller.dtos.QuantityRequestDto;
import com.trafilea.test.coffeeshop.exceptions.ApiException;
import com.trafilea.test.coffeeshop.services.CartService;
import com.trafilea.test.coffeeshop.services.ItemService;

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
			when(cartService.create(any())).thenThrow(new ApiException(HttpStatus.BAD_REQUEST,"Error de prueba"));
			// Act
			ResponseEntity<?> responseEntity = cartController.create(CartBuilder.getCartRequestDto());
			// Assert
			assertNotNull(responseEntity);
			assertEquals(responseEntity.getStatusCode(),HttpStatus.BAD_REQUEST);
		}
		@Test
		public void createNotError() throws Exception {
			// Arrange
			when(cartService.create(any())).thenReturn(CartBuilder.getCartDto());
			// Act
			ResponseEntity<?> responseEntity = cartController.create(CartBuilder.getCartRequestDto());
			// Assert
			assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		}
	}
	
	@Nested
	class addItemsToCart{
		@Test
		public void cartIdNull() throws ApiException {
			// Arrange
			Long cartId=0L;
			List<ItemRequestDto> itemDtos=ItemBuilder.getItemRequestDtos();
			// Act
			ResponseEntity<?> responseEntity = cartController.addItemsToCart(cartId,itemDtos);
			// Assert
			assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		}
		@Test
		public void itemDtosNull() throws ApiException {
			// Arrange
			Long cartId=1L;
			List<ItemRequestDto> itemDtos=null;
			// Act
			ResponseEntity<?> responseEntity = cartController.addItemsToCart(cartId,itemDtos);
			// Assert
			assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		}
		@Test
		public void addItemsToCartCreate() throws ApiException {
			// Arrange
			Long cartId=1L;
			List<ItemRequestDto> itemDtos=ItemBuilder.getItemRequestDtos();
			// Act
			ResponseEntity<?> responseEntity = cartController.addItemsToCart(cartId,itemDtos);
			// Assert
			assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		}
	}

	@Nested
	class updateQuantityByCartProduct{
		@Test
		public void editWithError() throws Exception {
			// Arrange
			Long cartId=1L;
			Long productId=1L;
			QuantityRequestDto quantityDto=QuantityRequestDto.builder().quantity(3).build();
			
			doThrow(new ApiException(HttpStatus.BAD_REQUEST ,"Error de prueba")).when(itemService).updateQuantityByCartItem(any(),any(),any());
			// Act
			ResponseEntity<?> responseEntity = cartController.updateQuantityByCartProduct(cartId, productId, quantityDto);
			// Assert
			assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
		}
		@Test
		public void editNotError() throws Exception {
			// Arrange
			Long cartId=1L;
			Long productId=1L;
			QuantityRequestDto quantityDto=QuantityRequestDto.builder().quantity(3).build();
			// Act
			ResponseEntity<?> responseEntity = cartController.updateQuantityByCartProduct(cartId, productId, quantityDto);
			// Assert
			assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		}
	}
	
}
