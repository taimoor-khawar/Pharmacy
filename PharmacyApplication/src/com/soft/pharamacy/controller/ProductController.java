package com.soft.pharamacy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.soft.pharmacy.model.Product;

@Controller
public class ProductController {
	
	@RequestMapping(value="/addproduct",method=RequestMethod.GET)  
    public ModelAndView loginpage(){ 
		 
		return new ModelAndView("AddProduct","command",new Product());
    }

}
