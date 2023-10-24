package com.trafilea.test.coffeeshop.services.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trafilea.test.coffeeshop.builder.ItemBuilder;
import com.trafilea.test.coffeeshop.entities.Item;

@ExtendWith(MockitoExtension.class)
public class SpecialRuleServiceImplTest {
	
	@InjectMocks
	private RuleServiceImpl specialRuleServiceImpl;

	@Nested
	public class ruleCategoryCoffee{
		@Test
		public void appliedRule() throws Exception {
			// Arrange
			List<Item> items=ItemBuilder.getItemsRuleCategoryCoffee();
			// Assert
			assertTrue(specialRuleServiceImpl.ruleCategoryCoffee(items));
		}
		@Test
		public void ruleNotApply() throws Exception {
			// Arrange
			List<Item> items=ItemBuilder.getItemsRuleCategoryEquipment();
			// Assert
			assertFalse(specialRuleServiceImpl.ruleCategoryCoffee(items));
		}
	}
	
	@Nested
	class ruleCategoryEquipment{
		@Test
		public void appliedRule() throws Exception {
			// Arrange
			List<Item> items=ItemBuilder.getItemsRuleCategoryEquipment();
			// Assert
			assertTrue(specialRuleServiceImpl.ruleCategoryEquipment(items));
		}
		@Test
		public void ruleNotApply() throws Exception {
			// Arrange
			List<Item> items=ItemBuilder.getItemsRuleCategoryCoffee();
			// Assert
			assertFalse(specialRuleServiceImpl.ruleCategoryEquipment(items));
		}
	}
	
	@Nested
	class ruleCategoryAccessories{
		@Test
		public void appliedRule() throws Exception {
			// Arrange
			List<Item> items=ItemBuilder.getItemsRuleCategoryAccessories();
			// Assert
			assertTrue(specialRuleServiceImpl.ruleCategoryAccessories(items));
		}
		@Test
		public void ruleNotApply() throws Exception {
			// Arrange
			List<Item> items=ItemBuilder.getItemsRuleCategoryCoffee();
			// Assert
			assertFalse(specialRuleServiceImpl.ruleCategoryAccessories(items));
		}
	}
	
}
