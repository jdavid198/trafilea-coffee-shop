package com.trafilea.test.coffeeshop.services;

import java.util.List;

import com.trafilea.test.coffeeshop.entities.Item;

public interface RuleService {
	public boolean ruleCategoryCoffee(List<Item> items);
	public boolean ruleCategoryEquipment(List<Item> items);
	public boolean ruleCategoryAccessories(List<Item> items);
}
