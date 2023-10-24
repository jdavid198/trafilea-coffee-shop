package com.trafilea.test.coffeeshop.mappers.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trafilea.test.coffeeshop.dtos.ItemDto;
import com.trafilea.test.coffeeshop.entities.Cart;
import com.trafilea.test.coffeeshop.entities.Item;
import com.trafilea.test.coffeeshop.entities.Product;
import com.trafilea.test.coffeeshop.mappers.ItemMapper;
import com.trafilea.test.coffeeshop.repositories.CartRepository;
import com.trafilea.test.coffeeshop.repositories.ProductRepository;

@Component
public class ItemMapperImpl implements ItemMapper{
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CartRepository cartRepository;
	
	@Override
	public ItemDto itemToItemDto(Item item) {
		if (item==null) {
			return null;
		}
		ItemDto itemDto=ItemDto.builder()
						.itemId(item.getItemId())
						.cartId(item.getCart().getCartId())
						.productId(item.getProduct().getProductId())
						.price(item.getPrice())
						.quantity(item.getQuantity())
						.discounts(item.getDiscounts())
						.build();
		return itemDto;
	}
	@Override
	public Item itemDtoToItem(ItemDto itemDto) {
		if (itemDto==null) {
			return null;
		}
		Item item=Item.builder()
				.itemId(itemDto.getItemId())
				.price(itemDto.getPrice())
				.quantity(itemDto.getQuantity())
				.discounts(itemDto.getDiscounts())
				.build();
		if (itemDto.getCartId()!=null && itemDto.getCartId()>0) {
			Optional<Cart> cartOptional= cartRepository.findById(itemDto.getCartId());
			if (cartOptional.isPresent()) {
				item.setCart(cartOptional.get());
			}
		}
		if (itemDto.getProductId()!=null && itemDto.getProductId()>0) {
			Optional<Product> productOptional= productRepository.findById(itemDto.getProductId());
			if (productOptional.isPresent()) {
				item.setProduct(productOptional.get());
			}
		}
		return item;
	}
	@Override
	public List<ItemDto> listItemToListItemDto(List<Item> items){
		if (items==null || items.isEmpty()) {
			return null;
		}
		List<ItemDto> itemDtos=new ArrayList<ItemDto>();
		for (Item item : items) {
			itemDtos.add(itemToItemDto(item));
		}
		return itemDtos;
	}
	@Override
	public List<Item> listItemDtoToListItem(List<ItemDto> itemDtos){
		if (itemDtos==null || itemDtos.isEmpty()) {
			return null;
		}
		List<Item> items=new ArrayList<Item>();
		for (ItemDto itemDto : itemDtos) {
			items.add(itemDtoToItem(itemDto));
		}
		return items;
	}
}
