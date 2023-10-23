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
@Table(name = "TRAFILEA_ORDER")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order implements Serializable{
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = -6469318038826491244L;
	
	@Id
	@Column(name = "ORDER_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CALL_SEQ_ORDER_ID")
	@SequenceGenerator(sequenceName = "SEQ_ORDER_ID", allocationSize = 1, name = "CALL_SEQ_ORDER_ID")
	private Long orderID;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CART_ID", nullable = false)
	private Cart cart;
	@Column(name = "TOTAL_PRODUCTS", nullable = false)
	private Double totalProducts;
	@Column(name = "TOTAL_DISCOUNTS")
	private Double totalDiscounts;
	@Column(name = "TOTAL_SHIPPING")
	private Double totalShipping;
	@Column(name = "TOTAL_ORDER", nullable = false)
	private Double totalOrder;
}
