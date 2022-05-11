package com.sportyshoes.repository;

import java.util.List;

import com.sportyshoes.model.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, String> {
	Admin findByUsername(String username);

	@Query(value = "SELECT username FROM Admin", nativeQuery = true)
	public List<String> findUsenames();
}
