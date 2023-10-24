package com.trafilea.test.coffeeshop.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.trafilea.test.coffeeshop.controller.dtos.ItemRequestDto;
import com.trafilea.test.coffeeshop.dtos.CartDto;
import com.trafilea.test.coffeeshop.dtos.ItemDto;
import com.trafilea.test.coffeeshop.entities.Cart;
import com.trafilea.test.coffeeshop.entities.Item;
import com.trafilea.test.coffeeshop.entities.Product;
import com.trafilea.test.coffeeshop.enums.StatusEnum;
import com.trafilea.test.coffeeshop.exceptions.ApiException;
import com.trafilea.test.coffeeshop.mappers.CartMapper;
import com.trafilea.test.coffeeshop.mappers.ItemMapper;
import com.trafilea.test.coffeeshop.repositories.CartRepository;
import com.trafilea.test.coffeeshop.repositories.ItemRepository;
import com.trafilea.test.coffeeshop.repositories.ProductRepository;
import com.trafilea.test.coffeeshop.services.ItemService;
import com.trafilea.test.coffeeshop.utils.Messages;

import lombok.RequiredArgsConstructor;

@Scope("singleton")
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService,Messages {
	
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private CartMapper cartMapper;
	
	@Override
	public CartDto addItemsToCart(Long cartId,List<ItemRequestDto> itemRequestDtos) throws ApiException {
		
		Optional<Cart> cartOptional=cartRepository.findById(cartId);
		if (!cartOptional.isPresent()) {
			throw new ApiException(HttpStatus.BAD_REQUEST, MSG_CART_WITH_ID + cartId + MSG_DOES_NOT_EXIST);
		}
		if (cartOptional.get().getStatus().equals(StatusEnum.CLOSE)) {
			throw new ApiException(HttpStatus.BAD_REQUEST, MSG_CART_CLOSE);
		}
		
		List<Item>items=new ArrayList<Item>();
		for (ItemRequestDto itemDto : itemRequestDtos) {
			Optional<Product> productOptional=productRepository.findById(itemDto.getProduct());
			if (!productOptional.isPresent()) {
				throw new ApiException(HttpStatus.BAD_REQUEST, MSG_PRODUCT_ID + itemDto.getProduct() + MSG_DOES_NOT_EXIST);
			}
			if (itemDto.getQuantity()==null) {
				throw new ApiException(HttpStatus.BAD_REQUEST, MSG_QUANTITY_IS_NULL + itemDto.getProduct() + MSG_POINT);
			}
			if (itemDto.getDiscounts()==null) {
				itemDto.setDiscounts(0D);
			}
			
			Optional<Item> itemOptional=itemRepository.findByCartAndProduct(cartOptional.get(), productOptional.get());
			Item item;
			if (itemOptional.isPresent()) {
				item=itemOptional.get();
			}else {
				item=Item.builder()
						.cart(cartOptional.get())
						.product(productOptional.get())
						.price(productOptional.get().getPrice())
						.quantity(0)
						.discounts(itemDto.getDiscounts())
						.build();
			}
			item.setQuantity(item.getQuantity()+itemDto.getQuantity());
			items.add(item);
		}
		
		itemRepository.saveAll(items);
		CartDto cartDto=cartMapper.cartToCartDto(cartOptional.get());
		cartDto.setItemDtos(itemMapper.listItemToListItemDto(items));
		return cartDto;
	}
	
	@Override
	public ItemDto updateQuantityByCartItem(Long cartId, Long itemId, Integer quantity) throws ApiException {
		if (quantity==null || quantity<0) {
			throw new ApiException(HttpStatus.BAD_REQUEST, MSG_MUST_SEND_VALID_AMOUNT);
		}
		if (cartId==null || cartId<0) {
			throw new ApiException(HttpStatus.BAD_REQUEST, MSG_MUST_SEND_VALID_CART);
		}
		if (itemId==null || itemId<0) {
			throw new ApiException(HttpStatus.BAD_REQUEST, MSG_MUST_SEND_VALID_PRODUCT);
		}
		
		Optional<Cart> cartOptional= cartRepository.findById(cartId);
		if (!cartOptional.isPresent()) {
			throw new ApiException(HttpStatus.BAD_REQUEST, MSG_CART_WITH_ID+cartId+MSG_DOES_NOT_EXIST);
		}
		if (cartOptional.get().getStatus().equals(StatusEnum.CLOSE)) {
			throw new ApiException(HttpStatus.CONFLICT,MSG_CART_CLOSE);
		}
		
		Optional<Item> itemOptional= itemRepository.findById(itemId);
		if (!itemOptional.isPresent()) {
			throw new ApiException(HttpStatus.BAD_REQUEST, MSG_MUST_SEND_A_VALID_ITEM);
		}
		if (quantity==0) {
			itemRepository.delete(itemOptional.get());
			return null;
		}
		itemOptional.get().setQuantity(quantity);
		itemRepository.save(itemOptional.get());
		return itemMapper.itemToItemDto(itemOptional.get());
	}
}
