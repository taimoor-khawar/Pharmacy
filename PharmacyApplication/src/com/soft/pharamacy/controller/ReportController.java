package com.soft.pharamacy.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.soft.pharmacy.dao.CustomerDAO;
import com.soft.pharmacy.dao.ReportDAO;
import com.soft.pharmacy.model.Bill;
import com.soft.pharmacy.model.Customer;


@Controller
public class ReportController {
	
	@Autowired
	ReportDAO reportdao;
	
	@Autowired
	CustomerDAO cusdao;
	
	@RequestMapping(value="/report/orderbycustomer",method=RequestMethod.GET)  
    public ModelAndView orderlist(@RequestParam(value="EnterprisedID",defaultValue="") long EnterprisedID
    		){
		ModelAndView reportmodelandview = new ModelAndView();
		ArrayList<Customer> customerslist = cusdao.getCustomers(EnterprisedID);
		reportmodelandview.addObject("customerslist", customerslist);
		reportmodelandview.setViewName("reports/OrderCustomerReport");
		return reportmodelandview;
    }
	
	@RequestMapping(value="/report/orderbycustomer",method=RequestMethod.POST
			)  
    public ModelAndView orderlist(@RequestParam(value="EnterprisedID",defaultValue="") long EnterprisedID,
    		@RequestParam(value="CustomerID",defaultValue="") long CustomerID,
    		@RequestParam(value="fromdate",defaultValue="") String FromDate,
    		@RequestParam(value="todate",defaultValue="") String ToDate,
    		HttpServletRequest request) throws IOException{
		ModelAndView reportmodelandview = new ModelAndView();
		ArrayList<Customer> customerslist = cusdao.getCustomers(EnterprisedID);
		ArrayList<Bill> orderslist = reportdao.getBillsByCustomerID(CustomerID, EnterprisedID,FromDate,ToDate);
		
		reportmodelandview.addObject("CustomerID", "s"+CustomerID);
		reportmodelandview.addObject("FromDate", FromDate);
		reportmodelandview.addObject("ToDate", ToDate);
		
		reportmodelandview.addObject("filename", "OrderCustomerReport");
		reportmodelandview.addObject("orderslist", orderslist);
		reportmodelandview.addObject("customerslist", customerslist);
		reportmodelandview.setViewName("reports/OrderCustomerReport");
		return reportmodelandview;
    }
	
	
	
	@RequestMapping(value="/report/orderlistexport",method=RequestMethod.GET
			)  
    public ModelAndView orderlistexport(@RequestParam(value="EnterprisedID",defaultValue="0") long EnterprisedID,
    		@RequestParam(value="CustomerID",defaultValue="0") long CustomerID,
    		HttpServletRequest request,@RequestParam(value="fromdate",defaultValue="") String FromDate,
    		@RequestParam(value="todate",defaultValue="") String ToDate
    		) throws IOException{
		ArrayList<Bill> orderslist = reportdao.getBillsByCustomerID(CustomerID, EnterprisedID,FromDate,ToDate);
		
		if(orderslist.size() > 0){
			return new ModelAndView("ExcelView","orderslist",orderslist);
		}else{
			return new ModelAndView("reports/OrderCustomerReport");
		}
//		return null;
	}

}
