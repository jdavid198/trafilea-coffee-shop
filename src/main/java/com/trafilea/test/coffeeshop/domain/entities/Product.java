package com.trafilea.test.coffeeshop.domain.entities;

import com.trafilea.test.coffeeshop.enums.CategoryEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "TRAFILEA_PRODUCT")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product implements java.io.Serializable{
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = 7111898626110419610L;

	@Id
	@Column(name = "PRODUCT_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CALL_SEQ_PRODUCT_ID")
	@SequenceGenerator(sequenceName = "SEQ_PRODUCT_ID",initialValue = 10, allocationSize = 1, name = "CALL_SEQ_PRODUCT_ID")
	private Long productId;
	@Column(name = "NAME", nullable = false, length = 200)
	private String name;
	@Column(name = "CATEGORY", nullable = false, precision = 2)
	@Enumerated(EnumType.STRING)
	private CategoryEnum category;
	@Column(name = "PRICE", nullable = false, precision = 2)
	private Double price;
}
