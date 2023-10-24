package com.trafilea.test.coffeeshop.dtos;

import java.io.Serializable;

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
public class OrderDto implements Serializable {
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = -3372547372752720620L;
	
	private Long orderID;
	private CartDto cartDto;
	private Double totalProducts;
	private Double totalDiscounts;
	private Double totalShipping;
	private Double totalOrder;
}
