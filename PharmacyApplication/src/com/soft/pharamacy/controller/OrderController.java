package com.soft.pharamacy.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.soft.pharmacy.dao.CustomerDAO;
import com.soft.pharmacy.dao.OrderDAO;
import com.soft.pharmacy.dao.ProductDAO;
import com.soft.pharmacy.model.Bill;
import com.soft.pharmacy.model.Billline;
import com.soft.pharmacy.model.Customer;
import com.soft.pharmacy.model.Product;


@Controller
public class OrderController {

	@Autowired
	ProductDAO prodao;
	
	@Autowired
	CustomerDAO cusdao;
	
	@Autowired
	OrderDAO orderdao;
	
	
	@RequestMapping(value="/order/orderlist",method=RequestMethod.GET)  
    public ModelAndView orderlist(@RequestParam(value="EnterprisedID",defaultValue="") long EnterprisedID){
		ModelAndView ordermodelandview = new ModelAndView();
		ArrayList<Bill> orderslist = orderdao.getBills(EnterprisedID);
		ordermodelandview.addObject("orderslist", orderslist);
		ordermodelandview.setViewName("order/OrderList");
		return ordermodelandview;
    }
	
	@RequestMapping(value="/order/neworder",method=RequestMethod.GET)  
    public ModelAndView neworder(@RequestParam(value="EnterprisedID",defaultValue="") long EnterprisedID){
		ModelAndView ordermodelandview = new ModelAndView();
		ArrayList<Customer> customerslist = cusdao.getCustomers(EnterprisedID);
		ArrayList<Product> productsslist = prodao.getProducts(EnterprisedID);
		ordermodelandview.addObject("productslist", productsslist);
		ordermodelandview.addObject("customerslist", customerslist);
		ordermodelandview.setViewName("order/NewOrder");
		return ordermodelandview;
    }
	
	@RequestMapping(value="/order/postorder",method=RequestMethod.POST)  
    public ModelAndView postorder(HttpServletRequest request){
		
		ModelAndView ordermodelview = new ModelAndView();
		ArrayList<Bill> billslist = new ArrayList<Bill>();
		ArrayList<Billline> billineslist = new ArrayList<Billline>();
		int result = 0;
		String Message="";
		long Bill_ID = orderdao.getBillID();
		double BillNetPrice = 0;
		double BillDiscount = 0;
		
		String enterprisedID = request.getParameter("EnterprisedID");
		if(enterprisedID==null)enterprisedID="0";
		long EnterprisedID = 0;
		try{
			EnterprisedID = Long.parseLong(enterprisedID);
		}catch(Exception e){
			EnterprisedID = 0;
		}
		
		String customerID = request.getParameter("CustomerID");
		if(customerID==null)customerID="0";
		long CustomerID = 0;
		try{
			CustomerID = Long.parseLong(customerID);
		}catch(Exception e){
			CustomerID = 0;
		}
		
		String[] productIDs = request.getParameterValues("ProductCartID");
		for(int i=0;i<productIDs.length;i++){
			
			long ProductID = 0;
			try{
				ProductID = Long.parseLong(productIDs[i]);
			}catch(Exception e){
				ProductID = 0;
			}
			
			String productprice = request.getParameter("ProductPrice"+productIDs[i]);
			if(productprice==null)productprice="0";
			double ProductPrice = 0;
			try{
				ProductPrice = Double.parseDouble(productprice);
			}catch(Exception e){
				ProductPrice = 0;
			}
			
			String productquantity = request.getParameter("ProductQuantity"+productIDs[i]);
			if(productquantity==null)productquantity="0";
			int ProductQuantity = 0;
			try{
				ProductQuantity = Integer.parseInt(productquantity);
			}catch(Exception e){
				ProductQuantity = 0;
			}
			
			String netprice = request.getParameter("NetPrice"+productIDs[i]);
			if(netprice==null)netprice="0";
			double NetPrice = 0;
			try{
				NetPrice = Double.parseDouble(netprice);
			}catch(Exception e){
				NetPrice = 0;
			}
			BillNetPrice += NetPrice;
			
			String discount = request.getParameter("Discount"+productIDs[i]);
			if(discount==null)discount="0";
			double Discount = 0;
			try{
				Discount = Double.parseDouble(discount);
			}catch(Exception e){
				Discount = 0;
			}
			BillDiscount += Discount;
			
			String totalprice = request.getParameter("TotalPrice"+productIDs[i]);
			if(totalprice==null)totalprice="0";
			double TotalPrice = 0;
			try{
				TotalPrice = Double.parseDouble(totalprice);
			}catch(Exception e){
				TotalPrice = 0;
			}
			
			Billline bl = new Billline(0, EnterprisedID, Bill_ID, ProductID, ProductQuantity, 
				ProductPrice,NetPrice, Discount, TotalPrice, "", "");
			billineslist.add(bl);
		}
		
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		double OrderTotal = BillNetPrice - BillDiscount;
		String BillName = "Sale-"+CustomerID+"-"+dateFormat.format(date);
		Bill bill = new Bill(Bill_ID, EnterprisedID, CustomerID,BillName, BillNetPrice, BillDiscount, OrderTotal, "", "");
		result = orderdao.createOrder(bill, billineslist);
		
		if(result == 1)
			Message = "Order successfully created";
		else
			Message = "Order not successfully created";
	
		billslist = orderdao.getBills(EnterprisedID);
		
		ordermodelview.addObject("Message",Message);
		ordermodelview.addObject("orderslist",billslist);
		ordermodelview.setViewName("/order/OrderList");
		return ordermodelview;
    }
	
