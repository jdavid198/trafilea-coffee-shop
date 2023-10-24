package com.trafilea.test.coffeeshop.mappers;

import java.util.List;

import com.trafilea.test.coffeeshop.dtos.CartDto;
import com.trafilea.test.coffeeshop.entities.Cart;

public interface CartMapper {
	public CartDto cartToCartDto(Cart cart);
	public Cart cartDtoToCart(CartDto cartDto);
	public List<CartDto> listCartToListCartDto(List<Cart> cart);
	public List<Cart> listCartDtoToListCart(List<CartDto> cart);
}
