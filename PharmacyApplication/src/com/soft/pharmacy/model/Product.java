package com.soft.pharmacy.model;

public class Product {
	
	long ProductID;
	long EnterprisedID;
	String ProductName;
	String ProductCode;
	long ProductTypeID;
	long SupplierID;
	double SupplyPrice;
	int ProductQuantity;
	double ProductMarkUp;
	double RetailPrice;
	String InsertionDate;
	String ModificationDate;
	
	String SupplierName;
	String ProducTypeName;
	
	public Product(){
		
		this.ProductID=0;
		this.EnterprisedID=0;
		this.ProductName="";
		this.ProductCode = "";
		this.ProductTypeID=0;
		this.SupplierID=0;
		this.SupplyPrice=0.0;
		this.ProductQuantity=0;
		this.ProductMarkUp=0;
		this.RetailPrice=0.0;
		this.InsertionDate="";
		this.ModificationDate="";
		this.SupplierName="";
		this.ProducTypeName="";
		
	}
	
	public Product(long ProductID,long EnterprisedID,String ProductName,String ProductCode,long ProductTypeID,long SupplierID,
				   double SupplyPrice,int ProductQuantity,double ProductMarkUp,double RetailPrice,
				   String InsertionDate,String ModificationDate){
		
		this.ProductID=ProductID;
		this.EnterprisedID = EnterprisedID;
		
		if(ProductName==null)ProductName="";
		this.ProductName=ProductName;
		
		if(ProductCode==null)ProductCode="";
		this.ProductCode=ProductCode;
		
		this.ProductTypeID=ProductTypeID;
		this.SupplierID=SupplierID;
		
		
		this.SupplyPrice=SupplyPrice;
		this.ProductQuantity=ProductQuantity;
		this.ProductMarkUp=ProductMarkUp;
		this.RetailPrice=RetailPrice;
		
		if(InsertionDate==null)InsertionDate="";
		this.InsertionDate=InsertionDate;
		
		if(ModificationDate==null)ModificationDate="";
		this.ModificationDate=ModificationDate;
		
		this.SupplierName="";
		this.ProducTypeName="";
		
	}

	public String getSupplierName() {
		return SupplierName;
	}

	public void setSupplierName(String supplierName) {
		SupplierName = supplierName;
	}

	public String getProducTypeName() {
		return ProducTypeName;
	}

	public void setProducTypeName(String producTypeName) {
		ProducTypeName = producTypeName;
	}

	public long getEnterprisedID() {
		return EnterprisedID;
	}

	public void setEnterprisedID(long enterprisedID) {
		EnterprisedID = enterprisedID;
	}

	public long getProductID() {
		return ProductID;
	}

	public void setProductID(long productID) {
		ProductID = productID;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		if(productName==null)productName="";
		ProductName = productName;
	}

	public Double getRetailPrice() {
		return RetailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		RetailPrice = retailPrice;
	}

	public String getInsertionDate() {
		return InsertionDate;
	}

	public void setInsertionDate(String insertionDate) {
		if(insertionDate==null)insertionDate="";
		InsertionDate = insertionDate;
	}

	public String getProductCode() {
		return ProductCode;
	}

	public void setProductCode(String productCode) {
		if(productCode==null)productCode="";
		ProductCode = productCode;
	}

	public long getProductTypeID() {
		return ProductTypeID;
	}

	public void setProductTypeID(int productTypeID) {
		ProductTypeID = productTypeID;
	}

	public long getSupplierID() {
		return SupplierID;
	}

	public void setSupplierID(int supplierID) {
		SupplierID = supplierID;
	}

	public double getSupplyPrice() {
		return SupplyPrice;
	}

	public void setSupplyPrice(double supplyPrice) {
		SupplyPrice = supplyPrice;
	}

	public int getProductQuantity() {
		return ProductQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		ProductQuantity = productQuantity;
	}

	public double getProductMarkUp() {
		return ProductMarkUp;
	}

	public void setProductMarkUp(double productMarkUp) {
		ProductMarkUp = productMarkUp;
	}

	public String getModificationDate() {
		return ModificationDate;
	}

	public void setModificationDate(String modificationDate) {
		if(modificationDate==null)modificationDate="";
		ModificationDate = modificationDate;
	}

	public void setRetailPrice(double retailPrice) {
		RetailPrice = retailPrice;
	}
	
	
	

}
