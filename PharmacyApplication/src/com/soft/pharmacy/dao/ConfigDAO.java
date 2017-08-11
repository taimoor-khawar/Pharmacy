package com.soft.pharmacy.dao;

import java.util.ArrayList;

import com.soft.pharmacy.model.ProductType;
import com.soft.pharmacy.model.Supplier;


public interface ConfigDAO {
	
	
	//Supplier
	int saveSupplier(Supplier supplier);
	int updateSupplier(Supplier supplier);
	int deleteSupplier(long supplierid,long EnterprisedID);
	ArrayList<Supplier> getSuppliers(long EnterprisedID);
	Supplier getSupplier(long supplierid,long EnterprisedID);
	int importsupplierdata(ArrayList<Supplier> SupplierList);
	Supplier getBySupplierCode(String supplierCode, long enterprisedID);
	
	
	//ProductType
	int saveProductType(ProductType producttype);
	int updateProductType(ProductType producttype);
	int deleteProductType(long ProductTypeID,long EnterprisedID);
	ArrayList<ProductType> getProductTypes(long EnterprisedID);
	ProductType getProductType(long ProductTypeID,long EnterprisedID);
	int importProductTypedata(ArrayList<ProductType> ProductTypeList);
	

}
