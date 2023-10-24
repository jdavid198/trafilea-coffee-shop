package com.trafilea.test.coffeeshop.services;

import java.util.List;

import com.trafilea.test.coffeeshop.dtos.OrderDto;
import com.trafilea.test.coffeeshop.entities.Item;
import com.trafilea.test.coffeeshop.exceptions.ApiException;

public interface CalculatorService {
	public OrderDto calculateOrder(List<Item> items) throws ApiException;
}
