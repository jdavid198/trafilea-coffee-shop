package com.trafilea.test.coffeeshop.domain.services;

import java.util.List;

import com.trafilea.test.coffeeshop.domain.entities.Item;

public interface SpecialRuleService {
	public boolean ruleCategoryCoffee(List<Item> items);
	public boolean ruleCategoryEquipment(List<Item> items);
	public boolean ruleCategoryAccessories(List<Item> items);
}
