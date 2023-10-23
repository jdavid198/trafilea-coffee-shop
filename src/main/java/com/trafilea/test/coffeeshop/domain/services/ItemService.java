package com.trafilea.test.coffeeshop.domain.services;

import java.util.List;

import com.trafilea.test.coffeeshop.application.dtos.ItemDto;
import com.trafilea.test.coffeeshop.domain.entities.Cart;
import com.trafilea.test.coffeeshop.domain.entities.Item;

public interface ItemService{
	public List<Item> findByCart(Cart cart);
	public void addItemsToCart(Long cartId,List<ItemDto> itemDtos) throws Exception;
	public void updateQuantityByCartProduct(Long cartId,Long productId,Integer quantity) throws Exception;
	public void save(Item item) throws Exception;
}