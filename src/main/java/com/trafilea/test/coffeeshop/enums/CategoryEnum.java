package com.trafilea.test.coffeeshop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CategoryEnum {
	COFFEE("Coffee"), EQUIPMENT("Equipment"), ACCESSORIES("Accessories");
	@Getter
	private String value;
}
