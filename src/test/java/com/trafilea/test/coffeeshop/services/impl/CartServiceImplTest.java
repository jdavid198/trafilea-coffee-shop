package com.trafilea.test.coffeeshop.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.trafilea.test.coffeeshop.controller.dtos.CartRequestDto;
import com.trafilea.test.coffeeshop.controller.dtos.ItemRequestDto;
import com.trafilea.test.coffeeshop.dtos.CartDto;
import com.trafilea.test.coffeeshop.exceptions.ApiException;
import com.trafilea.test.coffeeshop.mappers.CartMapper;
import com.trafilea.test.coffeeshop.repositories.CartRepository;
import com.trafilea.test.coffeeshop.services.ItemService;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {
	
	@Mock
	private CartMapper cartMapper;
	@Mock
	private CartRepository cartRepository;
	@Mock
	private ItemService itemService;
	@InjectMocks
	private CartServiceImpl cartServiceImpl;

	@Nested
	public class create{
		@Test
		public void cartOpen() {
			// Arrange
			Long userID=124333344L;
			CartRequestDto cartDto=CartRequestDto.builder().userID(userID).build();
			when(cartRepository.findByUserIDAndStatus(any(),any())).thenReturn(Optional.of(CartBuilder.getCart()));
			// Act
			ApiException apiException = assertThrows(ApiException.class, () -> {
				cartServiceImpl.create(cartDto);
			});
			// Assert
			assertEquals("You have a shopping cart open.", apiException.getException());
		}
		@Test
		public void createCart() throws ApiException {
			// Arrange
			Long userID=124333344L;
			List<ItemRequestDto> itemsRequestDtos=ItemBuilder.getItemRequestDtos();
			CartRequestDto cartRequestDto=CartRequestDto.builder().userID(userID).items(itemsRequestDtos).build();
			when(cartRepository.findByUserIDAndStatus(any(),any())).thenReturn(Optional.empty());
			when(cartRepository.save(any())).thenReturn(CartBuilder.getCart());
			when(itemService.addItemsToCart(any(),any())).thenReturn(CartBuilder.getCartDto());
			// Act
			CartDto cartDto=cartServiceImpl.create(cartRequestDto);
			// Assert
			assertNotNull(cartDto);
		}
		@Test
		public void createCartNoItems() throws ApiException {
			// Arrange
			Long userID=124333344L;
			List<ItemRequestDto> itemsRequestDtos=null;
			CartRequestDto cartRequestDto=CartRequestDto.builder().userID(userID).items(itemsRequestDtos).build();
			when(cartRepository.findByUserIDAndStatus(any(),any())).thenReturn(Optional.empty());
			when(cartRepository.save(any())).thenReturn(CartBuilder.getCart());
			when(cartMapper.cartToCartDto(any())).thenReturn(CartBuilder.getCartDto());
			// Act
			CartDto cartDto=cartServiceImpl.create(cartRequestDto);
			// Assert
			assertNotNull(cartDto);
		}
	}
	
}
