package com.soft.pharmacy.service.Impl;

import org.springframework.transaction.annotation.Transactional;

import com.soft.pharmacy.dao.LoginDAO;
import com.soft.pharmacy.model.Department;
import com.soft.pharmacy.service.LoginManager;

public class LoginManagerImpl implements LoginManager{

	private LoginDAO loginDAO;

	public void setCustomerDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}
	
	@Transactional
	@Override
	public int save(Department department) {
		// TODO Auto-generated method stub
		return loginDAO.save(department);
	}

}
