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
import com.soft.pharmacy.model.ProductType;
import com.soft.pharmacy.model.Supplier;
import com.soft.pharmacy.model.SysUserSession;


@Controller
@RequestMapping("/config")
public class ConfigController {

	@Autowired  
    ConfigDAO configdao;
	
	//Supplier
	@RequestMapping(value="/supplier/codecheck",method=RequestMethod.GET)
	public @ResponseBody String processAJAXRequest(
			@RequestParam("firstname") String SupplierCode,
			HttpServletRequest request
			) {
		
		HttpSession session = request
				.getSession(true);
		SysUserSession uss = (SysUserSession)session.getAttribute(session.getId());
		
		Supplier sup = configdao.getBySupplierCode(SupplierCode,uss.getSysuser().getsysUserID());
		String response = "";
		if(sup!=null){
			if(sup.getCode().length() > 0)
				response = "Supplier Code Already Exist";
			else
				response = "Supplier Code Available";
		}else{
			response = "Supplier Code Available";
		}
		// Process the request
		// Prepare the response string
		System.out.println(response);
		return response;
	}
	
	@RequestMapping(value="/supplier/listsupplier",method=RequestMethod.GET)  
    public ModelAndView supplierlist(@RequestParam(value="EnterprisedID",defaultValue="") long EnterprisedID){
		ModelAndView suppliermodelandview = new ModelAndView();
		ArrayList<Supplier> supplierslist = configdao.getSuppliers(EnterprisedID);
		suppliermodelandview.addObject("supplierslist", supplierslist);
		suppliermodelandview.setViewName("config/SupplierList");
		return suppliermodelandview;
    }
	
	@RequestMapping(value="/supplier/addsupplier",method=RequestMethod.GET)  
    public ModelAndView newsupplier(){ 
		 
		return new ModelAndView("config/AddSupplier","command",new Supplier());
    }
	
	@RequestMapping(value="/supplier/addsupplier",method=RequestMethod.POST)  
    public ModelAndView addsupplier(@ModelAttribute("supplier") Supplier supplier){ 
		
		int result = 0;
		String Message="";
		ModelAndView suppliermodelview = new ModelAndView();
		ArrayList<Supplier> SupplierList = new ArrayList<Supplier>();
		
		if(supplier != null)
			result = configdao.saveSupplier(supplier);
		
		
		if(result ==1)
			Message =  "Supplier Added Successfully";
		else if(result==0)
			Message =  "Supplier Data not Added Successfully";
		
		SupplierList = configdao.getSuppliers(supplier.getEnterpriseID());
		
		suppliermodelview.addObject("Message", Message);
		suppliermodelview.addObject("supplierslist", SupplierList);
		suppliermodelview.addObject("result", result);
		suppliermodelview.setViewName("config/SupplierList");
		
		return suppliermodelview;
    }
	
