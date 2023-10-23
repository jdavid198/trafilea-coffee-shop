package com.trafilea.test.coffeeshop.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TRAFILEA_CART")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Cart implements Serializable{
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = 7838789036777626365L;
	
	@Id
	@Column(name = "CART_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CALL_SEQ_CART_ID")
	@SequenceGenerator(sequenceName = "SEQ_CART_ID", allocationSize = 1, name = "CALL_SEQ_CART_ID")
	private Long cartId;
	@Column(name = "USER_ID", nullable = false, length = 30)
	private String userID;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "products")
	private List<Product> products;
}
