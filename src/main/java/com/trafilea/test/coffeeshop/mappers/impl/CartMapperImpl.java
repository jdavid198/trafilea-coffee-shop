package com.trafilea.test.coffeeshop.mappers.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trafilea.test.coffeeshop.dtos.CartDto;
import com.trafilea.test.coffeeshop.dtos.ItemDto;
import com.trafilea.test.coffeeshop.entities.Cart;
import com.trafilea.test.coffeeshop.entities.Item;
import com.trafilea.test.coffeeshop.mappers.CartMapper;
import com.trafilea.test.coffeeshop.mappers.ItemMapper;

@Component
public class CartMapperImpl implements CartMapper{
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Override
	public CartDto cartToCartDto(Cart cart) {
		if (cart==null) {
			return null;
		}
		CartDto cartDto=CartDto.builder()
						.cartId(cart.getCartId())
						.userID(cart.getUserID())
						.status(cart.getStatus())
						.build();
		List<ItemDto> ItemDtos=itemMapper.listItemToListItemDto(cart.getItems());
		cartDto.setItemDtos(ItemDtos);
		return cartDto;
	}
	@Override
	public Cart cartDtoToCart(CartDto cartDto) {
		if (cartDto==null) {
			return null;
		}
		Cart cart=Cart.builder()
				.cartId(cartDto.getCartId())
				.userID(cartDto.getUserID())
				.status(cartDto.getStatus())
				.build();
		List<Item> Items=itemMapper.listItemDtoToListItem(cartDto.getItemDtos());
		cart.setItems(Items);
		return cart;
	}
	@Override
	public List<CartDto> listCartToListCartDto(List<Cart> carts){
		if (carts==null || carts.isEmpty()) {
			return null;
		}
		List<CartDto> cartDtos=new ArrayList<CartDto>();
		for (Cart cart : carts) {
			cartDtos.add(cartToCartDto(cart));
		}
		return cartDtos;
	}
	@Override
	public List<Cart> listCartDtoToListCart(List<CartDto> cartDtos){
		if (cartDtos==null || cartDtos.isEmpty()) {
			return null;
		}
		List<Cart> carts=new ArrayList<Cart>();
		for (CartDto cartDto : cartDtos) {
			carts.add(cartDtoToCart(cartDto));
		}
		return carts;
	}
}
