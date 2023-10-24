package com.trafilea.test.coffeeshop.services;

import java.util.List;

import com.trafilea.test.coffeeshop.controller.dtos.ItemRequestDto;
import com.trafilea.test.coffeeshop.dtos.CartDto;
import com.trafilea.test.coffeeshop.dtos.ItemDto;
import com.trafilea.test.coffeeshop.exceptions.ApiException;

public interface ItemService{
	public CartDto addItemsToCart(Long cartId,List<ItemRequestDto> itemRequestDtos) throws ApiException;
	public ItemDto updateQuantityByCartItem(Long cartId, Long itemId,Integer quantity) throws ApiException;
}