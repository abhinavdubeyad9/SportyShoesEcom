package com.sportyshoes.repository;

import java.util.List;

import com.sportyshoes.model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, String> {

	Customer findByEmail(String email);

	@Query("SELECT c FROM Customer c WHERE c.email LIKE %?1%"
			+ " OR c.name LIKE %?1%"
			+ " OR c.contact LIKE %?1%")
	public List<Customer> userSearch(String name);

	@Query("SELECT c.email from Customer c")
	public List<String> customerEmails();
}