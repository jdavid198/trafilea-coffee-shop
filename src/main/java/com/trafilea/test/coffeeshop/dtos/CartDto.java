package com.trafilea.test.coffeeshop.dtos;

import java.io.Serializable;
import java.util.List;

import com.trafilea.test.coffeeshop.enums.StatusEnum;

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
public class CartDto implements Serializable{
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = -5537071016350934567L;
	
	private Long cartId;
	private Long userID;
	private StatusEnum status;
	private List<ItemDto> ItemDtos;
}
