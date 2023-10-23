package com.trafilea.test.coffeeshop.application.dtos;

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
public class OrderTotalsDto implements Serializable {
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = -3372547372752720620L;
	
	private Long orderID;
	private Double totalProducts;
	private Double totalDiscounts;
	private Double totalShipping;
	private Double totalOrder;
}
