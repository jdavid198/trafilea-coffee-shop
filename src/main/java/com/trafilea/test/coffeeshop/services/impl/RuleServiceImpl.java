package com.trafilea.test.coffeeshop.services.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.trafilea.test.coffeeshop.entities.Item;
import com.trafilea.test.coffeeshop.enums.CategoryEnum;
import com.trafilea.test.coffeeshop.services.RuleService;

import lombok.RequiredArgsConstructor;

@Scope("singleton")
@Service
@RequiredArgsConstructor
public class RuleServiceImpl implements RuleService{
	
	@Override
	public boolean ruleCategoryCoffee(List<Item> items) {
		long quantity=items.stream().filter(item->item.getProduct().getCategory().equals(CategoryEnum.COFFEE))
				.mapToInt(Item::getQuantity)
				.sum();
		return quantity>=2;
	}

	@Override
	public boolean ruleCategoryEquipment(List<Item> items) {
		long quantity=items.stream().filter(item->item.getProduct().getCategory().equals(CategoryEnum.EQUIPMENT))
				.mapToInt(Item::getQuantity)
				.sum();
		return quantity>3;
	}

	@Override
	public boolean ruleCategoryAccessories(List<Item> items) {
		Double value=items.stream()
				.filter(item->item.getProduct().getCategory().equals(CategoryEnum.ACCESSORIES))
                .mapToDouble(Item::getTotalPrice)
                .sum();
		return value>70;
	}
	
}