	@RequestMapping(value="/supplier/upload",method=RequestMethod.POST)  
    public ModelAndView importsupplierdata(HttpServletRequest request,
    		@RequestParam("file") MultipartFile File,
    		@RequestParam(value="EnterprisedID",defaultValue="") long EnterprisedID) throws IOException{
		
		int result = 0;
		String Message="";
		ModelAndView suppliermodelview = new ModelAndView();
		
		ArrayList<Supplier> SupplierList = new ArrayList<Supplier>();
		ArrayList<Supplier> SupplierListFromDB = new ArrayList<Supplier>();
		try {
			int i = 1;
			XSSFWorkbook workbook = new XSSFWorkbook(File.getInputStream());
			XSSFSheet worksheet = workbook.getSheetAt(0);
			while (i <= worksheet.getLastRowNum()) {
				XSSFRow row = worksheet.getRow(i++);
				
				String phonenumber = (String)row.getCell(4).getStringCellValue();
				phonenumber = phonenumber.replaceAll("[^\\d.]", "");
				
				String zipcode = (String)row.getCell(8).getStringCellValue();
				zipcode = zipcode.replaceAll("[^\\d.]", "");
				
				Supplier sup = new Supplier(0, EnterprisedID, row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue(),
						row.getCell(3).getStringCellValue(),phonenumber , 
						row.getCell(5).getStringCellValue(), row.getCell(6).getStringCellValue(), 
						row.getCell(7).getStringCellValue(), zipcode, 
						row.getCell(9).getStringCellValue(), row.getCell(2).getStringCellValue(), 
						"", "");
				SupplierList.add(sup);
			}			
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		result = configdao.importsupplierdata(SupplierList);
		
		suppliermodelview.addObject("result", result);
		if(result ==1)
			Message =  "Supplier Data imported Successfully";
		else if(result==0)
			Message =  "Supplier Data not imported Successfully";
		
		SupplierListFromDB = configdao.getSuppliers(EnterprisedID);
		
		suppliermodelview.addObject("Message", Message);
		suppliermodelview.addObject("supplierslist", SupplierListFromDB);
		suppliermodelview.setViewName("config/SupplierList");
		
		return suppliermodelview;
    }
	
	@RequestMapping(value="/supplier/editsupplier",method=RequestMethod.GET)  
    public ModelAndView editsupplier(@RequestParam(value="EnterprisedID",defaultValue="0") long EnterprisedID,
    		@RequestParam(value="SupplierID",defaultValue="0") long SupplierID){ 
		
		Supplier supplier = configdao.getSupplier(SupplierID, EnterprisedID);
		return new ModelAndView("config/EditSupplier","supplier",supplier);
    }
	
	@RequestMapping(value="/supplier/editsupplier",method=RequestMethod.POST)  
    public ModelAndView updatesupplier(@ModelAttribute("supplier") Supplier supplier){ 
		
		int result = 0;
		String Message= "";
		ModelAndView suppliermodelview = new ModelAndView();
		
		if(supplier != null)
			result = configdao.updateSupplier(supplier);
		
		if(result ==1)
			Message =  "Supplier Successfully Updated";
		else if(result==0)
			Message =  "Supplier Successfully not Updated";
		
		ArrayList<Supplier> supplierlist = configdao.getSuppliers(supplier.getEnterpriseID());
		
		suppliermodelview.addObject("Message", Message);
		suppliermodelview.addObject("result", result);
		suppliermodelview.addObject("supplierslist", supplierlist);
		suppliermodelview.setViewName("config/SupplierList");
		
		return suppliermodelview;
    }
	
	
	@RequestMapping(value="/supplier/deletesupplier",method=RequestMethod.GET)  
    public ModelAndView deletesupplier(@RequestParam(value="EnterprisedID",defaultValue="0") long EnterprisedID,
    		@RequestParam(value="SupplierID",defaultValue="0") long SupplierID){ 
		
		int result = 0;
		String Message= "";
		ModelAndView suppliermodelview = new ModelAndView();
		
		if(EnterprisedID != 0)
			result = configdao.deleteSupplier(SupplierID,EnterprisedID);
		
		if(result ==1)
			Message =  "Supplier Successfully Deleted";
		else if(result==0)
			Message =  "Supplier Successfully not Deleted";
		
		ArrayList<Supplier> supplierlist = configdao.getSuppliers(EnterprisedID);
		
		suppliermodelview.addObject("Message", Message);
		suppliermodelview.addObject("result", result);
		suppliermodelview.addObject("supplierslist", supplierlist);
		suppliermodelview.setViewName("config/SupplierList");
		
		return suppliermodelview;
    }
	
	@RequestMapping(value="/supplier/viewsupplier",method=RequestMethod.GET)  
    public ModelAndView viewsupplier(@RequestParam(value="EnterprisedID",defaultValue="0") long EnterprisedID,
    		@RequestParam(value="SupplierID",defaultValue="0") long SupplierID){ 
		
		Supplier supplier = configdao.getSupplier(SupplierID, EnterprisedID);
		return new ModelAndView("config/ViewSupplier","supplier",supplier);
    }
	
	
	//ProductType
	@RequestMapping(value="/producttype/listproducttype",method=RequestMethod.GET)  
    public ModelAndView producttypelist(@RequestParam(value="EnterprisedID",defaultValue="") long EnterprisedID){
		ModelAndView producttypemodelandview = new ModelAndView();
		ArrayList<ProductType> producttypeslist = configdao.getProductTypes(EnterprisedID);
		producttypemodelandview.addObject("producttypeslist", producttypeslist);
		producttypemodelandview.setViewName("config/ProductTypeList");
		return producttypemodelandview;
    }
	
	@RequestMapping(value="/producttype/addproducttype",method=RequestMethod.GET)  
    public ModelAndView newproducttype(){ 
		 
		return new ModelAndView("config/AddProductType","command",new ProductType());
    }
	
	@RequestMapping(value="/producttype/addproducttype",method=RequestMethod.POST)  
    public ModelAndView addproducttype(@ModelAttribute("productype") ProductType productype){ 
		
		int result = 0;
		String Message="";
		ModelAndView producttypemodelview = new ModelAndView();
		ArrayList<ProductType> ProductTypeList = new ArrayList<ProductType>();
		
		if(productype != null)
			result = configdao.saveProductType(productype);
		
		
		if(result ==1)
			Message =  "Product Type Added Successfully";
		else if(result==0)
			Message =  "Product Type not Added Successfully";
		
		ProductTypeList = configdao.getProductTypes(productype.getEnterprisedID());
		
		producttypemodelview.addObject("Message", Message);
		producttypemodelview.addObject("producttypeslist", ProductTypeList);
		producttypemodelview.addObject("result", result);
		producttypemodelview.setViewName("config/ProductTypeList");
		
		return producttypemodelview;
    }
	
	@RequestMapping(value="/producttype/upload",method=RequestMethod.POST)  
    public ModelAndView importproducttypedata(HttpServletRequest request,
    		@RequestParam("file") MultipartFile File,
    		@RequestParam(value="EnterprisedID",defaultValue="") long EnterprisedID) throws IOException{
		
		int result = 0;
		String Message="";
		ModelAndView producttypemodelview = new ModelAndView();
		
		ArrayList<ProductType> ProductTypeList = new ArrayList<ProductType>();
		ArrayList<ProductType> ProductTypeFromDB = new ArrayList<ProductType>();
		try {
			int i = 1;
			XSSFWorkbook workbook = new XSSFWorkbook(File.getInputStream());
			XSSFSheet worksheet = workbook.getSheetAt(0);
			while (i <= worksheet.getLastRowNum()) {
				XSSFRow row = worksheet.getRow(i++);
				
				ProductType sup = new ProductType(0, EnterprisedID,row.getCell(0).getStringCellValue(), 
						"", "");
				ProductTypeList.add(sup);
			}			
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		result = configdao.importProductTypedata(ProductTypeList);
		
		producttypemodelview.addObject("result", result);
		if(result ==1)
			Message =  "Product Type Data imported Successfully";
		else if(result==0)
			Message =  "Product Type Data not imported Successfully";
		
		ProductTypeFromDB = configdao.getProductTypes(EnterprisedID);
		
		producttypemodelview.addObject("Message", Message);
		producttypemodelview.addObject("producttypeslist", ProductTypeFromDB);
		producttypemodelview.setViewName("config/ProductTypeList");
		
		return producttypemodelview;
    }
	
	@RequestMapping(value="/producttype/editproducttype",method=RequestMethod.GET)  
    public ModelAndView editproducttype(@RequestParam(value="EnterprisedID",defaultValue="0") long EnterprisedID,
    		@RequestParam(value="ProductTypeID",defaultValue="0") long ProductTypeID){ 
		
		ProductType pt = configdao.getProductType(ProductTypeID, EnterprisedID);
		return new ModelAndView("config/EditProductType","producttype",pt);
    }
	
	@RequestMapping(value="/producttype/editproducttype",method=RequestMethod.POST)  
    public ModelAndView updateproducttype(@ModelAttribute("productype") ProductType productype){ 
		
		int result = 0;
		String Message= "";
		ModelAndView producttypemodelview = new ModelAndView();
		
		if(productype != null)
			result = configdao.updateProductType(productype);
		
		if(result ==1)
			Message =  "Product Type Successfully Updated";
		else if(result==0)
			Message =  "Product Type Successfully not Updated";
		
		ArrayList<ProductType> producttypelist = configdao.getProductTypes(productype.getEnterprisedID());
		
		producttypemodelview.addObject("Message", Message);
		producttypemodelview.addObject("result", result);
		producttypemodelview.addObject("producttypeslist", producttypelist);
		producttypemodelview.setViewName("config/ProductTypeList");
		
		return producttypemodelview;
    }
	
	
	@RequestMapping(value="/producttype/deleteproducttype",method=RequestMethod.GET)  
    public ModelAndView deleteproducttype(@RequestParam(value="EnterprisedID",defaultValue="0") long EnterprisedID,
    		@RequestParam(value="ProductTypeID",defaultValue="0") long ProductTypeID){ 
		
		int result = 0;
		String Message= "";
		ModelAndView producttypemodelview = new ModelAndView();
		
		if(EnterprisedID != 0)
			result = configdao.deleteProductType(ProductTypeID,EnterprisedID);
		
		if(result ==1)
			Message =  "Product Type Successfully Deleted";
		else if(result==0)
			Message =  "Product Type Successfully not Deleted";
		
		ArrayList<Supplier> supplierlist = configdao.getSuppliers(EnterprisedID);
		
		producttypemodelview.addObject("Message", Message);
		producttypemodelview.addObject("result", result);
		producttypemodelview.addObject("supplierslist", supplierlist);
		producttypemodelview.setViewName("config/ProductTypeList");
		
		return producttypemodelview;
    }
	
	@RequestMapping(value="/producttype/viewproducttype",method=RequestMethod.GET)  
    public ModelAndView viewproducttype(@RequestParam(value="EnterprisedID",defaultValue="0") long EnterprisedID,
    		@RequestParam(value="ProductTypeID",defaultValue="0") long ProductTypeID){ 
		
		ProductType pt = configdao.getProductType(ProductTypeID, EnterprisedID);
		return new ModelAndView("config/ViewProductType","producttype",pt);
    }
	
}
