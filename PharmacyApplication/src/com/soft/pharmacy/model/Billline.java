package com.soft.pharmacy.model;

public class Billline {
	
	long BilllineID;
	long EnterprisedID;
	long BillID;
	long ProductID;
	int ProductQuantity;
	double ProductAmount;
	double BilllineAmount;
	double BilllineDiscount;
	double BilllineDueAmount;
	String InsertionDate;
	String ModificationDate;
	
	String ProductName;
	
	public Billline(){
		
		this.BilllineID = 0;
		this.BillID=0;
		this.EnterprisedID=0;
		this.ProductID=0;
		this.ProductQuantity=0;
		
		this.ProductAmount = 0;
		this.BilllineAmount=0;
		this.BilllineDiscount=0;
		this.BilllineDueAmount=0.0;
		this.InsertionDate="";
		this.ModificationDate="";
		
		this.ProductName="";
		
		
	}
	
	public Billline(long BilllineID,long EnterprisedID,long BillID,long ProductID,int ProductQuantity,
	double ProductAmount,double BilllineAmount,double BilllineDiscount,double BilllineDueAmount,String InsertionDate,
	String ModificationDate){
		
		
		this.BilllineID = BilllineID;
		this.BillID=BillID;
		this.EnterprisedID = EnterprisedID;
		this.ProductID=ProductID;
		this.ProductQuantity = ProductQuantity;
		
		this.ProductAmount = ProductAmount;
		this.BilllineAmount=BilllineAmount;
		this.BilllineDiscount=BilllineDiscount;
		this.BilllineDueAmount=BilllineDueAmount;
		
		if(InsertionDate==null)InsertionDate="";
		this.InsertionDate=InsertionDate;
		
		if(ModificationDate==null)ModificationDate="";
		this.ModificationDate=ModificationDate;
		
		this.ProductName="";
		
	}

	public long getBilllineID() {
		return BilllineID;
	}

	public void setBilllineID(long billlineID) {
		BilllineID = billlineID;
	}

	public long getEnterprisedID() {
		return EnterprisedID;
	}

	public void setEnterprisedID(long enterprisedID) {
		EnterprisedID = enterprisedID;
	}

	public long getBillID() {
		return BillID;
	}

	public void setBillID(long billID) {
		BillID = billID;
	}

	public long getProductID() {
		return ProductID;
	}

	public void setProductID(long productID) {
		ProductID = productID;
	}

	public double getBilllineAmount() {
		return BilllineAmount;
	}

	public void setBilllineAmount(double billlineAmount) {
		BilllineAmount = billlineAmount;
	}

	public double getBilllineDiscount() {
		return BilllineDiscount;
	}

	public void setBilllineDiscount(double billlineDiscount) {
		BilllineDiscount = billlineDiscount;
	}

	public double getBilllineDueAmount() {
		return BilllineDueAmount;
	}

	public void setBilllineDueAmount(double billlineDueAmount) {
		BilllineDueAmount = billlineDueAmount;
	}

	public String getInsertionDate() {
		return InsertionDate;
	}

	public void setInsertionDate(String insertionDate) {
		if(insertionDate==null)insertionDate="";
		InsertionDate = insertionDate;
	}

	public String getModificationDate() {
		return ModificationDate;
	}

	public void setModificationDate(String modificationDate) {
		if(modificationDate==null)modificationDate="";
		ModificationDate = modificationDate;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		if(productName==null)productName="";
		ProductName = productName;
	}

	public int getProductQuantity() {
		return ProductQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		ProductQuantity = productQuantity;
	}

	public double getProductAmount() {
		return ProductAmount;
	}

	public void setProductAmount(double productAmount) {
		ProductAmount = productAmount;
	}
	
	
	
}
