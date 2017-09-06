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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.soft.pharmacy.dao.CustomerDAO;
import com.soft.pharmacy.dao.ProductDAO;
import com.soft.pharmacy.dao.ReportDAO;
import com.soft.pharmacy.excel.CreatePDF;
import com.soft.pharmacy.model.Bill;
import com.soft.pharmacy.model.Customer;
import com.soft.pharmacy.model.Product;
import com.soft.pharmacy.model.Purchase;


@Controller
public class ReportController {
	
	@Autowired
	ReportDAO reportdao;
	
	@Autowired
	CustomerDAO cusdao;
	
	@Autowired
	ProductDAO prdao;
	
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
	
	@RequestMapping(value="/report/purchasereport",method=RequestMethod.GET)  
    public ModelAndView purchase(@RequestParam(value="EnterprisedID",defaultValue="") long EnterprisedID
    		){
		ModelAndView reportmodelandview = new ModelAndView();
		ArrayList<Product> productslist = prdao.getProducts(EnterprisedID);
		reportmodelandview.addObject("productslist", productslist);
		reportmodelandview.setViewName("reports/PurchaseReport");
		return reportmodelandview;
    }
	
	@RequestMapping(value="/report/purchasesreport",method=RequestMethod.POST
			)  
    public ModelAndView purchasereport(@RequestParam(value="EnterprisedID",defaultValue="") long EnterprisedID,
    		@RequestParam(value="ProductID",defaultValue="") long ProductID,
    		@RequestParam(value="fromdate",defaultValue="") String FromDate,
    		@RequestParam(value="todate",defaultValue="") String ToDate,
    		HttpServletRequest request) throws IOException{
		ModelAndView reportmodelandview = new ModelAndView();
		ArrayList<Product> productslist = prdao.getProducts(EnterprisedID);
		ArrayList<Purchase> purchaseslist = reportdao.getPurchasesByProduct(ProductID, EnterprisedID,FromDate,ToDate);
		
		reportmodelandview.addObject("ProductID", "s"+ProductID);
		reportmodelandview.addObject("FromDate", FromDate);
		reportmodelandview.addObject("ToDate", ToDate);
		
		reportmodelandview.addObject("filename", "OrderCustomerReport");
		reportmodelandview.addObject("purchaseslist", purchaseslist);
		reportmodelandview.addObject("productslist", productslist);
		reportmodelandview.setViewName("reports/PurchaseReport");
		return reportmodelandview;
    }
	
	
	@RequestMapping(value="/report/purchasereportpdf",method=RequestMethod.GET
			)  
    public ModelAndView purchasereportpdf(@RequestParam(value="EnterprisedID",defaultValue="0") long EnterprisedID,
    		@RequestParam(value="ProductID",defaultValue="0") long ProductID,
    		HttpServletRequest request,@RequestParam(value="fromdate",defaultValue="") String FromDate,
    		@RequestParam(value="todate",defaultValue="") String ToDate,
    		HttpServletResponse response
    		) throws IOException{
		
		ModelAndView reportmodelandview = new ModelAndView();
		ArrayList<Product> productslist = prdao.getProducts(EnterprisedID);
		ArrayList<Purchase> purchaseslist = reportdao.getPurchasesByProduct(ProductID, EnterprisedID,FromDate,ToDate);
		Product pr = prdao.getProduct(ProductID, EnterprisedID);
		
		reportmodelandview.addObject("ProductID", "s"+ProductID);
		reportmodelandview.addObject("FromDate", FromDate);
		reportmodelandview.addObject("ToDate", ToDate);
		
		reportmodelandview.addObject("purchaseslist", purchaseslist);
		reportmodelandview.addObject("productslist", productslist);
		reportmodelandview.setViewName("reports/PurchaseReport");
		
		if(purchaseslist.size() > 0){
			 String filepath = "E:/Repository/Pharmacy/PharmacyApplication/pdf/purchase/";
			 String fileName = "Bill-Purchase-"+pr.getProductName()+".pdf";
		     File tempDirectory = new File(filepath);
		     if(!tempDirectory.exists())tempDirectory.mkdirs();
		     String temperotyFilePath = tempDirectory.getAbsolutePath();
		     System.out.println(temperotyFilePath);
		     
		     
			 response.setContentType("application/pdf");
			 response.setHeader("Content-disposition", "attachment; filename="+ fileName);
			 
		     try {
		    	 	response.flushBuffer();
			        CreatePDF.createPDFPurchaseReport(temperotyFilePath+"\\"+fileName,purchaseslist,pr,request);
			        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			        baos = convertPDFToByteArrayOutputStream(temperotyFilePath+"\\"+fileName);
			        OutputStream os = response.getOutputStream();
			        baos.writeTo(os);
			        os.flush();
			    } catch (Exception e1) {
			        e1.printStackTrace();
			    }
		   
		}
		
		return reportmodelandview;
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
	
	@RequestMapping(value="/report/customerreport",method=RequestMethod.GET)  
    public ModelAndView customerreport(@RequestParam(value="EnterprisedID",defaultValue="") long EnterprisedID
    		){
		ModelAndView reportmodelandview = new ModelAndView();
		reportmodelandview.setViewName("reports/CustomerReport");
		return reportmodelandview;
    }
	
	@RequestMapping(value="/report/customerreport",method=RequestMethod.POST
			)  
    public ModelAndView customersreport(@RequestParam(value="EnterprisedID",defaultValue="") long EnterprisedID,
    		@RequestParam(value="fromdate",defaultValue="") String FromDate,
    		@RequestParam(value="todate",defaultValue="") String ToDate,
    		HttpServletRequest request) throws IOException{
		ModelAndView reportmodelandview = new ModelAndView();
		ArrayList<Customer> customerslist = cusdao.getCustomers(EnterprisedID);
		reportmodelandview.addObject("FromDate", FromDate);
		reportmodelandview.addObject("ToDate", ToDate);
		
		reportmodelandview.addObject("customerslist", customerslist);
		reportmodelandview.setViewName("reports/CustomerReport");
		return reportmodelandview;
    }
	
	
	@RequestMapping(value="/report/customerreportpdf",method=RequestMethod.GET
			)  
    public ModelAndView customerreportpdf(@RequestParam(value="EnterprisedID",defaultValue="") long EnterprisedID,
    		@RequestParam(value="fromdate",defaultValue="") String FromDate,
    		@RequestParam(value="todate",defaultValue="") String ToDate,
    		HttpServletRequest request,
    		HttpServletResponse response
    		) throws IOException{
		
		ModelAndView reportmodelandview = new ModelAndView();
		ArrayList<Customer> customerslist = cusdao.getCustomers(EnterprisedID);
		reportmodelandview.addObject("FromDate", FromDate);
		reportmodelandview.addObject("ToDate", ToDate);
		
		reportmodelandview.addObject("customerslist", customerslist);
		reportmodelandview.setViewName("reports/CustomerReport");
		
		if(customerslist.size() > 0){
			 Date date = new Date();
			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			 String filepath = "E:/Repository/Pharmacy/PharmacyApplication/pdf/customer/";
			 String fileName = "CustomerReport-"+dateFormat.format(date)+".pdf";
		     File tempDirectory = new File(filepath);
		     if(!tempDirectory.exists())tempDirectory.mkdirs();
		     String temperotyFilePath = tempDirectory.getAbsolutePath();
		     System.out.println(temperotyFilePath);
		     
		     
			 response.setContentType("application/pdf");
			 response.setHeader("Content-disposition", "attachment; filename="+ fileName);
			 
		     try {
		    	 	response.flushBuffer();
			        CreatePDF.createPDFCustomerReport(temperotyFilePath+"\\"+fileName,customerslist,request);
			        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			        baos = convertPDFToByteArrayOutputStream(temperotyFilePath+"\\"+fileName);
			        OutputStream os = response.getOutputStream();
			        baos.writeTo(os);
			        os.flush();
			    } catch (Exception e1) {
			        e1.printStackTrace();
			    }
		   
		}
		
		return reportmodelandview;
	}

}
