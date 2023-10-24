package com.trafilea.test.coffeeshop.builder;

import com.trafilea.test.coffeeshop.controller.dtos.OrderRequestDto;
import com.trafilea.test.coffeeshop.dtos.OrderDto;
import com.trafilea.test.coffeeshop.entities.Order;

public class OrderBuilder {
	public static Order getOrder(){
		return Order.builder()
				.cart(CartBuilder.getCart())
				.totalDiscounts(0D)
				.totalShipping(0D)
				.totalProducts(500D)
				.totalOrder(500D)
				.build();
	}
	public static OrderRequestDto getOrderRequestDto(){
		return OrderRequestDto.builder()
				.cartId(1L)
				.build();
	}
	public static OrderDto getOrderDto(){
		return OrderDto.builder()
				.orderID(1L)
				.totalDiscounts(0D)
				.totalShipping(0D)
				.totalProducts(500D)
				.totalOrder(500D)
				.build();
	}
}
