package com.trafilea.test.coffeeshop.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trafilea.test.coffeeshop.domain.entities.Cart;
import com.trafilea.test.coffeeshop.domain.entities.Item;
import com.trafilea.test.coffeeshop.domain.entities.Product;

public interface ItemRepository extends JpaRepository<Item, Long>{
	public List<Item> findByCart(Cart cart);
	public Item findByCartAndProduct(Cart cart,Product product);
}