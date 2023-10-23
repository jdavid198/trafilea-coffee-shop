package com.trafilea.test.coffeeshop.domain.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TRAFILEA_ITEM")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Item implements Serializable{
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = -7414591672445559447L;
	
	@Id
	@Column(name = "ITEM_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CALL_SEQ_PRODUCT_CART_ID")
	@SequenceGenerator(sequenceName = "SEQ_PRODUCT_CART_ID", allocationSize = 1, name = "CALL_SEQ_PRODUCT_CART_ID")
	private Long itemId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CART_ID")
	private Cart cart;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;
	@Column(name = "PRICE", nullable = false, precision = 2)
	private Double price;
	@Column(name = "QUANTITY", nullable = false)
	private Integer quantity;
	@Column(name = "DISCOUNTS", nullable = false)
	private Double discounts;

	public Double getTotalPrice() {
		return price*quantity;
	}
	
}
