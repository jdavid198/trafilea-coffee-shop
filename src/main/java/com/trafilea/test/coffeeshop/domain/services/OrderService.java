package com.trafilea.test.coffeeshop.domain.services;

import com.trafilea.test.coffeeshop.application.dtos.OrderDto;
import com.trafilea.test.coffeeshop.application.dtos.OrderTotalsDto;

public interface OrderService{
	public OrderTotalsDto create(OrderDto orderDto) throws Exception;
}