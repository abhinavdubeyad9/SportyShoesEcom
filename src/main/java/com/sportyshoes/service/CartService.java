package com.sportyshoes.service;

import java.util.List;

import com.sportyshoes.model.Cart;
import com.sportyshoes.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	public void saveCart(Cart cart) {
		cartRepository.save(cart);
	}

	public List<Cart> getAllCart() {
		return cartRepository.findAll();
	}

	public void cartDeleteAll() {
		cartRepository.deleteAll();
	}
}
