package com.trafilea.test.coffeeshop.services;

import com.trafilea.test.coffeeshop.controller.dtos.CartRequestDto;
import com.trafilea.test.coffeeshop.dtos.CartDto;
import com.trafilea.test.coffeeshop.exceptions.ApiException;

public interface CartService{
	public CartDto create(CartRequestDto cartDto) throws ApiException;
}