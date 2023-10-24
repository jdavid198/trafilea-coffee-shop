package com.trafilea.test.coffeeshop.services;

import com.trafilea.test.coffeeshop.controller.dtos.OrderRequestDto;
import com.trafilea.test.coffeeshop.dtos.OrderDto;
import com.trafilea.test.coffeeshop.exceptions.ApiException;

public interface OrderService{
	public OrderDto create(OrderRequestDto orderDto) throws ApiException;
}