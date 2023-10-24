package com.trafilea.test.coffeeshop.services.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.trafilea.test.coffeeshop.services.ProductService;
import com.trafilea.test.coffeeshop.utils.Messages;

import lombok.RequiredArgsConstructor;

@Scope("singleton")
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService,Messages{
}
