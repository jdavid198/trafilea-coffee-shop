package com.trafilea.test.coffeeshop.application.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDto implements java.io.Serializable{
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = -2482099859346341898L;

	private Long productId;
	private String name;
	private String category;
	private Double price;
}
