package com.trafilea.test.coffeeshop.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.trafilea.test.coffeeshop.controller.dtos.OrderRequestDto;
import com.trafilea.test.coffeeshop.dtos.OrderDto;
import com.trafilea.test.coffeeshop.entities.Cart;
import com.trafilea.test.coffeeshop.entities.Item;
import com.trafilea.test.coffeeshop.entities.Order;
import com.trafilea.test.coffeeshop.enums.CategoryEnum;
import com.trafilea.test.coffeeshop.enums.StatusEnum;
import com.trafilea.test.coffeeshop.exceptions.ApiException;
import com.trafilea.test.coffeeshop.mappers.CartMapper;
import com.trafilea.test.coffeeshop.repositories.CartRepository;
import com.trafilea.test.coffeeshop.repositories.ItemRepository;
import com.trafilea.test.coffeeshop.repositories.OrderRepository;
import com.trafilea.test.coffeeshop.services.CalculatorService;
import com.trafilea.test.coffeeshop.services.OrderService;
import com.trafilea.test.coffeeshop.services.RuleService;
import com.trafilea.test.coffeeshop.utils.Messages;

import lombok.RequiredArgsConstructor;

@Scope("singleton")
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService,Messages{
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private RuleService ruleService;
	@Autowired
	private CalculatorService calculatorService;
	
	@Override
	public OrderDto create(OrderRequestDto orderRequestDto) throws ApiException{
		
		Optional<Cart> cartOptional=cartRepository.findById(orderRequestDto.getCartId());
		if (!cartOptional.isPresent()) {
			throw new ApiException(HttpStatus.BAD_REQUEST,MSG_MUST_SEND_VALID_CART);
		}	
			
		Optional<Order> orderOptional=orderRepository.findByCart(cartOptional.get());
		if (orderOptional.isPresent()) {
			throw new ApiException(HttpStatus.BAD_REQUEST,MSG_ALREADY_ORDER_CREATED+orderOptional.get().getOrderID());
		}	
		
		List<Item> items=itemRepository.findByCart(cartOptional.get());
		if (items == null || items.isEmpty()) {
			throw new ApiException(HttpStatus.BAD_REQUEST, MSG_ITEMS_HAVE_NOT_BEEN_ADDED_CART);
		}
		
		OrderDto orderDto= calculatorService.calculateOrder(items);
		
		if (ruleService.ruleCategoryCoffee(items)) {
			Item itemCoffee=items.stream().filter(item -> item.getProduct().getCategory().equals(CategoryEnum.COFFEE)).findFirst().get();
			Item freeCoffee=Item.builder().cart(cartOptional.get()).product(itemCoffee.getProduct()).price(0D).quantity(1).discounts(0D).build();
			itemRepository.save(freeCoffee);
		}
		
		cartOptional.get().setStatus(StatusEnum.CLOSE);
		cartRepository.save(cartOptional.get());
		
		Order order=Order.builder()
					.cart(cartOptional.get())
					.totalProducts(orderDto.getTotalProducts())
					.totalDiscounts(orderDto.getTotalDiscounts())
					.totalShipping(orderDto.getTotalShipping())
					.totalOrder(orderDto.getTotalOrder())
					.build();
		
		orderRepository.save(order);
		
		orderDto.setOrderID(order.getOrderID());
		orderDto.setCartDto(cartMapper.cartToCartDto(cartOptional.get()));
		
		return orderDto;
	}
	
}
