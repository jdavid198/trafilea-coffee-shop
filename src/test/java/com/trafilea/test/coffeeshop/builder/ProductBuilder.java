package com.trafilea.test.coffeeshop.builder;

import com.trafilea.test.coffeeshop.controller.dtos.ProductDto;
import com.trafilea.test.coffeeshop.entities.Product;
import com.trafilea.test.coffeeshop.enums.CategoryEnum;

public class ProductBuilder {
	public static Product getProduct(){
		return Product.builder()
				.productId(1L)
				.name("American Coffee")
				.category(CategoryEnum.COFFEE)
				.price(10D)
				.build();
	}
	public static ProductDto getProductDto(){
		return ProductDto.builder()
				.name("American Coffee")
				.category(CategoryEnum.COFFEE.name())
				.price(10D)
				.build();
	}
}
