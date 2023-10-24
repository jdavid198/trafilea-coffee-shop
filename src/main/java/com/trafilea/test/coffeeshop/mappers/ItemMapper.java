package com.trafilea.test.coffeeshop.mappers;

import java.util.List;

import com.trafilea.test.coffeeshop.dtos.ItemDto;
import com.trafilea.test.coffeeshop.entities.Item;

public interface ItemMapper {
	public ItemDto itemToItemDto(Item item);
	public Item itemDtoToItem(ItemDto itemDto);
	public List<ItemDto> listItemToListItemDto(List<Item> items);
	public List<Item> listItemDtoToListItem(List<ItemDto> itemDtos);
}
