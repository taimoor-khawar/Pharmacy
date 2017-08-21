package com.soft.pharmacy.dao;

import java.util.ArrayList;

import com.soft.pharmacy.model.Bill;
import com.soft.pharmacy.model.Billline;



public interface OrderDAO {
	
	
	//Order
	int createOrder(Bill bill,ArrayList<Billline> billines);
	int updateOrder(Bill bill,ArrayList<Billline> billines);
	int deleteOrder(long Billid,long EnterprisedID);
	ArrayList<Billline> getBilllines(long BillID,long EnterprisedID);
	
	//Bill
	long getBillID();
	int saveBill(Bill bill);
	int updateBill(Bill bill);
	int deleteBill(long Billid,long EnterprisedID);
	ArrayList<Bill> getBills(long EnterprisedID);
	Bill getBill(long Billid,long EnterprisedID);
	
	
	//BillLine
	int saveBillline(Billline bill);
	int updateBillline(Billline bill);
	int deleteBillline(long Billlineid,long EnterprisedID);
	ArrayList<Billline> getBilllines(long EnterprisedID);
	Billline getBillline(long Billlineid,long EnterprisedID);

}
