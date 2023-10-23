package com.trafilea.test.coffeeshop.application.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.trafilea.test.coffeeshop.application.dtos.ItemDto;
import com.trafilea.test.coffeeshop.domain.entities.Cart;
import com.trafilea.test.coffeeshop.domain.entities.Item;
import com.trafilea.test.coffeeshop.domain.entities.Product;
import com.trafilea.test.coffeeshop.domain.repositories.ItemRepository;
import com.trafilea.test.coffeeshop.domain.services.CartService;
import com.trafilea.test.coffeeshop.domain.services.ItemService;
import com.trafilea.test.coffeeshop.domain.services.ProductService;

import lombok.RequiredArgsConstructor;

@Scope("singleton")
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;
	
	@Override
	public List<Item> findByCart(Cart cart) {
		return itemRepository.findByCart(cart);
	}

	@Override
	public void addItemsToCart(Long cartId,List<ItemDto> itemDtos) throws Exception {
		
		Cart cart=cartService.findById(cartId);
		if (cart==null) {
			throw new Exception("The cart with id " + cartId + " does not exist.");
		}
		
		List<Item>items=new ArrayList<Item>();
		for (ItemDto itemDto : itemDtos) {
			Product product=productService.findById(itemDto.getProduct());
			if (product==null) {
				throw new Exception("The product with id " + itemDto.getProduct() + " does not exist.");
			}
			if (itemDto.getQuantity()==null) {
				throw new Exception("The quantity is null for the product with id " + itemDto.getProduct() + ".");
			}
			if (itemDto.getDiscounts()==null) {
				itemDto.setDiscounts(0D);
			}
			Item item=Item.builder().cart(cart).product(product).quantity(itemDto.getQuantity()).discounts(itemDto.getDiscounts()).build();
			items.add(item);
		}
		
		itemRepository.saveAll(items);
	}
	
	@Override
	public void updateQuantityByCartProduct(Long cartId, Long productId, Integer quantity) throws Exception {
		Cart cart= cartService.findById(cartId);
		Product product= productService.findById(productId);
		
		if (quantity==null || quantity<0) {
			throw new Exception("You must send a valid amount.");
		}
		if (cart==null) {
			throw new Exception("The cart with id " + cartId + " does not exist.");
		}
		if (product==null) {
			throw new Exception("The product with id " + productId + " does not exist.");
		}
		
		Item item= itemRepository.findByCartAndProduct(cart, product);
		if (quantity==0) {
			itemRepository.delete(item);
			return;
		}
		item.setQuantity(quantity);
		itemRepository.save(item);
	}

	@Override
	public void save(Item item) throws Exception {
		itemRepository.save(item);
	}
	
}
