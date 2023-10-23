package com.trafilea.test.coffeeshop.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.trafilea.test.coffeeshop.application.dtos.CartDto;
import com.trafilea.test.coffeeshop.domain.entities.Cart;
import com.trafilea.test.coffeeshop.domain.repositories.CartRepository;
import com.trafilea.test.coffeeshop.domain.services.CartService;

import lombok.RequiredArgsConstructor;

@Scope("singleton")
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartRepository cartRepository;

	@Override
	public Long create(CartDto cartDto) {
		Cart cart=Cart.builder().userID(cartDto.getUserID()).build();
		cartRepository.save(cart);
		return cart.getCartId();
	}

	@Override
	public Cart findById(Long cartId) {
		Optional<Cart>optional=cartRepository.findById(cartId);
		if (optional.isEmpty()) {
			return null;
		}
		return optional.get();
	}
	
}
