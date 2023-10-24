package com.trafilea.test.coffeeshop.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.trafilea.test.coffeeshop.dtos.OrderDto;
import com.trafilea.test.coffeeshop.entities.Item;
import com.trafilea.test.coffeeshop.exceptions.ApiException;
import com.trafilea.test.coffeeshop.services.CalculatorService;
import com.trafilea.test.coffeeshop.services.RuleService;
import com.trafilea.test.coffeeshop.utils.Messages;

import lombok.RequiredArgsConstructor;

@Scope("singleton")
@Service
@RequiredArgsConstructor
public class CalculatorServiceImpl implements CalculatorService, Messages {

	private static final double DEFAULT_SHIPING = 10.0;

	@Autowired
	private RuleService ruleService;

	@Override
	public OrderDto calculateOrder(List<Item> items) throws ApiException {
		
		Double totalProducts = 0.0;
		Double totalDiscounts = 0.0;
		Double totalShipping = DEFAULT_SHIPING;
		Double totalOrder = 0.0;

		for (Item item : items) {
			Double totalItem = item.getProduct().getPrice() * item.getQuantity();
			if (item.getDiscounts() != null && item.getDiscounts() > 0) {
				totalProducts += totalItem - (totalItem * (item.getDiscounts() / 100));
				continue;
			}
			totalProducts += totalItem;
		}

		if (ruleService.ruleCategoryEquipment(items)) {
			totalShipping = 0.0;
		}
		if (ruleService.ruleCategoryAccessories(items)) {
			double discount = (double) 10 / 100;
			totalDiscounts = totalProducts * discount;
		}

		totalOrder = totalProducts - totalDiscounts + totalShipping;
		
		OrderDto orderDto=OrderDto.builder()
				.totalProducts(totalProducts)
				.totalDiscounts(totalDiscounts)
				.totalShipping(totalShipping)
				.totalOrder(totalOrder)
				.build();
		
		return orderDto;
	}

}
