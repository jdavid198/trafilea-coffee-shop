package com.trafilea.test.coffeeshop.interfaces.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trafilea.test.coffeeshop.application.dtos.CartDto;
import com.trafilea.test.coffeeshop.application.dtos.ItemDto;
import com.trafilea.test.coffeeshop.application.dtos.QuantityDto;
import com.trafilea.test.coffeeshop.domain.services.CartService;
import com.trafilea.test.coffeeshop.domain.services.ItemService;
import com.trafilea.test.coffeeshop.interfaces.dto.ResponseMessageDto;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class CartController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value = "/carts", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody CartDto cartDto) throws Exception {
		ResponseMessageDto responseMessageDto;
		try {
			Long cartId=cartService.create(cartDto);
			responseMessageDto=ResponseMessageDto.builder()
													.error(false)
													.message("The cart is created successfully, the cart id is:"+cartId)
													.object(cartId).build();
			return ResponseEntity.status(HttpStatus.CREATED).body(responseMessageDto);
		} catch (Exception e) {
			responseMessageDto=ResponseMessageDto.builder()
					.error(true)
					.message(e.getMessage())
					.build();
			return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(responseMessageDto);
		}
	}
	
	@RequestMapping(value = "/carts/{cartId}/item", method = RequestMethod.POST)
	public ResponseEntity<?> addItemsToCart(@PathVariable("cartId") Long cartId,@RequestBody List<ItemDto> itemDtos) throws Exception {
		ResponseMessageDto responseMessageDto;
		try {
			itemService.addItemsToCart(cartId, itemDtos);
			responseMessageDto=ResponseMessageDto.builder()
					.error(false)
					.message("The products are added to the cart")
					.object(cartId).build();
			return ResponseEntity.status(HttpStatus.CREATED).body(responseMessageDto);
		} catch (Exception e) {
			responseMessageDto=ResponseMessageDto.builder()
					.error(true)
					.message(e.getMessage())
					.build();
			return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(responseMessageDto);
		}
	}
	
	@RequestMapping(value = "/carts/{cartId}/item/{productId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateQuantityByCartProduct(@PathVariable("cartId") Long cartId,@PathVariable("productId") Long productId,@RequestBody QuantityDto quantityDto ) throws Exception {
		ResponseMessageDto responseMessageDto;
		try {
			itemService.updateQuantityByCartProduct(cartId, productId, quantityDto.getQuantity());
			responseMessageDto=ResponseMessageDto.builder()
					.error(false)
					.message("The quantity is updated successfully")
					.object(cartId).build();
			return ResponseEntity.status(HttpStatus.CREATED).body(cartId);
		} catch (Exception e) {
			responseMessageDto=ResponseMessageDto.builder()
					.error(true)
					.message(e.getMessage())
					.build();
			return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(responseMessageDto);
		}
	}
	
}
