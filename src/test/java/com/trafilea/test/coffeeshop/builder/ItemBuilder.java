package com.trafilea.test.coffeeshop.builder;

import java.util.ArrayList;
import java.util.List;

import com.trafilea.test.coffeeshop.application.dtos.ItemDto;
import com.trafilea.test.coffeeshop.domain.entities.Item;
import com.trafilea.test.coffeeshop.domain.enums.CategoryEnum;

public class ItemBuilder {
	public static Item getItem(){
		return Item.builder()
				.cart(CartBuilder.getCart())
				.product(ProductBuilder.getProduct())
				.quantity(1)
				.discounts(0D)
				.build();
	}
	public static List<Item> getItems(){
		List<Item> items=new ArrayList<Item>();
		items.add(getItem());
		items.add(getItem());
		items.add(getItem());
		return items;
	}
	public static List<Item> getItemsRuleCategoryCoffee(){
		List<Item> items=new ArrayList<Item>();
		Item item=getItem();
		item.getProduct().setCategory(CategoryEnum.COFFEE);
		items.add(item);
		items.add(item);
		items.add(item);
		return items;
	}
	public static List<Item> getItemsRuleCategoryEquipment(){
		List<Item> items=new ArrayList<Item>();
		Item item=getItem();
		item.getProduct().setCategory(CategoryEnum.EQUIPMENT);
		items.add(item);
		items.add(item);
		items.add(item);
		items.add(item);
		return items;
	}
	public static List<Item> getItemsRuleCategoryAccessories(){
		List<Item> items=new ArrayList<Item>();
		Item item=getItem();
		item.getProduct().setCategory(CategoryEnum.ACCESSORIES);
		item.setPrice(50D);
		items.add(item);
		items.add(item);
		items.add(item);
		return items;
	}
	public static ItemDto getItemDto(){
		return ItemDto.builder()
				.product(1L)
				.discounts(0D)
				.quantity(1)
				.build();
	}
	public static List<ItemDto> getItemDtos(){
		List<ItemDto> itemDtos=new ArrayList<ItemDto>();
		itemDtos.add(getItemDto());
		itemDtos.add(getItemDto());
		itemDtos.add(getItemDto());
		return itemDtos;
	}
}
