package com.trafilea.test.coffeeshop.interfaces.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trafilea.test.coffeeshop.application.dtos.OrderDto;
import com.trafilea.test.coffeeshop.application.dtos.OrderTotalsDto;
import com.trafilea.test.coffeeshop.domain.services.OrderService;
import com.trafilea.test.coffeeshop.interfaces.dto.ResponseMessageDto;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class OrderController {
	
	private static final String MSG_ORDER_CREATED_SUCCESSFULLY_ORDER_ID = "The order is created successfully, the order id is:";
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody OrderDto orderDto) throws Exception {
		ResponseMessageDto responseMessageDto;
		try {
			OrderTotalsDto orderTotalsDto=orderService.create(orderDto);
			responseMessageDto=ResponseMessageDto.builder()
					.error(false)
					.message(MSG_ORDER_CREATED_SUCCESSFULLY_ORDER_ID+orderTotalsDto.getOrderID())
					.object(orderTotalsDto).build();
			return ResponseEntity.status(HttpStatus.CREATED).body(responseMessageDto);
		} catch (Exception e) {
			responseMessageDto=ResponseMessageDto.builder()
					.error(true)
					.message(e.getMessage())
					.build();
			return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(responseMessageDto);
		}
	}

}
