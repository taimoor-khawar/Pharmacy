package com.soft.pharamacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.soft.pharmacy.dao.LoginDAO;
import com.soft.pharmacy.model.Department;

@Controller
public class ConnectionController {
	
	@Autowired  
    LoginDAO dao;
	
	@RequestMapping(value="/login")  
    public ModelAndView loginpage(){ 
		Department department = new Department(0,"","Taimoor","abc");
		System.out.println(dao.save(department));
		return new ModelAndView("Login");
    }

}
