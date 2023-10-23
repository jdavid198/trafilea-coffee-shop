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

import com.trafilea.test.coffeeshop.application.dtos.ItemDto;
import com.trafilea.test.coffeeshop.builder.CartBuilder;
import com.trafilea.test.coffeeshop.builder.ItemBuilder;
import com.trafilea.test.coffeeshop.builder.ProductBuilder;
import com.trafilea.test.coffeeshop.domain.entities.Cart;
import com.trafilea.test.coffeeshop.domain.entities.Item;
import com.trafilea.test.coffeeshop.domain.enums.StatusEnum;
import com.trafilea.test.coffeeshop.domain.repositories.ItemRepository;
import com.trafilea.test.coffeeshop.domain.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {
	
	@Mock
	private ItemRepository itemRepository;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private CartServiceImpl cartServiceImpl;
	@Mock
	private ProductServiceImpl productServiceImpl;
	@InjectMocks
	private ItemServiceImpl itemServiceImpl;

	@Nested
	public class findByCart{
		@Test
		public void cartNull() {
			// Arrange
			Cart cart=null;
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				itemServiceImpl.findByCart(cart);
			});
			// Assert
			assertEquals("You must send a valid cart.", exception.getMessage());
		}
		@Test
		public void find() throws Exception {
			// Arrange
			Cart cart=CartBuilder.getCart();
			when(itemRepository.findByCart(any())).thenReturn(ItemBuilder.getItems());
			// Act
			List<Item> items= itemServiceImpl.findByCart(cart);
			// Assert
			assertNotNull(items);
		}
	}
	
	@Nested
	public class addItemsToCart{
		@Test
		public void cartIdNull() {
			// Arrange
			Long cartId=null;
			List<ItemDto> itemDtos=ItemBuilder.getItemDtos();
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				itemServiceImpl.addItemsToCart(cartId,itemDtos);
			});
			// Assert
			assertEquals("You must send a valid cart.", exception.getMessage());
		}
		@Test
		public void itemDtosNull() {
			// Arrange
			Long cartId=1L;
			List<ItemDto> itemDtos=null;
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				itemServiceImpl.addItemsToCart(cartId,itemDtos);
			});
			// Assert
			assertEquals("You must send a list of items with valid information.", exception.getMessage());
		}
		@Test
		public void cartNotFound() throws Exception {
			// Arrange
			Long cartId=1L;
			List<ItemDto> itemDtos=ItemBuilder.getItemDtos();
			when(cartServiceImpl.findById(any())).thenReturn(null);
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				itemServiceImpl.addItemsToCart(cartId,itemDtos);
			});
			// Assert
			assertEquals("The cart with id " + cartId + " does not exist.", exception.getMessage());
		}
		@Test
		public void cartClose() throws Exception {
			// Arrange
			Long cartId=1L;
			List<ItemDto> itemDtos=ItemBuilder.getItemDtos();
			Cart cart=CartBuilder.getCart();
			cart.setStatus(StatusEnum.CLOSE);
			when(cartServiceImpl.findById(any())).thenReturn(cart);
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				itemServiceImpl.addItemsToCart(cartId,itemDtos);
			});
			// Assert
			assertEquals("The cart is close.", exception.getMessage());
		}
		@Test
		public void itemWithProductNull() throws Exception {
			// Arrange
			Long cartId=1L;
			List<ItemDto> itemDtos=ItemBuilder.getItemDtos();
			ItemDto itemDto=itemDtos.get(0);
			itemDto.setProduct(null);
			when(cartServiceImpl.findById(any())).thenReturn(CartBuilder.getCart());
			when(productServiceImpl.findById(any())).thenReturn(null);
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				itemServiceImpl.addItemsToCart(cartId,itemDtos);
			});
			// Assert
			assertTrue(exception.getMessage().contains("The product with id"));
		}
		@Test
		public void itemWithQuantityNull() throws Exception {
			// Arrange
			Long cartId=1L;
			List<ItemDto> itemDtos=ItemBuilder.getItemDtos();
			ItemDto itemDto=itemDtos.get(0);
			itemDto.setQuantity(null);
			when(cartServiceImpl.findById(any())).thenReturn(CartBuilder.getCart());
			when(productServiceImpl.findById(any())).thenReturn(ProductBuilder.getProduct());
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				itemServiceImpl.addItemsToCart(cartId,itemDtos);
			});
			System.out.println(exception.getMessage());
			// Assert
			assertTrue(exception.getMessage().contains("The quantity is null for the product with id"));
		}
		@Test
		public void saveAll() throws Exception {
			// Arrange
			Long cartId=1L;
			List<ItemDto> itemDtos=ItemBuilder.getItemDtos();
			ItemDto itemDto=itemDtos.get(0);
			itemDto.setDiscounts(null);
			when(cartServiceImpl.findById(any())).thenReturn(CartBuilder.getCart());
			when(productServiceImpl.findById(any())).thenReturn(ProductBuilder.getProduct());
			// Act
			itemServiceImpl.addItemsToCart(cartId,itemDtos);
		}
	}
	
	@Nested
	class updateQuantityByCartProduct{
		@Test
		public void quantityNull() {
			// Arrange
			Integer quantity=null;
			Long cartId=null;
			Long productId=null;
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				itemServiceImpl.updateQuantityByCartProduct(cartId,productId,quantity);
			});
			// Assert
			assertEquals("You must send a valid amount.", exception.getMessage());
		}
		@Test
		public void cartNull() {
			// Arrange
			Integer quantity=1;
			Long cartId=null;
			Long productId=null;
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				itemServiceImpl.updateQuantityByCartProduct(cartId,productId,quantity);
			});
			// Assert
			assertEquals("You must send a valid cart.", exception.getMessage());
		}
		@Test
		public void productNull() {
			// Arrange
			Integer quantity=1;
			Long cartId=1L;
			Long productId=null;
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				itemServiceImpl.updateQuantityByCartProduct(cartId,productId,quantity);
			});
			// Assert
			assertEquals("You must send a valid product.", exception.getMessage());
		}
		@Test
		public void cartNotFound() throws Exception {
			// Arrange
			Integer quantity=1;
			Long cartId=1L;
			Long productId=1L;
			when(cartServiceImpl.findById(any())).thenReturn(null);
			when(productServiceImpl.findById(any())).thenReturn(ProductBuilder.getProduct());
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				itemServiceImpl.updateQuantityByCartProduct(cartId,productId,quantity);
			});
			// Assert
			assertEquals("The cart with id " + cartId + " does not exist.", exception.getMessage());
		}
		@Test
		public void cartClose() throws Exception {
			// Arrange
			Integer quantity=1;
			Long cartId=1L;
			Long productId=1L;
			Cart cart=CartBuilder.getCart();
			cart.setStatus(StatusEnum.CLOSE);
			when(cartServiceImpl.findById(any())).thenReturn(cart);
			when(productServiceImpl.findById(any())).thenReturn(ProductBuilder.getProduct());
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				itemServiceImpl.updateQuantityByCartProduct(cartId,productId,quantity);
			});
			// Assert
			assertEquals("The cart is close.", exception.getMessage());
		}
		@Test
		public void productNotFound() throws Exception {
			// Arrange
			Integer quantity=1;
			Long cartId=1L;
			Long productId=1L;
			when(cartServiceImpl.findById(any())).thenReturn(CartBuilder.getCart());
			when(productServiceImpl.findById(any())).thenReturn(null);
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				itemServiceImpl.updateQuantityByCartProduct(cartId,productId,quantity);
			});
			// Assert
			assertEquals("The product with id " + productId + " does not exist.", exception.getMessage());
		}
		@Test
		public void itemNotFound() throws Exception {
			// Arrange
			Integer quantity=1;
			Long cartId=1L;
			Long productId=1L;
			when(cartServiceImpl.findById(any())).thenReturn(CartBuilder.getCart());
			when(productServiceImpl.findById(any())).thenReturn(ProductBuilder.getProduct());
			when(itemRepository.findByCartAndProduct(any(),any())).thenReturn(null);
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				itemServiceImpl.updateQuantityByCartProduct(cartId,productId,quantity);
			});
			// Assert
			assertEquals("The item with cart id " + cartId + " and product "+ productId +" has not been created.", exception.getMessage());
		}
		@Test
		public void itemFindQuantityZero() throws Exception {
			// Arrange
			Integer quantity=0;
			Long cartId=1L;
			Long productId=1L;
			when(cartServiceImpl.findById(any())).thenReturn(CartBuilder.getCart());
			when(productServiceImpl.findById(any())).thenReturn(ProductBuilder.getProduct());
			when(itemRepository.findByCartAndProduct(any(),any())).thenReturn(ItemBuilder.getItem());
			// Act
			itemServiceImpl.updateQuantityByCartProduct(cartId,productId,quantity);
		}
		@Test
		public void saveQuantity() throws Exception {
			// Arrange
			Integer quantity=3;
			Long cartId=1L;
			Long productId=1L;
			when(cartServiceImpl.findById(any())).thenReturn(CartBuilder.getCart());
			when(productServiceImpl.findById(any())).thenReturn(ProductBuilder.getProduct());
			when(itemRepository.findByCartAndProduct(any(),any())).thenReturn(ItemBuilder.getItem());
			// Act
			itemServiceImpl.updateQuantityByCartProduct(cartId,productId,quantity);
		}
	}
	
	@Nested
	class save{
		@Test
		public void itemNull() {
			// Arrange
			Item item=null;
			// Act
			Exception exception = assertThrows(Exception.class, () -> {
				itemServiceImpl.save(item);
			});
			// Assert
			assertEquals("You must send a valid item.", exception.getMessage());
		}
		@Test
		public void saveItem() throws Exception {
			// Arrange
			Item item=ItemBuilder.getItem();
			// Act
			itemServiceImpl.save(item);
		}
	}
	
}