	@RequestMapping(value="/order/editorder",method=RequestMethod.GET)  
    public ModelAndView editorder(@RequestParam(value="EnterprisedID",defaultValue="") long EnterprisedID,
    		@RequestParam(value="BillID",defaultValue="") long BillID){
		
		
		ModelAndView ordermodelview = new ModelAndView();
		Bill bill = new Bill();
		ArrayList<Billline> billineslist = new ArrayList<Billline>();
		
		bill = orderdao.getBill(BillID, EnterprisedID);
		billineslist = orderdao.getBilllines(BillID, EnterprisedID);
		
		ordermodelview.addObject("Bill",bill);
		ordermodelview.addObject("billineslist",billineslist);
		ordermodelview.setViewName("/order/EditOrder");
		return ordermodelview;
	}
	
	@RequestMapping(value="/order/updateorder",method=RequestMethod.POST)  
    public ModelAndView updateorder(HttpServletRequest request){
		
		ModelAndView ordermodelview = new ModelAndView();
		ArrayList<Bill> billslist = new ArrayList<Bill>();
		ArrayList<Billline> billineslist = new ArrayList<Billline>();
		int result = 0;
		String Message="";
		
		double BillNetPrice = 0;
		double BillDiscount = 0;
		
		String enterprisedID = request.getParameter("EnterprisedID");
		if(enterprisedID==null)enterprisedID="0";
		long EnterprisedID = 0;
		try{
			EnterprisedID = Long.parseLong(enterprisedID);
		}catch(Exception e){
			EnterprisedID = 0;
		}
		
		String customerID = request.getParameter("CustomerID");
		if(customerID==null)customerID="0";
		long CustomerID = 0;
		try{
			CustomerID = Long.parseLong(customerID);
		}catch(Exception e){
			CustomerID = 0;
		}
		
		String billID = request.getParameter("BillID");
		if(billID==null)billID="0";
		long BillID = 0;
		try{
			BillID = Long.parseLong(billID);
		}catch(Exception e){
			BillID = 0;
		}
		
		String BillName = request.getParameter("BillName");
		if(BillName==null)BillName="";
		
		String[] productIDs = request.getParameterValues("ProductCartID");
		for(int i=0;i<productIDs.length;i++){
			
			long ProductID = 0;
			try{
				ProductID = Long.parseLong(productIDs[i]);
			}catch(Exception e){
				ProductID = 0;
			}
			
			String billlineid = request.getParameter("BilllineID"+i);
			if(billlineid==null)billlineid="0";
			long BilllineID = 0;
			try{
				BilllineID = Long.parseLong(billlineid);
			}catch(Exception e){
				BilllineID = 0;
			}
			
			System.out.println("BilllineID"+BilllineID);
			
			String productprice = request.getParameter("ProductPrice"+productIDs[i]);
			if(productprice==null)productprice="0";
			double ProductPrice = 0;
			try{
				ProductPrice = Double.parseDouble(productprice);
			}catch(Exception e){
				ProductPrice = 0;
			}
			System.out.println("ProductPrice"+ProductPrice);
			
			String productquantity = request.getParameter("ProductQuantity"+productIDs[i]);
			if(productquantity==null)productquantity="0";
			int ProductQuantity = 0;
			try{
				ProductQuantity = Integer.parseInt(productquantity);
			}catch(Exception e){
				ProductQuantity = 0;
			}
			System.out.println("ProductQuantity"+ProductQuantity);
			
			String netprice = request.getParameter("NetPrice"+productIDs[i]);
			if(netprice==null)netprice="0";
			double NetPrice = 0;
			try{
				NetPrice = Double.parseDouble(netprice);
			}catch(Exception e){
				NetPrice = 0;
			}
			System.out.println("NetPrice"+NetPrice);
			BillNetPrice += NetPrice;
			
			String discount = request.getParameter("Discount"+productIDs[i]);
			if(discount==null)discount="0";
			double Discount = 0;
			try{
				Discount = Double.parseDouble(discount);
			}catch(Exception e){
				Discount = 0;
			}
			System.out.println("Discount"+Discount);
			BillDiscount += Discount;
			
			String totalprice = request.getParameter("TotalPrice"+productIDs[i]);
			if(totalprice==null)totalprice="0";
			double TotalPrice = 0;
			try{
				TotalPrice = Double.parseDouble(totalprice);
			}catch(Exception e){
				TotalPrice = 0;
			}
			System.out.println("TotalPrice"+TotalPrice);
			Billline bl = new Billline(BilllineID, EnterprisedID, BillID, ProductID, ProductQuantity, 
				ProductPrice,NetPrice, Discount, TotalPrice, "", "");
			billineslist.add(bl);
		}
		
		double OrderTotal = BillNetPrice - BillDiscount;
		Bill bill = new Bill(BillID, EnterprisedID, CustomerID,BillName,
				BillNetPrice, BillDiscount, OrderTotal, "", "");
		result = orderdao.updateOrder(bill, billineslist);
		
		if(result == 1)
			Message = "Order successfully updated";
		else
			Message = "Order not successfully updated";
	
		billslist = orderdao.getBills(EnterprisedID);
		
		
		ordermodelview.addObject("Message",Message);
		ordermodelview.addObject("orderslist",billslist);
		ordermodelview.setViewName("/order/OrderList");
		return ordermodelview;
    }
	
	@RequestMapping(value="/order/deleteorder",method=RequestMethod.GET)  
    public ModelAndView deleteorder(@RequestParam(value="EnterprisedID",defaultValue="") long EnterprisedID,
    		@RequestParam(value="BillID",defaultValue="") long BillID){
		
		int result = 0;
		ModelAndView ordermodelview = new ModelAndView();
		String Message = "";
		ArrayList<Bill> billslist = new ArrayList<Bill>();
		
		result = orderdao.deleteOrder(BillID, EnterprisedID);
		
		if(result == 1)
			Message = "Order successfully deleted";
		else
			Message = "Order not successfully deleted";
		
		billslist = orderdao.getBills(EnterprisedID);
		
		ordermodelview.addObject("result",result);
		ordermodelview.addObject("Message",Message);
		ordermodelview.addObject("orderslist",billslist);
		ordermodelview.setViewName("/order/OrderList");
		return ordermodelview;
	}
}
