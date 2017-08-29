package com.soft.pharmacy.dao;

import java.util.ArrayList;

import com.soft.pharmacy.model.Bill;

public interface ReportDAO {
	
	ArrayList<Bill> getBillsByCustomerID(long CustomerID,long EnterprisedID,String FromDate,String ToDate);

}
