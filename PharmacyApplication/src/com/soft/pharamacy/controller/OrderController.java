package com.soft.pharamacy.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.soft.pharmacy.dao.ConfigDAO;
import com.soft.pharmacy.dao.CustomerDAO;
import com.soft.pharmacy.dao.OrderDAO;
import com.soft.pharmacy.dao.ProductDAO;
import com.soft.pharmacy.excel.CreatePDF;
import com.soft.pharmacy.model.Bill;
import com.soft.pharmacy.model.Billline;
import com.soft.pharmacy.model.Customer;
import com.soft.pharmacy.model.Product;
import com.soft.pharmacy.model.Purchase;
import com.soft.pharmacy.model.Supplier;


@Controller
public class OrderController {

	@Autowired
	ProductDAO prodao;
	
	@Autowired
	CustomerDAO cusdao;
	
	@Autowired
	OrderDAO orderdao;
	
	@Autowired
	ConfigDAO configdao;
	
	
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
			System.out.println("ProductID="+productIDs[i]);
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
			System.out.println("ProductPrice="+ProductPrice);
			
			String productquantity = request.getParameter("ProductQuantity"+productIDs[i]);
			if(productquantity==null)productquantity="0";
			int ProductQuantity = 0;
			try{
				ProductQuantity = Integer.parseInt(productquantity);
			}catch(Exception e){
				ProductQuantity = 0;
			}
			System.out.println("ProductQuantity="+ProductQuantity);
			
			String netprice = request.getParameter("HNetPrice"+productIDs[i]);
			if(netprice==null)netprice="0";
			double NetPrice = 0;
			try{
				NetPrice = Double.parseDouble(netprice);
			}catch(Exception e){
				NetPrice = 0;
			}
			System.out.println("NetPrice="+NetPrice);
			BillNetPrice += NetPrice;
			
			String discount = request.getParameter("Discount"+productIDs[i]);
			if(discount==null)discount="0";
			double Discount = 0;
			try{
				Discount = Double.parseDouble(discount);
			}catch(Exception e){
				Discount = 0;
			}
			System.out.println("Discount="+Discount);
			BillDiscount += Discount;
			
			String totalprice = request.getParameter("HTotalPrice"+productIDs[i]);
			if(totalprice==null)totalprice="0";
			double TotalPrice = 0;
			try{
				TotalPrice = Double.parseDouble(totalprice);
			}catch(Exception e){
				TotalPrice = 0;
			}
			System.out.println("TotalPrice="+TotalPrice);
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
	
	//--------------------------------------------------Purchase----------------------------
	
	@RequestMapping(value="/order/purhaselist",method=RequestMethod.GET)  
    public ModelAndView purchaselist(@RequestParam(value="EnterprisedID",defaultValue="") 
    					long EnterprisedID){
		ModelAndView purhasemodelandview = new ModelAndView();
		ArrayList<Purchase> purhaseorderlist = orderdao.getPurchaseOrders(EnterprisedID);
		purhasemodelandview.addObject("purhaseorderlist", purhaseorderlist);
		purhasemodelandview.setViewName("order/PurchaseList");
		return purhasemodelandview;
    }
	
	
	@RequestMapping(value="/order/newpurchase",method=RequestMethod.GET)  
    public ModelAndView newpurchase(@RequestParam(value="EnterprisedID",defaultValue="")long EnterprisedID){
		ModelAndView purhasemodelandview = new ModelAndView();
		ArrayList<Product> productslist = prodao.getProducts(EnterprisedID);
		ArrayList<Supplier> supplierslist = configdao.getSuppliers(EnterprisedID);
		purhasemodelandview.addObject("productslist", productslist);
		purhasemodelandview.addObject("supplierslist", supplierslist);
		purhasemodelandview.addObject("command", new Purchase());
		purhasemodelandview.setViewName("order/AddPurchase");
		return purhasemodelandview;
    }
	

