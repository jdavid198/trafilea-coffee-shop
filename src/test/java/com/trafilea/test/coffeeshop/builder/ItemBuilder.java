package com.trafilea.test.coffeeshop.builder;

import java.util.ArrayList;
import java.util.List;

import com.trafilea.test.coffeeshop.controller.dtos.ItemRequestDto;
import com.trafilea.test.coffeeshop.dtos.ItemDto;
import com.trafilea.test.coffeeshop.entities.Item;
import com.trafilea.test.coffeeshop.enums.CategoryEnum;

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
				.cartId(1L)
				.productId(1L)
				.quantity(1)
				.discounts(0D)
				.build();
	}
	public static List<ItemDto> getItemDtos(){
		List<ItemDto> itemDtos=new ArrayList<ItemDto>();
		itemDtos.add(getItemDto());
		itemDtos.add(getItemDto());
		itemDtos.add(getItemDto());
		return itemDtos;
	}
	
	public static ItemRequestDto getItemRequestDto(){
		return ItemRequestDto.builder()
				.product(1L)
				.discounts(0D)
				.quantity(1)
				.build();
	}
	public static List<ItemRequestDto> getItemRequestDtos(){
		List<ItemRequestDto> itemDtos=new ArrayList<ItemRequestDto>();
		itemDtos.add(getItemRequestDto());
		itemDtos.add(getItemRequestDto());
		itemDtos.add(getItemRequestDto());
		return itemDtos;
	}
}
