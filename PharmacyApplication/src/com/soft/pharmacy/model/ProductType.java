package com.soft.pharmacy.model;

public class ProductType {
	
	long ProductTypeID;
	long EnterprisedID;
	String ProductTypeName;
	String InsertionDate;
	String ModifyDate;
	
	public ProductType(){
		this.ProductTypeID = 0;
		this.EnterprisedID = 0;
		this.ProductTypeName = "";
		this.InsertionDate= "";
		this.ModifyDate= "";
	}
	
	public String getInsertionDate() {
		return InsertionDate;
	}

	public void setInsertionDate(String insertionDate) {
		if(insertionDate==null)insertionDate="";
		InsertionDate = insertionDate;
	}

	public String getModifyDate() {
		return ModifyDate;
	}

	public void setModifyDate(String modifyDate) {
		if(modifyDate==null)modifyDate="";
		ModifyDate = modifyDate;
	}

	public ProductType(long ProductTypeID,long EnterprisedID,String ProductTypeName,String InsertionDate,
	String ModifyDate){
		this.ProductTypeID = ProductTypeID;
		this.EnterprisedID = EnterprisedID;
		
		if(ProductTypeName==null)ProductTypeName="";
		this.ProductTypeName = ProductTypeName;
		
		if(InsertionDate==null)InsertionDate="";
		this.InsertionDate= InsertionDate;
		
		if(ModifyDate==null)ModifyDate="";
		this.ModifyDate= ModifyDate;
	}

	public long getProductTypeID() {
		return ProductTypeID;
	}

	public void setProductTypeID(long productTypeID) {
		ProductTypeID = productTypeID;
	}

	public String getProductTypeName() {
		return ProductTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		if(productTypeName==null)productTypeName="";
		ProductTypeName = productTypeName;
	}

	public long getEnterprisedID() {
		return EnterprisedID;
	}

	public void setEnterprisedID(long enterprisedID) {
		EnterprisedID = enterprisedID;
	}
	
	

}
