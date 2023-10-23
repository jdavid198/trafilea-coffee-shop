package com.trafilea.test.coffeeshop.builder;

import com.trafilea.test.coffeeshop.application.dtos.CartDto;
import com.trafilea.test.coffeeshop.domain.entities.Cart;
import com.trafilea.test.coffeeshop.domain.enums.StatusEnum;

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
				.userID(1212312L).build();
	}
}
