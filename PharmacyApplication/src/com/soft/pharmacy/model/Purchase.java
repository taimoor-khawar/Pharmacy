package com.soft.pharmacy.model;

public class Purchase {
	
	long PurchaseID;
	long EnterprisedID;
	long ProductID;
	long SupplierID;
	double Quantity;
	double OLDQuantity;
	double BuyingPrice;
	
	String InsertionDate;
	String ModificationDate;
	
	String ProductName;
	String SupplierName;
	
	public Purchase(){
		
		this.PurchaseID=0;
		this.EnterprisedID=0;
		this.ProductID=0;
		this.SupplierID=0;
		this.Quantity=0.0;
		this.OLDQuantity = 0.0;
		this.BuyingPrice = 0.0;
		
		this.InsertionDate="";
		this.ModificationDate="";
		
		this.ProductName="";
		this.SupplierName="";
		
		
	}
	
	public Purchase(long PurchaseID,long EnterprisedID,long ProductID,long SupplierID,
	double Quantity,double OLDQuantity,double BuyingPrice,
			String InsertionDate,String ModificationDate){
		
		this.PurchaseID = PurchaseID;
		this.EnterprisedID = EnterprisedID;
		this.ProductID = ProductID;
		this.SupplierID = SupplierID;
		this.Quantity = Quantity;
		this.OLDQuantity = OLDQuantity;
		this.BuyingPrice = BuyingPrice;
		
		if(InsertionDate==null)InsertionDate="";
		this.InsertionDate=InsertionDate;
		
		if(ModificationDate==null)ModificationDate="";
		this.ModificationDate=ModificationDate;
		
		this.ProductName="";
		this.SupplierName="";
		
	}

	public long getPurchaseID() {
		return PurchaseID;
	}

	public void setPurchaseID(long purchaseID) {
		PurchaseID = purchaseID;
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

	public long getSupplierID() {
		return SupplierID;
	}

	public void setSupplierID(long supplierID) {
		SupplierID = supplierID;
	}

	public double getQuantity() {
		return Quantity;
	}

	public void setQuantity(double quantity) {
		Quantity = quantity;
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

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		if(productName==null)productName="";
		ProductName = productName;
	}

	public String getSupplierName() {
		return SupplierName;
	}

	public void setSupplierName(String supplierName) {
		if(supplierName==null)supplierName="";
		SupplierName = supplierName;
	}

	public double getOLDQuantity() {
		return OLDQuantity;
	}

	public void setOLDQuantity(double oLDQuantity) {
		OLDQuantity = oLDQuantity;
	}

	public double getBuyingPrice() {
		return BuyingPrice;
	}

	public void setBuyingPrice(double buyingPrice) {
		BuyingPrice = buyingPrice;
	}	
}
