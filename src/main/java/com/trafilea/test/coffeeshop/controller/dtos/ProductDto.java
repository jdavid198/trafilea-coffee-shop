package com.trafilea.test.coffeeshop.controller.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

	@NotNull
	private Long productId;
	@NotNull
	@NotEmpty
	private String name;
	@NotNull
	@NotEmpty
	private String category;
	@NotNull
	private Double price;
}
