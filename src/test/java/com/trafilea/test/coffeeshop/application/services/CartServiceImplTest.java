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

import com.trafilea.test.coffeeshop.application.dtos.CartDto;
import com.trafilea.test.coffeeshop.builder.CartBuilder;
import com.trafilea.test.coffeeshop.domain.entities.Cart;
import com.trafilea.test.coffeeshop.domain.repositories.CartRepository;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {
	
	@Mock
	private CartRepository cartRepository;
	@InjectMocks
	private CartServiceImpl cartServiceImpl;

	@Nested
	public class create{
		
		@Test
		public void userIdNull() {
			// Arrange
			Long userID=null;
			CartDto cartDto=CartDto.builder().userID(userID).build();
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				cartServiceImpl.create(cartDto);
			});
			// Assert
			assertEquals("You must send the user id.", exception.getMessage());
		}
		@Test
		public void cartDtoNull() {
			// Arrange
			CartDto cartDto=null;
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				cartServiceImpl.create(cartDto);
			});
			// Assert
			assertEquals("You must send the cart.", exception.getMessage());
		}
		@Test
		public void cartOpen() {
			// Arrange
			Long userID=124333344L;
			CartDto cartDto=CartDto.builder().userID(userID).build();
			when(cartRepository.findByUserIDAndStatus(any(),any())).thenReturn(CartBuilder.getCart());
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				cartServiceImpl.create(cartDto);
			});
			// Assert
			assertEquals("You have a shopping cart open.", exception.getMessage());
		}
		@Test
		public void saveCartDto() throws Exception {
			// Arrange
			Long userID=124333344L;
			CartDto cartDto=CartDto.builder().userID(userID).build();
			when(cartRepository.save(any())).thenReturn(CartBuilder.getCart());
			when(cartRepository.findByUserIDAndStatus(any(),any())).thenReturn(null);
			// Act
			Long cartId= cartServiceImpl.create(cartDto);
			// Assert
			assertNotNull(cartId);
		}
		
	}
	
	@Nested
	class findById{
		@Test
		public void cartIdNull() {
			// Arrange
			Long cartID=null;
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				cartServiceImpl.findById(cartID);
			});
			// Assert
			assertEquals("You must send the cart id.", exception.getMessage());
		}
		@Test
		public void cartNull() throws Exception {
			// Arrange
			Long cartID=1L;
			when(cartRepository.findById(any())).thenReturn(null);
			// Act
			Cart cart=cartServiceImpl.findById(cartID);
			// Assert
			assertNull(cart);
		}
		@Test
		public void findCart() throws Exception {
			// Arrange
			Long cartID=1L;
			when(cartRepository.findById(any())).thenReturn(Optional.of(CartBuilder.getCart()));
			// Act
			Cart cart=cartServiceImpl.findById(cartID);
			// Assert
			assertNotNull(cart);
		}
	}
	
	@Nested
	class save{
		@Test
		public void itemNull() {
			// Arrange
			Cart cart=null;
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				cartServiceImpl.save(cart);
			});
			// Assert
			assertEquals("You must send the cart.", exception.getMessage());
		}
		@Test
		public void saveItem() throws Exception {
			// Arrange
			Cart cart=CartBuilder.getCart();
			// Act
			cartServiceImpl.save(cart);
		}
	}
	
}
