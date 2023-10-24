package com.trafilea.test.coffeeshop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trafilea.test.coffeeshop.controller.dtos.CartRequestDto;
import com.trafilea.test.coffeeshop.controller.dtos.ItemRequestDto;
import com.trafilea.test.coffeeshop.controller.dtos.QuantityRequestDto;
import com.trafilea.test.coffeeshop.dtos.CartDto;
import com.trafilea.test.coffeeshop.dtos.ItemDto;
import com.trafilea.test.coffeeshop.exceptions.ApiException;
import com.trafilea.test.coffeeshop.services.CartService;
import com.trafilea.test.coffeeshop.services.ItemService;
import com.trafilea.test.coffeeshop.utils.Messages;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class CartController implements Messages{
	
	@Autowired
	private CartService cartService;
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value = "/carts", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody CartRequestDto cartRequestDto) throws ApiException {
		try {
			CartDto cartDto=cartService.create(cartRequestDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(cartDto);
		} catch (ApiException apiException) {
			return ResponseEntity.status(apiException.getCode()).body(apiException.getException());
		}
	}
	
	@RequestMapping(value = "/carts/{cartId}/items", method = RequestMethod.POST)
	public ResponseEntity<?> addItemsToCart(@PathVariable("cartId") Long cartId,@Valid @RequestBody List<ItemRequestDto> itemRequestDtos) throws ApiException {
		try {
			if (cartId==null || cartId<1) {
				throw new ApiException(HttpStatus.BAD_REQUEST, MSG_MUST_SEND_VALID_CART);
			}
			if (itemRequestDtos==null || itemRequestDtos.isEmpty()) {
				throw new ApiException(HttpStatus.BAD_REQUEST, MSG_SEND_LIST_ITEMS_VALID);
			}
			CartDto cartDto=itemService.addItemsToCart(cartId, itemRequestDtos);
			return ResponseEntity.status(HttpStatus.CREATED).body(cartDto);
		} catch (ApiException apiException) {
			return ResponseEntity.status(apiException.getCode()).body(apiException.getException());
		}
	}
	
	@RequestMapping(value = "/carts/{cartId}/items/{itemId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateQuantityByCartProduct(@PathVariable("cartId") Long cartId,@PathVariable("itemId") Long itemId,@Valid @RequestBody QuantityRequestDto quantityRequestDto ) throws ApiException {
		try {
			ItemDto itemDto=itemService.updateQuantityByCartItem(cartId, itemId, quantityRequestDto.getQuantity());
			return ResponseEntity.status(HttpStatus.CREATED).body(itemDto);
		} catch (ApiException apiException) {
			return ResponseEntity.status(apiException.getCode()).body(apiException.getException());
		}
	}
	
}
