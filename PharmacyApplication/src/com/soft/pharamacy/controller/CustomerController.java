package com.soft.pharamacy.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.soft.pharmacy.dao.CustomerDAO;
import com.soft.pharmacy.model.Customer;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired  
    CustomerDAO customerdao;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)  
    public ModelAndView customerlist(@RequestParam(value="EnterprisedID",defaultValue="") long EnterprisedID){
		ModelAndView customerlistmodelview = new ModelAndView();
		ArrayList<Customer> customerslist = customerdao.getCustomers(EnterprisedID);
		customerlistmodelview.addObject("customerslist", customerslist);
		System.out.println("Incontroller="+(Customer)customerslist.get(0));
		customerlistmodelview.setViewName("customer/CustomerList");
		return customerlistmodelview;
    }
	
	@RequestMapping(value="/add",method=RequestMethod.GET)  
    public ModelAndView newcustomer(){ 
		 
		return new ModelAndView("customer/AddCustomer","command",new Customer());
    }
	
	@RequestMapping(value="/add",method=RequestMethod.POST)  
    public ModelAndView addcustomer(@ModelAttribute("customer") Customer customer){ 
		
		int result = 0;
		String Message="";
		ModelAndView customermodelview = new ModelAndView();
		
		if(customer != null)
			result = customerdao.save(customer);
		
		customermodelview.addObject("result", result);
		if(result ==1)
			Message =  "Customer Successfully added";
		else if(result==0)
			Message =  "Customer Successfully not added";
		
		ArrayList<Customer> customerslist = customerdao.getCustomers(customer.getEnterpriseID());
		
		customermodelview.addObject("Message", Message);
		customermodelview.addObject("customerslist", customerslist);
		customermodelview.setViewName("customer/CustomerList");
		
		return customermodelview;
    }
	
	@RequestMapping(value="/edit",method=RequestMethod.GET)  
    public ModelAndView editcustomer(@RequestParam(value="EnterprisedID",defaultValue="0") long EnterprisedID,
    		@RequestParam(value="CustomerID",defaultValue="0") long CustomerID){ 
		
		Customer customer = customerdao.getCustomer(CustomerID, EnterprisedID);
		return new ModelAndView("customer/EditCustomer","customer",customer);
    }
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)  
    public ModelAndView updatecustomer(@ModelAttribute("customer") Customer customer){ 
		
		int result = 0;
		String Message= "";
		ModelAndView customermodelview = new ModelAndView();
		
		if(customer != null)
			result = customerdao.update(customer);
		
		if(result ==1)
			Message =  "Customer Successfully Updated";
		else if(result==0)
			Message =  "Customer Successfully not Updated";
		
		ArrayList<Customer> customerslist = customerdao.getCustomers(customer.getEnterpriseID());
		
		customermodelview.addObject("Message", Message);
		customermodelview.addObject("result", result);
		customermodelview.addObject("customerslist", customerslist);
		customermodelview.setViewName("customer/CustomerList");
		
		return customermodelview;
    }
	
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)  
    public ModelAndView deletecustomer(@RequestParam(value="EnterprisedID",defaultValue="0") long EnterprisedID,
    		@RequestParam(value="CustomerID",defaultValue="0") long CustomerID){ 
		
		int result = 0;
		String Message= "";
		ModelAndView customermodelview = new ModelAndView();
		
		if(EnterprisedID != 0)
			result = customerdao.delete(CustomerID,EnterprisedID);
		
		if(result ==1)
			Message =  "Customer Successfully Deleted";
		else if(result==0)
			Message =  "Customer Successfully not Deleted";
		
		ArrayList<Customer> customerslist = customerdao.getCustomers(EnterprisedID);
		
		customermodelview.addObject("Message", Message);
		customermodelview.addObject("result", result);
		customermodelview.addObject("customerslist", customerslist);
		customermodelview.setViewName("customer/CustomerList");
		
		return customermodelview;
    }
	
	@RequestMapping(value="/view",method=RequestMethod.GET)  
    public ModelAndView viewcustomer(@RequestParam(value="EnterprisedID",defaultValue="0") long EnterprisedID,
    		@RequestParam(value="CustomerID",defaultValue="0") long CustomerID){ 
		
		Customer customer = customerdao.getCustomer(CustomerID, EnterprisedID);
		return new ModelAndView("customer/ViewCustomer","customer",customer);
    }
	
	
}
