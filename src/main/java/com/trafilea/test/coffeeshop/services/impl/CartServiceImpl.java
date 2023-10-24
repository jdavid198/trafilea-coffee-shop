package com.trafilea.test.coffeeshop.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.trafilea.test.coffeeshop.controller.dtos.CartRequestDto;
import com.trafilea.test.coffeeshop.dtos.CartDto;
import com.trafilea.test.coffeeshop.entities.Cart;
import com.trafilea.test.coffeeshop.enums.StatusEnum;
import com.trafilea.test.coffeeshop.exceptions.ApiException;
import com.trafilea.test.coffeeshop.mappers.CartMapper;
import com.trafilea.test.coffeeshop.repositories.CartRepository;
import com.trafilea.test.coffeeshop.services.CartService;
import com.trafilea.test.coffeeshop.services.ItemService;
import com.trafilea.test.coffeeshop.utils.Messages;

import lombok.RequiredArgsConstructor;

@Scope("singleton")
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService,Messages{
	
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ItemService itemService;
	@Autowired
	private CartMapper cartMapper;

	@Override
	public CartDto create(CartRequestDto cartRequestDto) throws ApiException {
		Optional<Cart> cartOptional=cartRepository.findByUserIDAndStatus(cartRequestDto.getUserID(), StatusEnum.OPEN);
		if (cartOptional.isPresent()) {
			throw new ApiException(HttpStatus.CONFLICT, MSG_HAVE_SHOPPING_CART_OPEN);
		}
		Cart cart=Cart.builder().userID(cartRequestDto.getUserID()).status(StatusEnum.OPEN).build();
		cart=cartRepository.save(cart);
		if (cartRequestDto.getItems()!=null && !cartRequestDto.getItems().isEmpty()) {
			return itemService.addItemsToCart(cart.getCartId(), cartRequestDto.getItems());
		}
		return cartMapper.cartToCartDto(cart);
	}
}
