package com.trafilea.test.coffeeshop.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StatusEnum {
	OPEN("Open"), CLOSE("Close");
	@Getter
	private String value;
}
