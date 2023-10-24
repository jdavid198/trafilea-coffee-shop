package com.trafilea.test.coffeeshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trafilea.test.coffeeshop.controller.dtos.OrderRequestDto;
import com.trafilea.test.coffeeshop.dtos.OrderDto;
import com.trafilea.test.coffeeshop.exceptions.ApiException;
import com.trafilea.test.coffeeshop.services.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody OrderRequestDto orderRequestDto) throws ApiException {
		try {
			OrderDto orderDto=orderService.create(orderRequestDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
		} catch (ApiException apiException) {
			return ResponseEntity.status(apiException.getCode()).body(apiException.getException());
		}
	}

}