	@RequestMapping(value="/order/newpurchase",method=RequestMethod.POST)  
    public ModelAndView addpurchase(@ModelAttribute("purchase") Purchase purchase){
		
		ModelAndView purhasemodelandview = new ModelAndView();
		int result = 0;
		String Message="";
		if(purchase != null)
			result = orderdao.savePurchaseOrder(purchase);
		
		
		if(result ==1)
			Message =  "Product Added Successfully";
		else if(result==0)
			Message =  "Product not Added Successfully";
		
		ArrayList<Purchase> purhaseorderlist = orderdao.getPurchaseOrders(purchase.getEnterprisedID());
		
		
		purhasemodelandview.addObject("Message", Message);
		purhasemodelandview.addObject("result", result);
		purhasemodelandview.addObject("purhaseorderlist", purhaseorderlist);
		purhasemodelandview.setViewName("order/PurchaseList");
		return purhasemodelandview;
    }
	
	
	@RequestMapping(value="/order/editpurchase",method=RequestMethod.GET)  
    public ModelAndView editpurchase(@RequestParam(value="EnterprisedID",defaultValue="")long EnterprisedID,
    		@RequestParam(value="PurchaseID",defaultValue="")long PurchaseID){
		ModelAndView purhasemodelandview = new ModelAndView();
		ArrayList<Product> productslist = prodao.getProducts(EnterprisedID);
		ArrayList<Supplier> supplierslist = configdao.getSuppliers(EnterprisedID);
		Purchase pur = orderdao.getPurchaseOrder(PurchaseID, EnterprisedID);
		purhasemodelandview.addObject("productslist", productslist);
		purhasemodelandview.addObject("supplierslist", supplierslist);
		purhasemodelandview.addObject("purchase", pur);
		purhasemodelandview.setViewName("order/EditPurchase");
		return purhasemodelandview;
    }
	
	
	@RequestMapping(value="/order/editpurchase",method=RequestMethod.POST)  
    public ModelAndView updatepurchase(@ModelAttribute("purchase") Purchase purchase){
		
		ModelAndView purhasemodelandview = new ModelAndView();
		int result = 0;
		String Message="";
		if(purchase != null)
			result = orderdao.updatePurchaseOrder(purchase);
		
		
		if(result ==1)
			Message =  "Product Updated Successfully";
		else if(result==0)
			Message =  "Product not Updated Successfully";
		
		ArrayList<Purchase> purhaseorderlist = orderdao.getPurchaseOrders(purchase.getEnterprisedID());
		
		
		purhasemodelandview.addObject("Message", Message);
		purhasemodelandview.addObject("result", result);
		purhasemodelandview.addObject("purhaseorderlist", purhaseorderlist);
		purhasemodelandview.setViewName("order/PurchaseList");
		return purhasemodelandview;
    }
	
	
	@RequestMapping(value="/order/deletepurchase",method=RequestMethod.GET)  
    public ModelAndView deletepurchase(@RequestParam(value="EnterprisedID",defaultValue="")long EnterprisedID,
    		@RequestParam(value="PurchaseID",defaultValue="")long PurchaseID){
		
		ModelAndView purhasemodelandview = new ModelAndView();
		int result = 0;
		String Message="";
		if(PurchaseID > 0)
			result = orderdao.deletePurchaseOrder(PurchaseID, EnterprisedID);
		
		
		if(result ==1)
			Message =  "Product Deleted Successfully";
		else if(result==0)
			Message =  "Product not Deleted Successfully";
		
		ArrayList<Purchase> purhaseorderlist = orderdao.getPurchaseOrders(EnterprisedID);
		
		
		purhasemodelandview.addObject("Message", Message);
		purhasemodelandview.addObject("result", result);
		purhasemodelandview.addObject("purhaseorderlist", purhaseorderlist);
		purhasemodelandview.setViewName("order/PurchaseList");
		return purhasemodelandview;
    }
	
	@RequestMapping(value="/order/getpurchase",method=RequestMethod.GET,
			headers="Accept=*/*")
	@ResponseBody
	public String getproducttype(
			@RequestParam(value="PurchaseID",defaultValue="0") long PurchaseID,
			@RequestParam(value="EnterprisedID",defaultValue="0") long EnterprisedID
			//HttpServletRequest request
			) throws JSONException {
		
		JSONObject jo = new JSONObject();
		
		/*HttpSession session = request
				.getSession(true);
		SysUserSession uss = (SysUserSession)session.getAttribute(session.getId());*/
		Purchase pur = orderdao.getPurchaseOrder(PurchaseID, EnterprisedID);
	
		jo.append("ProductID", pur.getProductID());
		jo.append("ProductName", pur.getProductName());
		jo.append("SupplierID", pur.getSupplierID());
		jo.append("SupplierName", pur.getSupplierName());
		jo.append("Quantity", pur.getQuantity());
		jo.append("OLDQuantity", pur.getOLDQuantity());
		
		
	    return jo.toString();
	}
	
	@RequestMapping(value="/order/orderpdf",method=RequestMethod.GET
			)  
    public ModelAndView orderlistpdf(@RequestParam(value="EnterprisedID",defaultValue="0") long EnterprisedID,
    		@RequestParam(value="BillID",defaultValue="0") long BillID,
    		HttpServletRequest request,HttpServletResponse response
    		) throws IOException{
		
		ModelAndView ordermodelview = new ModelAndView();
		Bill bill = orderdao.getBill(BillID, EnterprisedID);
		ArrayList<Billline> billlines = orderdao.getBilllines(BillID, EnterprisedID);
		
		ArrayList<Bill> billslist = orderdao.getBills(EnterprisedID);
		
		ordermodelview.addObject("orderslist",billslist);
		ordermodelview.setViewName("/order/OrderList");
		
		if(billlines.size() > 0 && bill != null){
			 String filepath = "E:/Repository/Pharmacy/PharmacyApplication/pdf/";
			 String fileName = "Bill-Invoice-"+bill.getCustomerName()+".pdf";
		     File tempDirectory = new File(filepath);
		     if(!tempDirectory.exists())tempDirectory.mkdir();
		     String temperotyFilePath = tempDirectory.getAbsolutePath();
		     System.out.println(temperotyFilePath);
		     
		     
			 response.setContentType("application/pdf");
			 response.setHeader("Content-disposition", "attachment; filename="+ fileName);
			 
		     try {
		    	 	response.flushBuffer();
			        CreatePDF.createPDFBill(temperotyFilePath+"\\"+fileName,billlines,bill,request);
			        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			        baos = convertPDFToByteArrayOutputStream(temperotyFilePath+"\\"+fileName);
			        OutputStream os = response.getOutputStream();
			        baos.writeTo(os);
			        os.flush();
			    } catch (Exception e1) {
			        e1.printStackTrace();
			    }
		   
		}
    	return ordermodelview;
	}
	
	private ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName) {

		InputStream inputStream = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {

			inputStream = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			baos = new ByteArrayOutputStream();

			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return baos;
	}
}
