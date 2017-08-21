package com.soft.pharmacy.model;

public class Product {
	
	long ProductID;
	long EnterprisedID;
	String ProductName;
	String ProductCode;
	long ProductTypeID;
	long SupplierID;
	int ProductQuantity;
	double SellingPrice;
	double BuyingPrice;
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
		
		this.ProductQuantity=0;
		this.SellingPrice=0;
		this.BuyingPrice=0.0;
		this.InsertionDate="";
		this.ModificationDate="";
		this.SupplierName="";
		this.ProducTypeName="";
		
	}
	
	public Product(long ProductID,long EnterprisedID,String ProductName,String ProductCode,long ProductTypeID,long SupplierID,
				   int ProductQuantity,double SellingPrice,double BuyingPrice,
				   String InsertionDate,String ModificationDate){
		
		this.ProductID=ProductID;
		this.EnterprisedID = EnterprisedID;
		
		if(ProductName==null)ProductName="";
		this.ProductName=ProductName;
		
		if(ProductCode==null)ProductCode="";
		this.ProductCode=ProductCode;
		
		this.ProductTypeID=ProductTypeID;
		this.SupplierID=SupplierID;
		
		
		
		this.ProductQuantity=ProductQuantity;
		this.SellingPrice=SellingPrice;
		this.BuyingPrice=BuyingPrice;
		
		if(InsertionDate==null)InsertionDate="";
		this.InsertionDate=InsertionDate;
		
		if(ModificationDate==null)ModificationDate="";
		this.ModificationDate=ModificationDate;
		
		this.SupplierName="";
		this.ProducTypeName="";
		
	}

	public long getProductID() {
		return ProductID;
	}

	public void setProductID(long productID) {
		ProductID = productID;
	}

	public long getEnterprisedID() {
		return EnterprisedID;
	}

	public void setEnterprisedID(long enterprisedID) {
		EnterprisedID = enterprisedID;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public String getProductCode() {
		return ProductCode;
	}

	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}

	public long getProductTypeID() {
		return ProductTypeID;
	}

	public void setProductTypeID(long productTypeID) {
		ProductTypeID = productTypeID;
	}

	public long getSupplierID() {
		return SupplierID;
	}

	public void setSupplierID(long supplierID) {
		SupplierID = supplierID;
	}
	
	public int getProductQuantity() {
		return ProductQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		ProductQuantity = productQuantity;
	}

	public double getSellingPrice() {
		return SellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		SellingPrice = sellingPrice;
	}

	public double getBuyingPrice() {
		return BuyingPrice;
	}

	public void setBuyingPrice(double buyingPrice) {
		BuyingPrice = buyingPrice;
	}

	public String getInsertionDate() {
		return InsertionDate;
	}

	public void setInsertionDate(String insertionDate) {
		InsertionDate = insertionDate;
	}

	public String getModificationDate() {
		return ModificationDate;
	}

	public void setModificationDate(String modificationDate) {
		ModificationDate = modificationDate;
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

	
	
	
	

}
