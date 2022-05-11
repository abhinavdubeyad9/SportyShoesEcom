package com.sportyshoes.controller;

//import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.sportyshoes.model.Cart;
import com.sportyshoes.model.Customer;
import com.sportyshoes.model.Product;
import com.sportyshoes.model.Purchase;
import com.sportyshoes.service.CartService;
import com.sportyshoes.service.CustomerService;
import com.sportyshoes.service.PurchaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PurchaseService purchaseService;

	@ExceptionHandler(Exception.class)
	public String handleSqlException(Exception e, HttpSession session) {
		session.setAttribute("action", "Choose Payment before Buying");
		return "redirect:/viewCart";
	}

	@PostMapping("/confirmCart")
	public String addToCart(@RequestParam("size") float size, @RequestParam("quantity") int quantity,
			HttpSession session) {
		Cart cart = new Cart();
		Product product = (Product) session.getAttribute("product");
		int min = 100;
		int max = 999;
		int b = (int) (Math.random() * (max - min + 1) + min);
		cart.setId(b);
		cart.setProductId(product.getId());
		cart.setQuantity(quantity);
		cart.setPrice(product.getPrice() * quantity);
		cart.setSize(size);
		cartService.saveCart(cart);
		session.setAttribute("action", "Product added to cart");
		float temp = 0;
		if (session.getAttribute("sessionCost") == null) {
			temp = 0;
		} else {
			temp = (float) session.getAttribute("sessionCost");
		}
		float sessionCost = (cart.getPrice() + temp);
		session.setAttribute("sessionCost", sessionCost);
		return "redirect:/";
	}

	@GetMapping("/viewCart")
	public String viewCart(Model model, HttpSession session) {
		List<Cart> cartList = cartService.getAllCart();
		if (!cartList.isEmpty()) {
			model.addAttribute("cartList", cartList);
			model.addAttribute("action", session.getAttribute("action"));
			session.setAttribute("action", null);
			return "viewCart";
		} else {
			session.setAttribute("action", "No products currently in Cart");
			return "redirect:/";
		}
	}

	@PostMapping("/buyNow")
	public String buyProducts(@RequestParam("pm") String pm, HttpSession session) {
		System.out.println(pm);
		if (pm.equals("yes")) {
			List<Cart> cartList = cartService.getAllCart();
			Purchase purchase = new Purchase();
			String email = (String) session.getAttribute("customerLogin");
			Customer customer = customerService.getCustomer(email);
			for (Cart cl : cartList) {
				java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
				int min = 100000;
				int max = 999999;
				int b = (int) (Math.random() * (max - min + 1) + min);
				purchase.setId(b);
				purchase.setDop(date);
				System.out.println(date);
				purchase.setCustomer(customer);
				purchase.setProductid(cl.getProductId());
				purchase.setQuantity(cl.getQuantity());
				purchase.setTotalcost(cl.getPrice());
				purchaseService.addPurchase(purchase);
			}
			session.setAttribute("action", "Products added to Customer Order List Sucessfully");
			return "redirect:/";
		} else {
			session.setAttribute("action", "Make Payment before to finilize orders");
			return "redirect:/viewCart";
		}
	}

}
