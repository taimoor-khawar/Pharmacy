package com.soft.pharmacy.model;

public class Bill {
	
	long BillID;
	long EnterprisedID;
	long CustomerID;
	String BillName;
	double BillAmount;
	double BillDiscount;
	double BillDueAmount;
	String InsertionDate;
	String ModificationDate;
	
	String CustomerName;
	String CustomerAddress;
	
	public Bill(){
		
		this.BillID=0;
		this.EnterprisedID=0;
		this.CustomerID=0;
		this.BillName = "";
		
		this.BillAmount=0;
		this.BillDiscount=0;
		this.BillDueAmount=0.0;
		this.InsertionDate="";
		this.ModificationDate="";
		
		this.CustomerName="";
		this.CustomerAddress="";
		
		
	}
	
	public Bill(long BillID,long EnterprisedID,long CustomerID,String BillName,double BillAmount,
	double BillDiscount,double BillDueAmount,String InsertionDate,String ModificationDate){
		
		this.BillID=BillID;
		this.EnterprisedID = EnterprisedID;
		this.CustomerID=CustomerID;
		if(BillName==null)BillName="";
		this.BillName = BillName;
		
		this.BillAmount=BillAmount;
		this.BillDiscount=BillDiscount;
		this.BillDueAmount=BillDueAmount;
		
		if(InsertionDate==null)InsertionDate="";
		this.InsertionDate=InsertionDate;
		
		if(ModificationDate==null)ModificationDate="";
		this.ModificationDate=ModificationDate;
		
		this.CustomerName="";
		this.CustomerAddress="";
		
	}

	public long getBillID() {
		return BillID;
	}

	public void setBillID(long billID) {
		BillID = billID;
	}

	public long getEnterprisedID() {
		return EnterprisedID;
	}

	public void setEnterprisedID(long enterprisedID) {
		EnterprisedID = enterprisedID;
	}

	public long getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(long customerID) {
		CustomerID = customerID;
	}

	public double getBillAmount() {
		return BillAmount;
	}

	public void setBillAmount(double billAmount) {
		BillAmount = billAmount;
	}

	public double getBillDiscount() {
		return BillDiscount;
	}

	public void setBillDiscount(double billDiscount) {
		BillDiscount = billDiscount;
	}

	public double getBillDueAmount() {
		return BillDueAmount;
	}

	public void setBillDueAmount(double billDueAmount) {
		BillDueAmount = billDueAmount;
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

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		if(customerName==null)customerName="";
		CustomerName = customerName;
	}

	public String getBillName() {
		return BillName;
	}

	public void setBillName(String billName) {
		BillName = billName;
	}

	public String getCustomerAddress() {
		return CustomerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		if(customerAddress==null)customerAddress="";
		CustomerAddress = customerAddress;
	}
	
	
}
