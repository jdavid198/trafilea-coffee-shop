package com.trafilea.test.coffeeshop.services.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trafilea.test.coffeeshop.builder.ItemBuilder;
import com.trafilea.test.coffeeshop.dtos.OrderDto;
import com.trafilea.test.coffeeshop.entities.Item;
import com.trafilea.test.coffeeshop.services.RuleService;

@ExtendWith(MockitoExtension.class)
public class CalculatorServiceImplTest {
	@Mock
	private RuleService ruleService;
	@InjectMocks
	private CalculatorServiceImpl calculatorServiceImpl;

	@Nested
	public class calculateOrder{
		@Test
		public void appliedRule() throws Exception {
			// Arrange
			List<Item> items=ItemBuilder.getItemsRuleCategoryCoffee();
			items.get(0).setDiscounts(10D);
			items.add(ItemBuilder.getItem());
			when(ruleService.ruleCategoryAccessories(any())).thenReturn(true);
			when(ruleService.ruleCategoryEquipment(any())).thenReturn(true);
			// Assert
			OrderDto orderDto=calculatorServiceImpl.calculateOrder(items);
			assertNotNull(orderDto);
		}
	}
	
}
