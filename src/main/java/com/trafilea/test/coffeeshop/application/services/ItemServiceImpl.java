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
import com.trafilea.test.coffeeshop.domain.enums.StatusEnum;
import com.trafilea.test.coffeeshop.domain.repositories.ItemRepository;
import com.trafilea.test.coffeeshop.domain.services.CartService;
import com.trafilea.test.coffeeshop.domain.services.ItemService;
import com.trafilea.test.coffeeshop.domain.services.ProductService;

import lombok.RequiredArgsConstructor;

@Scope("singleton")
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
	
	private static final String MSG_CART_CLOSE = "The cart is close.";
	private static final String MSG_MUST_SEND_A_VALID_ITEM = "You must send a valid item.";
	private static final String MSG_HAS_NOT_BEEN_CREATED = " has not been created.";
	private static final String MSG_AND_PRODUCT = " and product ";
	private static final String MSG_ITEM_CART_ID = "The item with cart id ";
	private static final String MSG_MUST_SEND_VALID_PRODUCT = "You must send a valid product.";
	private static final String MSG_MUST_SEND_VALID_AMOUNT = "You must send a valid amount.";
	private static final String MSG_CART_WITH_ID = "The cart with id ";
	private static final String MSG_DOES_NOT_EXIST = " does not exist.";
	private static final String MSG_PRODUCT_ID = "The product with id ";
	private static final String MSG_POINT = ".";
	private static final String MSG_QUANTITY_IS_NULL = "The quantity is null for the product with id ";
	private static final String MSG_SEND_LIST_ITEMS_VALID = "You must send a list of items with valid information.";
	private static final String MSG_MUST_SEND_VALID_CART = "You must send a valid cart.";
	
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;
	
	@Override
	public List<Item> findByCart(Cart cart) throws Exception {
		if (cart==null) {
			throw new Exception(MSG_MUST_SEND_VALID_CART);
		}
		return itemRepository.findByCart(cart);
	}

	@Override
	public void addItemsToCart(Long cartId,List<ItemDto> itemDtos) throws Exception {
		
		Cart cart;
		
		if (cartId==null || cartId<0) {
			throw new Exception(MSG_MUST_SEND_VALID_CART);
		}
		if (itemDtos==null || itemDtos.isEmpty()) {
			throw new Exception(MSG_SEND_LIST_ITEMS_VALID);
		}
		
		cart=cartService.findById(cartId);
		
		if (cart==null) {
			throw new Exception(MSG_CART_WITH_ID + cartId + MSG_DOES_NOT_EXIST);
		}
		if (cart.getStatus().equals(StatusEnum.CLOSE)) {
			throw new Exception(MSG_CART_CLOSE);
		}
		
		List<Item>items=new ArrayList<Item>();
		for (ItemDto itemDto : itemDtos) {
			Product product=productService.findById(itemDto.getProduct());
			if (product==null) {
				throw new Exception(MSG_PRODUCT_ID + itemDto.getProduct() + MSG_DOES_NOT_EXIST);
			}
			if (itemDto.getQuantity()==null) {
				throw new Exception(MSG_QUANTITY_IS_NULL + itemDto.getProduct() + MSG_POINT);
			}
			if (itemDto.getDiscounts()==null) {
				itemDto.setDiscounts(0D);
			}
			Item item=Item.builder()
					.cart(cart)
					.product(product)
					.price(product.getPrice())
					.quantity(itemDto.getQuantity())
					.discounts(itemDto.getDiscounts())
					.build();
			items.add(item);
		}
		
		itemRepository.saveAll(items);
	}
	
	@Override
	public void updateQuantityByCartProduct(Long cartId, Long productId, Integer quantity) throws Exception {
		
		Cart cart;
		Product product;
		
		if (quantity==null || quantity<0) {
			throw new Exception(MSG_MUST_SEND_VALID_AMOUNT);
		}
		if (cartId==null || cartId<0) {
			throw new Exception(MSG_MUST_SEND_VALID_CART);
		}
		if (productId==null || productId<0) {
			throw new Exception(MSG_MUST_SEND_VALID_PRODUCT);
		}
		
		cart= cartService.findById(cartId);
		product= productService.findById(productId);
		
		if (cart==null) {
			throw new Exception(MSG_CART_WITH_ID + cartId + MSG_DOES_NOT_EXIST);
		}
		if (cart.getStatus().equals(StatusEnum.CLOSE)) {
			throw new Exception(MSG_CART_CLOSE);
		}
		if (product==null) {
			throw new Exception(MSG_PRODUCT_ID + productId + MSG_DOES_NOT_EXIST);
		}
		
		Item item= itemRepository.findByCartAndProduct(cart, product);
		
		if (item==null) {
			throw new Exception(MSG_ITEM_CART_ID + cartId + MSG_AND_PRODUCT+ productId +MSG_HAS_NOT_BEEN_CREATED);
		}
		if (quantity==0) {
			itemRepository.delete(item);
			return;
		}
		
		item.setQuantity(quantity);
		itemRepository.save(item);
	}

	@Override
	public void save(Item item) throws Exception {
		if (item==null) {
			throw new Exception(MSG_MUST_SEND_A_VALID_ITEM);
		}
		itemRepository.save(item);
	}
	
}
