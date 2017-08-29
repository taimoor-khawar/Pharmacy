package com.soft.pharamacy.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.soft.pharmacy.dao.ConfigDAO;
import com.soft.pharmacy.dao.ProductDAO;
import com.soft.pharmacy.model.Product;
import com.soft.pharmacy.model.ProductType;
import com.soft.pharmacy.model.Supplier;
import com.soft.pharmacy.model.SysUserSession;

@Controller
public class ProductController {
	
	@Autowired
	ProductDAO prodao;
	
	@Autowired  
    ConfigDAO configdao;
	
	@RequestMapping(value="/product/list",method=RequestMethod.GET)  
    public ModelAndView productlist(@RequestParam(value="EnterprisedID",defaultValue="") 
    long EnterprisedID){ 
		 
		ModelAndView productmodelandview = new ModelAndView();
		ArrayList<Product> productslist = prodao.getProducts(EnterprisedID);
		productmodelandview.addObject("productslist", productslist);
		productmodelandview.setViewName("product/ProductList");
		return productmodelandview;
    }
	
	@RequestMapping(value="/product/addproduct",method=RequestMethod.GET)  
    public ModelAndView newproduct(@RequestParam(value="EnterprisedID",defaultValue="")long EnterprisedID){ 
		
		ModelAndView productmodelandview = new ModelAndView();
		ArrayList<Supplier> supplierslist = configdao.getSuppliers(EnterprisedID);
		ArrayList<ProductType> producttypeslist = configdao.getProductTypes(EnterprisedID);
		productmodelandview.addObject("supplierslist", supplierslist);
		productmodelandview.addObject("producttypeslist", producttypeslist);
		productmodelandview.addObject("command",new Product());
		productmodelandview.setViewName("product/AddProduct");
		return productmodelandview;
    }
	
	@RequestMapping(value="/product/addproduct",method=RequestMethod.POST)  
    public ModelAndView addproduct(@ModelAttribute("product") Product product){ 
		
		int result = 0;
		String Message="";
		ModelAndView productmodelandview = new ModelAndView();
		ArrayList<Product> ProductList = new ArrayList<Product>();
		
		if(product != null)
			result = prodao.saveproduct(product);
		
		
		if(result ==1)
			Message =  "Product Added Successfully";
		else if(result==0)
			Message =  "Product not Added Successfully";
		
		ProductList = prodao.getProducts(product.getEnterprisedID());
		
		productmodelandview.addObject("Message", Message);
		productmodelandview.addObject("productslist", ProductList);
		productmodelandview.addObject("result", result);
		productmodelandview.setViewName("product/ProductList");
		return productmodelandview;
    }
	
	@RequestMapping(value="/product/editproduct",method=RequestMethod.GET)  
    public ModelAndView editproduct(@RequestParam(value="EnterprisedID",defaultValue="0") long EnterprisedID,
    		@RequestParam(value="ProductID",defaultValue="0") long ProductID){ 
		
		ModelAndView productmodelandview = new ModelAndView();
		ArrayList<Supplier> supplierslist = configdao.getSuppliers(EnterprisedID);
		ArrayList<ProductType> producttypeslist = configdao.getProductTypes(EnterprisedID);
		Product product = prodao.getProduct(ProductID, EnterprisedID);
		productmodelandview.addObject("supplierslist", supplierslist);
		productmodelandview.addObject("producttypeslist", producttypeslist);
		productmodelandview.addObject("product", product);
		productmodelandview.setViewName("/product/EditProduct");
		return productmodelandview;
    }
	
	@RequestMapping(value="/product/editproduct",method=RequestMethod.POST)  
    public ModelAndView updateproduct(@ModelAttribute("product") Product product){ 
		
		int result = 0;
		String Message= "";
		ModelAndView productmodelandview = new ModelAndView();
		
		if(product != null)
			result = prodao.updateproduct(product);
		
		if(result ==1)
			Message =  "Product Successfully Updated";
		else if(result==0)
			Message =  "Product Successfully not Updated";
		
		ArrayList<Product> ProductList = prodao.getProducts(product.getEnterprisedID());
		
		productmodelandview.addObject("Message", Message);
		productmodelandview.addObject("productslist", ProductList);
		productmodelandview.addObject("result", result);
		productmodelandview.setViewName("product/ProductList");
		return productmodelandview;
    }
	
	
	@RequestMapping(value="/product/deleteproduct",method=RequestMethod.GET)  
    public ModelAndView deletesupplier(@RequestParam(value="EnterprisedID",defaultValue="0") long EnterprisedID,
    		@RequestParam(value="ProductID",defaultValue="0") long ProductID){ 
		
		int result = 0;
		String Message= "";
		ModelAndView productmodelandview = new ModelAndView();
		
		if(EnterprisedID != 0)
			result = prodao.deleteproduct(ProductID,EnterprisedID);
		
		if(result ==1)
			Message =  "Product Successfully Deleted";
		else if(result==0)
			Message =  "Product Successfully not Deleted";
		
		ArrayList<Product> ProductList = prodao.getProducts(EnterprisedID);
		
		productmodelandview.addObject("Message", Message);
		productmodelandview.addObject("productslist", ProductList);
		productmodelandview.addObject("result", result);
		productmodelandview.setViewName("product/ProductList");
		return productmodelandview;
    }
	
