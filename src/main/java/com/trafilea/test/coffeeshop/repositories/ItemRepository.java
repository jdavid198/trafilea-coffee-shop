package com.trafilea.test.coffeeshop.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trafilea.test.coffeeshop.entities.Cart;
import com.trafilea.test.coffeeshop.entities.Item;
import com.trafilea.test.coffeeshop.entities.Product;

public interface ItemRepository extends JpaRepository<Item, Long>{
	public List<Item> findByCart(Cart cart);
	public Optional<Item> findByCartAndProduct(Cart cart,Product product);
}