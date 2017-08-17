package com.soft.pharmacy.dao;

import java.util.ArrayList;

import com.soft.pharmacy.model.Product;
import com.soft.pharmacy.model.Supplier;


public interface ProductDAO {

	int saveproduct(Product product);
	int updateproduct(Product product);
	int deleteproduct(long productid,long EnterprisedID);
	ArrayList<Product> getProducts(long EnterprisedID);
	Product getProduct(long productid,long EnterprisedID);
	Product getByproductCode(String productCode, long enterprisedID);
	long getProductTypeID(String TypeName,long EnterprisedID);
	long getSupplierID(String SupplierCode,long EnterprisedID);
	int importproductdata(ArrayList<Product> ProductList);
}
