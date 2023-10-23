package com.trafilea.test.coffeeshop.application.services;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.trafilea.test.coffeeshop.domain.entities.Item;
import com.trafilea.test.coffeeshop.domain.entities.Product;
import com.trafilea.test.coffeeshop.domain.services.SpecialRuleService;
import com.trafilea.test.coffeeshop.enums.CategoryEnum;

import lombok.RequiredArgsConstructor;

@Scope("singleton")
@Service
@RequiredArgsConstructor
public class SpecialRuleServiceImpl implements SpecialRuleService{
	
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
		Double quantity=items.stream()
				.filter(item->item.getProduct().getCategory().equals(CategoryEnum.ACCESSORIES))
				.flatMap(item -> Stream.of(item.getProduct()))
                .mapToDouble(Product::getPrice)
                .sum();
		return quantity>70;
	}
	
}
