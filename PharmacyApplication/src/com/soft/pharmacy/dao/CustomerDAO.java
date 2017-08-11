package com.soft.pharmacy.dao;

import java.util.ArrayList;

import com.soft.pharmacy.model.Customer;


public interface CustomerDAO {

	int save(Customer customer);
	int update(Customer customer);
	int delete(long customerid,long EnterprisedID);
	ArrayList<Customer> getCustomers(long EnterprisedID);
	Customer getCustomer(long customerid,long EnterprisedID);
}
