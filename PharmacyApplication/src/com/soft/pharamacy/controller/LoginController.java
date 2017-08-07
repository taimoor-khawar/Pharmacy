package com.soft.pharamacy.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.soft.pharmacy.dao.LoginDAO;
import com.soft.pharmacy.model.SysUser;

@Controller
public class LoginController {
	
	@Autowired  
    LoginDAO dao;
	
	@RequestMapping(value="/login")  
    public ModelAndView loginpage(){ 
		 
		return new ModelAndView("Login");
    }
	
	@RequestMapping(value="/test")  
    public ModelAndView testpage(){ 
		 
		return new ModelAndView("test");
    }
	
	
	@RequestMapping(value="/mainlogin",method = RequestMethod.POST)  
    public ModelAndView showmain(@RequestParam(value="username",defaultValue="") String Username,
    		@RequestParam(value="password",defaultValue="")String Password,
    		final HttpServletRequest request) throws SQLException, Exception{ 
		 if(Username==null)Username="";
		 if(Password==null)Password="";
		
		 ModelAndView loginmodelview = null;
		 SysUser sysuser = dao.getLoginByUsernameaDMIN(Username,Password,request);
		 
		 if(sysuser != null){
				 loginmodelview =  new ModelAndView("dashboard","Message","You entered username and password as a Student");
			 }
			 else{ 
				 loginmodelview =  new ModelAndView("/Login","Message","You entered invalid username and password");
			 }
		 
		return loginmodelview;
    }

}
