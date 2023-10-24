package com.trafilea.test.coffeeshop.builder;

import com.trafilea.test.coffeeshop.controller.dtos.CartRequestDto;
import com.trafilea.test.coffeeshop.dtos.CartDto;
import com.trafilea.test.coffeeshop.entities.Cart;
import com.trafilea.test.coffeeshop.enums.StatusEnum;

public class CartBuilder {
	public static Cart getCart(){
		return Cart.builder()
				.cartId(1L)
				.userID(1212312L)
				.status(StatusEnum.OPEN)
				.build();
	}
	public static CartDto getCartDto(){
		return CartDto.builder()
				.cartId(1L)
				.userID(1212312L)
				.status(StatusEnum.OPEN)
				.build();
	}
	public static CartRequestDto getCartRequestDto(){
		return CartRequestDto.builder()
				.userID(1212312L).build();
	}
}
