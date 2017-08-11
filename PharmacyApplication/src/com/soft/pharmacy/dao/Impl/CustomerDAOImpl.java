package com.soft.pharmacy.dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.soft.pharmacy.dao.CustomerDAO;
import com.soft.pharmacy.model.Customer;

public class CustomerDAOImpl extends JdbcDaoSupport implements CustomerDAO{

	private static final Logger logger = Logger.getLogger(CustomerDAOImpl.class);
	
	private PlatformTransactionManager transactionManager;
	
	@Autowired
	public CustomerDAOImpl(DataSource dataSource) {
		this.setDataSource(dataSource);

	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	@Override
	public int save(Customer customer) {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		int result = 0;
		try {

			String sql = "INSERT INTO [dbo].[POS_TBLCUSTOMERS] ([ENTERPRISEID],[CUS_FNAME],[CUS_LNAME]"
					+ ",[CUS_EMAIL],[CUS_PHONENUMBER],[CUS_ADDRESS1],[CUS_ADDRESS2],[CUS_PROVINCE],"
					+ "[CUS_ZIP],[CUS_COUNTRY],[CUS_COMMENTS],[CUS_INSERTDATE],[CUS_MODIFYDATE])"
					+ " values ("+customer.getEnterpriseID()+",'"+customer.getFirstName()+"','"+customer.getLastName()+"','"+
								customer.getEmail()+"','"+customer.getPhoneNumber()+"','"+customer.getAddress1()+"','"+
								customer.getAddress2()+"','"+customer.getState()+"','"+customer.getZip()+"','"+
								customer.getCountry()+"','"+customer.getComments()+"',GETDATE(),GETDATE())";
			
			result = this.getJdbcTemplate().update(sql);
			logger.debug(sql);
			transactionManager.commit(status);
		} catch (DataAccessException e) {
			logger.debug("Error in creating record, rolling back");
			transactionManager.rollback(status);
			throw e;
		}
		
		return result;
	}

	@Override
	public int update(Customer customer) {
		
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
		String sql = " UPDATE [dbo].[POS_TBLCUSTOMERS] "
				   + " SET [CUS_FNAME] = '"+customer.getFirstName()+"',CUS_LNAME = '" + customer.getLastName()+"',"
				   + " [CUS_EMAIL]='"+customer.getEmail()+"',[CUS_PHONENUMBER]='"+customer.getPhoneNumber()+"',"
				   + " [CUS_ADDRESS1]='"+customer.getAddress1()+"',[CUS_ADDRESS2]='"+customer.getAddress2()+"',"
				   + " [CUS_PROVINCE]='"+customer.getState()+"',[CUS_ZIP]='"+customer.getZip()+"',"
				   + " [CUS_COUNTRY]='"+customer.getCountry()+"',[CUS_COMMENTS]='"+customer.getComments()+"',"
				   + " [CUS_MODIFYDATE]=GETDATE()"
				   + " WHERE [ENTERPRISEID] = "+customer.getEnterpriseID()
				   + " and [CUS_ID] = " + customer.getCustomerID();
		
		result = this.getJdbcTemplate().update(sql);
		transactionManager.commit(status);
		} catch (DataAccessException e) {
			logger.debug("Error in creating record, rolling back");
			transactionManager.rollback(status);
			throw e;
		}
		
		return result;
	}

	@Override
	public int delete(long customerid,long EnterprisedID) {
		
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
		String sql = " DELETE FROM [dbo].[POS_TBLCUSTOMERS] "
				   + " WHERE [ENTERPRISEID] = "+EnterprisedID
				   + " and [CUS_ID] = " + customerid;
		
		result = this.getJdbcTemplate().update(sql);
		transactionManager.commit(status);
		} catch (DataAccessException e) {
			logger.debug("Error in creating record, rolling back");
			transactionManager.rollback(status);
			throw e;
		}
		
		return result;
	}

	@Override
	public ArrayList<Customer> getCustomers(long EnterprisedID) {
		String sql= " SELECT [CUS_ID],[ENTERPRISEID],[CUS_FNAME],[CUS_LNAME],[CUS_EMAIL],"
				+   " [CUS_PHONENUMBER],[CUS_ADDRESS1],[CUS_ADDRESS2],[CUS_PROVINCE],[CUS_ZIP],"
				+   " [CUS_COUNTRY],[CUS_COMMENTS],[CUS_INSERTDATE],[CUS_MODIFYDATE] " 
			    +	" FROM [POS_TBLCUSTOMERS] WHERE [ENTERPRISEID] = " + EnterprisedID
			    +	" ORDER BY [CUS_ID] ASC ";
		System.out.println(sql);
		return (ArrayList<Customer>) this.getJdbcTemplate().query(sql, new RowMapper<Customer>(){
			@Override
			public Customer mapRow(ResultSet rs, int row)
					throws SQLException {
				Customer cus = new Customer(rs.getLong("CUS_ID"), rs.getLong("ENTERPRISEID"), 
						rs.getString("CUS_FNAME"), rs.getString("CUS_LNAME"), rs.getString("CUS_EMAIL"), 
						rs.getString("CUS_PHONENUMBER"), rs.getString("CUS_ADDRESS1"), rs.getString("CUS_ADDRESS2"), 
						rs.getString("CUS_PROVINCE"), rs.getString("CUS_ZIP"), rs.getString("CUS_COUNTRY"), 
						rs.getString("CUS_COMMENTS"), rs.getString("CUS_INSERTDATE"), rs.getString("CUS_MODIFYDATE"));
				return cus;
			}

		});
	}

	@Override
	public Customer getCustomer(long customerid,long EnterprisedID) {
		String sql= " SELECT [CUS_ID],[ENTERPRISEID],[CUS_FNAME],[CUS_LNAME],[CUS_EMAIL],"
				+   " [CUS_PHONENUMBER],[CUS_ADDRESS1],[CUS_ADDRESS2],[CUS_PROVINCE],[CUS_ZIP],"
				+   " [CUS_COUNTRY],[CUS_COMMENTS],[CUS_INSERTDATE],[CUS_MODIFYDATE] " 
				+   " FROM [POS_TBLCUSTOMERS] WHERE [ENTERPRISEID] = " + EnterprisedID
				+   " AND [CUS_ID] = " + customerid;
		System.out.println(sql);
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<Customer>() {
			@Override
			public Customer extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				Customer cus = new Customer();
				if(rs.next()){
					  cus = new Customer(rs.getLong("CUS_ID"), rs.getLong("ENTERPRISEID"), 
							rs.getString("CUS_FNAME"), rs.getString("CUS_LNAME"), rs.getString("CUS_EMAIL"), 
							rs.getString("CUS_PHONENUMBER"), rs.getString("CUS_ADDRESS1"), rs.getString("CUS_ADDRESS2"), 
							rs.getString("CUS_PROVINCE"), rs.getString("CUS_ZIP"), rs.getString("CUS_COUNTRY"), 
							rs.getString("CUS_COMMENTS"), rs.getString("CUS_INSERTDATE"), rs.getString("CUS_MODIFYDATE"));
				}else{
					return null;
				}
				
				return cus;
			}
			

		});
	}

}
