package com.trafilea.test.coffeeshop.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.trafilea.test.coffeeshop.application.dtos.OrderDto;
import com.trafilea.test.coffeeshop.application.dtos.OrderTotalsDto;
import com.trafilea.test.coffeeshop.domain.entities.Cart;
import com.trafilea.test.coffeeshop.domain.entities.Item;
import com.trafilea.test.coffeeshop.domain.entities.Order;
import com.trafilea.test.coffeeshop.domain.enums.CategoryEnum;
import com.trafilea.test.coffeeshop.domain.enums.StatusEnum;
import com.trafilea.test.coffeeshop.domain.repositories.OrderRepository;
import com.trafilea.test.coffeeshop.domain.services.CartService;
import com.trafilea.test.coffeeshop.domain.services.ItemService;
import com.trafilea.test.coffeeshop.domain.services.OrderService;
import com.trafilea.test.coffeeshop.domain.services.SpecialRuleService;

import lombok.RequiredArgsConstructor;

@Scope("singleton")
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
	
	private static final String MSG_ITEMS_HAVE_NOT_BEEN_ADDED_CART = "The items have not been added to the cart";
	private static final String MSG_MUST_SEND_CART = "You must send the cart.";
	private static final String MSG_ALREADY_ORDER_CREATED = "There is already an order created for that cart.The order is ";
	private static final String MSG_MUST_SEND_VALID_CART = "You must send a valid cart.";
	private static final double DEFAULT_VALUE_TOTALS = 0.0;
	private static final double DEFAULT_SHIPING = 20.0;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CartService cartService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private SpecialRuleService specialRuleService;
	
	@Override
	public OrderTotalsDto create(OrderDto orderDto) throws Exception{
		
		Order order;
		Cart cart;
		List<Item> items;
		Double totalProducts;
		Double totalDiscounts;
		Double totalShipping;
		Double totalOrder;
		
		if (orderDto==null || orderDto.getCartId()==null) {
			throw new Exception(MSG_MUST_SEND_CART);
		}
		
		cart=cartService.findById(orderDto.getCartId());
		
		if (cart==null) {
			throw new Exception(MSG_MUST_SEND_VALID_CART);
		}	
		
		totalProducts=DEFAULT_VALUE_TOTALS;
		totalDiscounts=DEFAULT_VALUE_TOTALS;
		totalShipping=DEFAULT_SHIPING;
		totalOrder=DEFAULT_VALUE_TOTALS;
			
		order=orderRepository.findByCart(cart);
		
		if (order!=null) {
			throw new Exception(MSG_ALREADY_ORDER_CREATED+order.getOrderID());
		}	
		
		items=itemService.findByCart(cart);
		
		if (items==null || items.isEmpty()) {
			throw new Exception(MSG_ITEMS_HAVE_NOT_BEEN_ADDED_CART);
		}
		
		for (Item item : items) {
			Double totalItem=item.getProduct().getPrice()*item.getQuantity();
			if (item.getDiscounts()!=null && item.getDiscounts()>0) {
				totalProducts+=totalItem-(totalItem*(item.getDiscounts()/100));
			}
			totalProducts+=totalItem;
		}
		
		if (specialRuleService.ruleCategoryCoffee(items)) {
			Item itemCoffee=items.stream()
            .filter(item -> item.getProduct().getCategory().equals(CategoryEnum.COFFEE))
            .findFirst().get();
			Item newItem=Item.builder()
					.cart(cart)
					.product(itemCoffee.getProduct())
					.price(0D)
					.quantity(1)
					.discounts(0D)
					.build();
			itemService.save(newItem);
		}
		if (specialRuleService.ruleCategoryEquipment(items)) {
			totalShipping=0.0;
		}
		if (specialRuleService.ruleCategoryAccessories(items)) {
			double discount=(double)10/100;
			totalDiscounts=totalProducts*discount;
		}
		
		totalOrder=totalProducts-totalDiscounts+totalShipping;
		
		cart.setStatus(StatusEnum.CLOSE);
		cartService.save(cart);
		
		order=Order.builder()
					.cart(cart)
					.totalProducts(totalProducts)
					.totalDiscounts(totalDiscounts)
					.totalShipping(totalShipping)
					.totalOrder(totalOrder).build();
		
		orderRepository.save(order);
		
		OrderTotalsDto orderTotalsDto=OrderTotalsDto.builder()
				.orderID(order.getOrderID())
				.totalProducts(totalProducts)
				.totalDiscounts(totalDiscounts)
				.totalShipping(totalShipping)
				.totalOrder(totalOrder).build();
		return orderTotalsDto;
	}
	
}
