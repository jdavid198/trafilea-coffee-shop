package com.trafilea.test.coffeeshop.builder;

import com.trafilea.test.coffeeshop.application.dtos.OrderDto;
import com.trafilea.test.coffeeshop.application.dtos.OrderTotalsDto;
import com.trafilea.test.coffeeshop.domain.entities.Order;

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
	public static OrderDto getOrderDto(){
		return OrderDto.builder()
				.cartId(1L)
				.build();
	}
	public static OrderTotalsDto getOrderTotalsDto(){
		return OrderTotalsDto.builder()
				.orderID(1L)
				.totalDiscounts(0D)
				.totalShipping(0D)
				.totalProducts(500D)
				.totalOrder(500D)
				.build();
	}
}
