package com.soft.pharmacy.model;

public class Product {
	
	long ProductID;
	String ProductName;
	int CategoryID;
	String Magnitude;
	String Nomenclature;
	String CriticalUnit;
	String Manufacture;
	Double TradePrice;
	Double RetailPrice;
	String InsertionDate;
	
	public Product(){
		
		this.ProductID=0;
		this.ProductName="";
		this.CategoryID=0;
		this.Magnitude="";
		this.Nomenclature="";
		this.CriticalUnit="";
		this.Manufacture="";
		this.TradePrice=0.0;
		this.RetailPrice=0.0;
		this.InsertionDate="";
		
	}
	
	public Product(long ProductID,String ProductName,int CategoryID,String Magnitude,String Nomenclature,
	String CriticalUnit,String Manufacture,Double TradePrice,Double RetailPrice,String InsertionDate){
		
		this.ProductID=ProductID;
		
		if(ProductName==null)ProductName="";
		this.ProductName=ProductName;
		
		this.CategoryID=CategoryID;
		
		if(Magnitude==null)Magnitude="";
		this.Magnitude=Magnitude;
		
		if(Nomenclature==null)Nomenclature="";
		this.Nomenclature=Nomenclature;
		
		if(CriticalUnit==null)CriticalUnit="";
		this.CriticalUnit=CriticalUnit;
		
		if(Manufacture==null)Manufacture="";
		this.Manufacture=Manufacture;
		
		this.TradePrice=TradePrice;
		this.RetailPrice=RetailPrice;
		
		if(InsertionDate==null)InsertionDate="";
		this.InsertionDate=InsertionDate;
		
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

	public int getCategoryID() {
		return CategoryID;
	}

	public void setCategoryID(int categoryID) {
		CategoryID = categoryID;
	}

	public String getMagnitude() {
		return Magnitude;
	}

	public void setMagnitude(String magnitude) {
		if(magnitude==null)magnitude="";
		Magnitude = magnitude;
	}

	public String getNomenclature() {
		return Nomenclature;
	}

	public void setNomenclature(String nomenclature) {
		if(nomenclature==null)nomenclature="";
		Nomenclature = nomenclature;
	}

	public String getCriticalUnit() {
		return CriticalUnit;
	}

	public void setCriticalUnit(String criticalUnit) {
		if(criticalUnit==null)criticalUnit="";
		CriticalUnit = criticalUnit;
	}

	public String getManufacture() {
		return Manufacture;
	}

	public void setManufacture(String manufacture) {
		if(manufacture==null)manufacture="";
		Manufacture = manufacture;
	}

	public Double getTradePrice() {
		return TradePrice;
	}

	public void setTradePrice(Double tradePrice) {
		TradePrice = tradePrice;
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
	
	
	

}
