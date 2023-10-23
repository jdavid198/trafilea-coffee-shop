package com.trafilea.test.coffeeshop.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.trafilea.test.coffeeshop.application.dtos.CartDto;
import com.trafilea.test.coffeeshop.domain.entities.Cart;
import com.trafilea.test.coffeeshop.domain.enums.StatusEnum;
import com.trafilea.test.coffeeshop.domain.repositories.CartRepository;
import com.trafilea.test.coffeeshop.domain.services.CartService;

import lombok.RequiredArgsConstructor;

@Scope("singleton")
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
	
	private static final String MSG_HAVE_SHOPPING_CART_OPEN = "You have a shopping cart open.";
	private static final String MSG_SEND_CART_ID = "You must send the cart id.";
	private static final String MSG_MUST_SEND_USER_ID = "You must send the user id.";
	private static final String MSG_SEND_CART = "You must send the cart.";
	@Autowired
	private CartRepository cartRepository;

	@Override
	public Long create(CartDto cartDto) throws Exception {
		Cart cart;
		if (cartDto==null) {
			throw new Exception(MSG_SEND_CART);
		}
		if (cartDto.getUserID()==null) {
			throw new Exception(MSG_MUST_SEND_USER_ID);
		}
		cart=cartRepository.findByUserIDAndStatus(cartDto.getUserID(), StatusEnum.OPEN);
		if (cart!=null) {
			throw new Exception(MSG_HAVE_SHOPPING_CART_OPEN);
		}
		cart=Cart.builder().userID(cartDto.getUserID()).status(StatusEnum.OPEN).build();
		cart=cartRepository.save(cart);
		return cart.getCartId();
	}

	@Override
	public Cart findById(Long cartId) throws Exception {
		if (cartId==null) {
			throw new Exception(MSG_SEND_CART_ID);
		}
		Optional<Cart>optional=cartRepository.findById(cartId);
		if (optional==null || optional.isEmpty()) {
			return null;
		}
		return optional.get();
	}
	
	@Override
	public void save(Cart cart) throws Exception {
		if (cart==null) {
			throw new Exception(MSG_SEND_CART);
		}
		cartRepository.save(cart);
	}
	
}
