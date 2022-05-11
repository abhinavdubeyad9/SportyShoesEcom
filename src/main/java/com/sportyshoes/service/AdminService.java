package com.sportyshoes.service;

import com.sportyshoes.model.Admin;
import com.sportyshoes.repository.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;

	public Admin getAdmin(String username) {
		return adminRepository.findByUsername(username);
	}

	public boolean loginVerify(String username, String password) {
		Admin admin = adminRepository.findByUsername(username);
		if (admin != null && admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
			return true;
		}
		return false;
	}

	public void updatePassword(Admin admin) {
		adminRepository.save(admin);

	}

}