	@RequestMapping(value="/product/viewproduct",method=RequestMethod.GET)  
    public ModelAndView viewsupplier(@RequestParam(value="EnterprisedID",defaultValue="0") long EnterprisedID,
    		@RequestParam(value="ProductID",defaultValue="0") long ProductID){ 
		
		Product product = prodao.getProduct(ProductID, EnterprisedID);
		return new ModelAndView("product/ViewProduct","product",product);
    }
	
	@RequestMapping(value="/product/productcodecheck",method=RequestMethod.GET)
	public @ResponseBody String processAJAXRequest(
			@RequestParam("productCode") String ProductCode,
			HttpServletRequest request
			) {
		
		HttpSession session = request
				.getSession(true);
		SysUserSession uss = (SysUserSession)session.getAttribute(session.getId());
		
		Product pr = prodao.getByproductCode(ProductCode,uss.getSysuser().getsysUserID());
		String response = "";
		if(pr!=null){
			if(pr.getProductCode().length() > 0)
				response = "Product Code Already Exist";
			else
				response = "Product Code Available";
		}else{
			response = "Product Code Available";
		}
		// Process the request
		// Prepare the response string
		System.out.println(response);
		return response;
	}
	
	@RequestMapping(value="/product/upload",method=RequestMethod.POST)  
    public ModelAndView importsupplierdata(HttpServletRequest request,
    		@RequestParam("file") MultipartFile File,
    		@RequestParam(value="EnterprisedID",defaultValue="") long EnterprisedID) throws IOException{
		
		int result = 0;
		String Message="";
		ModelAndView productmodelandview = new ModelAndView();
		
		ArrayList<Product> ProductList = new ArrayList<Product>();
		ArrayList<Product> ProductListmDB = new ArrayList<Product>();
		try {
			int i = 1;
			XSSFWorkbook workbook = new XSSFWorkbook(File.getInputStream());
			XSSFSheet worksheet = workbook.getSheetAt(0);
			while (i <= worksheet.getLastRowNum()) {
				XSSFRow row = worksheet.getRow(i++);
				
				String ProductType = row.getCell(2).getStringCellValue();
				long ProductTypeID = prodao.getProductTypeID(ProductType, EnterprisedID);
				
				String SupplierCode = (String)row.getCell(3).getStringCellValue();
				long SupplierID = prodao.getSupplierID(SupplierCode, EnterprisedID);
				
				String sellingprice = row.getCell(4).getStringCellValue();
				sellingprice = sellingprice.replaceAll("[^\\d.]", "");
				double SellingPrice = Double.parseDouble(sellingprice);
				
				String buyingprice = row.getCell(6).getStringCellValue();
				buyingprice = buyingprice.replaceAll("[^\\d.]", "");
				double BuyingPrice = Double.parseDouble(buyingprice);
				
				if(SupplierID > 0 && ProductTypeID > 0){
					Product pr = new Product(0,EnterprisedID,(String)row.getCell(0).getStringCellValue(),
							(String)row.getCell(1).getStringCellValue(),ProductTypeID,SupplierID,
							(int)row.getCell(5).getNumericCellValue(),SellingPrice,BuyingPrice,"","");
					ProductList.add(pr);
					worksheet.removeRow(row);
				}
			}			
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		result = prodao.importproductdata(ProductList);
		
		productmodelandview.addObject("result", result);
		if(result ==1)
			Message =  "Products Data imported Successfully";
		else if(result==0)
			Message =  "Products Data not imported Successfully";
		
		ProductListmDB = prodao.getProducts(EnterprisedID);
		
		productmodelandview.addObject("Message", Message);
		productmodelandview.addObject("productslist", ProductListmDB);
		productmodelandview.setViewName("product/ProductList");
		return productmodelandview;
    }
	
	@RequestMapping(value="/product/getproduct",method=RequestMethod.GET,
			headers="Accept=*/*")
	@ResponseBody
	public String getproducttype(
			@RequestParam(value="ProductID",defaultValue="0") long ProductID,
			HttpServletRequest request) throws JSONException {
		
		JSONObject jo = new JSONObject();
		
		HttpSession session = request
				.getSession(true);
		SysUserSession uss = (SysUserSession)session.getAttribute(session.getId());
		
		Product pr = prodao.getProduct(ProductID,uss.getSysuser().getsysUserID());
	
		jo.append("ProductID", pr.getProductID());
		jo.append("ProductName", pr.getProductName());
		jo.append("SellingPrice", pr.getSellingPrice());
		jo.append("BuyingPrice", pr.getBuyingPrice());
		jo.append("ProductQuantity", pr.getProductQuantity());
		
		
	    return jo.toString();
	}

}
