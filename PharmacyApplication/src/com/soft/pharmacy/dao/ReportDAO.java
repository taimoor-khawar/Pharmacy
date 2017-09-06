package com.soft.pharmacy.dao;

import java.util.ArrayList;

import com.soft.pharmacy.model.Bill;
import com.soft.pharmacy.model.Purchase;

public interface ReportDAO {
	
	ArrayList<Bill> getBillsByCustomerID(long CustomerID,long EnterprisedID,String FromDate,String ToDate);
	ArrayList<Purchase> getPurchasesByProduct(long ProductID,long EnterprisedID,String FromDate,String ToDate);
}
