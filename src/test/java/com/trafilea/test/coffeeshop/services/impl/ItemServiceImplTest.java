package com.trafilea.test.coffeeshop.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.trafilea.test.coffeeshop.builder.ProductBuilder;
import com.trafilea.test.coffeeshop.controller.dtos.ItemRequestDto;
import com.trafilea.test.coffeeshop.entities.Cart;
import com.trafilea.test.coffeeshop.enums.StatusEnum;
import com.trafilea.test.coffeeshop.exceptions.ApiException;
import com.trafilea.test.coffeeshop.mappers.CartMapper;
import com.trafilea.test.coffeeshop.mappers.ItemMapper;
import com.trafilea.test.coffeeshop.repositories.CartRepository;
import com.trafilea.test.coffeeshop.repositories.ItemRepository;
import com.trafilea.test.coffeeshop.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {
	
	@Mock
	private ItemRepository itemRepository;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private CartRepository cartRepository;
	@InjectMocks
	private ItemServiceImpl itemServiceImpl;
	@Mock
	private CartMapper cartMapper;
	@Mock
	private ItemMapper itemMapper;

	@Nested
	public class addItemsToCart{
		

		@Test
		public void cartNotFound() throws Exception {
			// Arrange
			Long cartId=1L;
			List<ItemRequestDto> itemDtos=ItemBuilder.getItemRequestDtos();
			when(cartRepository.findById(any())).thenReturn(Optional.empty());
			// Act
			ApiException apiException = assertThrows(ApiException.class, () -> {
				itemServiceImpl.addItemsToCart(cartId,itemDtos);
			});
			// Assert
			assertEquals("The cart with id " + cartId + " does not exist.", apiException.getException());
		}
		@Test
		public void cartClose() throws Exception {
			// Arrange
			Long cartId=1L;
			List<ItemRequestDto> itemDtos=ItemBuilder.getItemRequestDtos();
			Cart cart=CartBuilder.getCart();
			cart.setStatus(StatusEnum.CLOSE);
			when(cartRepository.findById(any())).thenReturn(Optional.of(cart));
			// Act
			ApiException apiException = assertThrows(ApiException.class, () -> {
				itemServiceImpl.addItemsToCart(cartId,itemDtos);
			});
			// Assert
			assertEquals("The cart is close.", apiException.getException());
		}
		@Test
		public void itemWithProductNull() throws Exception {
			// Arrange
			Long cartId=1L;
			List<ItemRequestDto> itemDtos=ItemBuilder.getItemRequestDtos();
			ItemRequestDto itemDto=itemDtos.get(0);
			itemDto.setProduct(null);
			when(cartRepository.findById(any())).thenReturn(Optional.of(CartBuilder.getCart()));
			when(productRepository.findById(any())).thenReturn(Optional.empty());
			// Act
			ApiException apiException = assertThrows(ApiException.class, () -> {
				itemServiceImpl.addItemsToCart(cartId,itemDtos);
			});
			// Assert
			assertTrue(apiException.getException().contains("The product with id"));
		}
		@Test
		public void itemWithQuantityNull() throws Exception {
			// Arrange
			Long cartId=1L;
			List<ItemRequestDto> itemDtos=ItemBuilder.getItemRequestDtos();
			ItemRequestDto itemDto=itemDtos.get(0);
			itemDto.setQuantity(null);
			when(cartRepository.findById(any())).thenReturn(Optional.of(CartBuilder.getCart()));
			when(productRepository.findById(any())).thenReturn(Optional.of(ProductBuilder.getProduct()));
			// Act
			ApiException apiException = assertThrows(ApiException.class, () -> {
				itemServiceImpl.addItemsToCart(cartId,itemDtos);
			});
			System.out.println(apiException.getException());
			// Assert
			assertTrue(apiException.getException().contains("The quantity is null for the product with id"));
		}
		@Test
		public void saveAll() throws Exception {
			// Arrange
			Long cartId=1L;
			List<ItemRequestDto> itemDtos=ItemBuilder.getItemRequestDtos();
			ItemRequestDto itemDto=itemDtos.get(0);
			itemDto.setDiscounts(null);
			when(cartRepository.findById(any())).thenReturn(Optional.of(CartBuilder.getCart()));
			when(productRepository.findById(any())).thenReturn(Optional.of(ProductBuilder.getProduct()));
			when(itemRepository.findByCartAndProduct(any(),any())).thenReturn(Optional.of(ItemBuilder.getItem()));
			when(cartMapper.cartToCartDto(any())).thenReturn(CartBuilder.getCartDto());
			when(itemMapper.listItemToListItemDto(any())).thenReturn(ItemBuilder.getItemDtos());
			// Act
			itemServiceImpl.addItemsToCart(cartId,itemDtos);
		}
		@Test
		public void saveAllNewItems() throws Exception {
			// Arrange
			Long cartId=1L;
			List<ItemRequestDto> itemDtos=ItemBuilder.getItemRequestDtos();
			ItemRequestDto itemDto=itemDtos.get(0);
			itemDto.setDiscounts(null);
			when(cartRepository.findById(any())).thenReturn(Optional.of(CartBuilder.getCart()));
			when(productRepository.findById(any())).thenReturn(Optional.of(ProductBuilder.getProduct()));
			when(itemRepository.findByCartAndProduct(any(),any())).thenReturn(Optional.empty());
			when(cartMapper.cartToCartDto(any())).thenReturn(CartBuilder.getCartDto());
			when(itemMapper.listItemToListItemDto(any())).thenReturn(ItemBuilder.getItemDtos());
			// Act
			itemServiceImpl.addItemsToCart(cartId,itemDtos);
		}
	}
	
	@Nested
	class updateQuantityByCartItem{
		@Test
		public void quantityNull() {
			// Arrange
			Integer quantity=null;
			Long cartId=null;
			Long itemId=null;
			// Act
			ApiException apiException = assertThrows(ApiException.class, () -> {
				itemServiceImpl.updateQuantityByCartItem(cartId,itemId,quantity);
			});
			// Assert
			assertEquals("You must send a valid amount.", apiException.getException());
		}
		@Test
		public void cartNull() {
			// Arrange
			Integer quantity=1;
			Long cartId=null;
			Long itemId=null;
			// Act
			ApiException apiException = assertThrows(ApiException.class, () -> {
				itemServiceImpl.updateQuantityByCartItem(cartId,itemId,quantity);
			});
			// Assert
			assertEquals("You must send a valid cart.", apiException.getException());
		}
		@Test
		public void productNull() {
			// Arrange
			Integer quantity=1;
			Long cartId=1L;
			Long itemId=null;
			// Act
			ApiException apiException = assertThrows(ApiException.class, () -> {
				itemServiceImpl.updateQuantityByCartItem(cartId,itemId,quantity);
			});
			// Assert
			assertEquals("You must send a valid product.", apiException.getException());
		}
		@Test
		public void cartNotFound() throws Exception {
			// Arrange
			Integer quantity=1;
			Long cartId=1L;
			Long itemId=1L;
			when(cartRepository.findById(any())).thenReturn(Optional.empty());
			// Act
			ApiException apiException = assertThrows(ApiException.class, () -> {
				itemServiceImpl.updateQuantityByCartItem(cartId,itemId,quantity);
			});
			// Assert
			assertEquals("The cart with id " + cartId + " does not exist.", apiException.getException());
		}
		@Test
		public void cartClose() throws Exception {
			// Arrange
			Integer quantity=1;
			Long cartId=1L;
			Long itemId=1L;
			Cart cart=CartBuilder.getCart();
			cart.setStatus(StatusEnum.CLOSE);
			when(cartRepository.findById(any())).thenReturn(Optional.of(cart));
			// Act
			ApiException apiException = assertThrows(ApiException.class, () -> {
				itemServiceImpl.updateQuantityByCartItem(cartId,itemId,quantity);
			});
			// Assert
			assertEquals("The cart is close.", apiException.getException());
		}
		@Test
		public void itemNotFound() throws Exception {
			// Arrange
			Integer quantity=1;
			Long cartId=1L;
			Long itemId=1L;
			when(cartRepository.findById(any())).thenReturn(Optional.of(CartBuilder.getCart()));
			when(itemRepository.findById(any())).thenReturn(Optional.empty());
			// Act
			ApiException apiException = assertThrows(ApiException.class, () -> {
				itemServiceImpl.updateQuantityByCartItem(cartId,itemId,quantity);
			});
			// Assert
			assertEquals("You must send a valid item.", apiException.getException());
		}
		@Test
		public void itemFindQuantityZero() throws Exception {
			// Arrange
			Integer quantity=0;
			Long cartId=1L;
			Long itemId=1L;
			when(cartRepository.findById(any())).thenReturn(Optional.of(CartBuilder.getCart()));
			when(itemRepository.findById(any())).thenReturn(Optional.of(ItemBuilder.getItem()));
			// Act
			itemServiceImpl.updateQuantityByCartItem(cartId,itemId,quantity);
		}
		@Test
		public void saveQuantity() throws Exception {
			// Arrange
			Integer quantity=1;
			Long cartId=1L;
			Long itemId=1L;
			when(cartRepository.findById(any())).thenReturn(Optional.of(CartBuilder.getCart()));
			when(itemRepository.findById(any())).thenReturn(Optional.of(ItemBuilder.getItem()));
			// Act
			itemServiceImpl.updateQuantityByCartItem(cartId,itemId,quantity);
		}
	}
	
}
