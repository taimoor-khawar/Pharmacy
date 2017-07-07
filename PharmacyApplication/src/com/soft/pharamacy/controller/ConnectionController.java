package com.soft.pharamacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.soft.pharmacy.dao.LoginDAO;

@Controller
public class ConnectionController {
	
	@Autowired  
    LoginDAO dao;
	
	@RequestMapping(value="/login")  
    public ModelAndView loginpage(){ 
		System.out.println("Value="+dao.save());
		return new ModelAndView("Login");
    }

}
