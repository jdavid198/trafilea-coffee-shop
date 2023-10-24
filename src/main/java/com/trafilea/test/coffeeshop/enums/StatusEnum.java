package com.trafilea.test.coffeeshop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StatusEnum {
	OPEN("Open"), CLOSE("Close");
	@Getter
	private String value;
}
