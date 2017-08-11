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

import com.soft.pharmacy.dao.ConfigDAO;
import com.soft.pharmacy.model.ProductType;
import com.soft.pharmacy.model.Supplier;

public class ConfigDAOImpl extends JdbcDaoSupport implements ConfigDAO{

	private static final Logger logger = Logger.getLogger(CustomerDAOImpl.class);
	
	private PlatformTransactionManager transactionManager;
	
	@Autowired
	public ConfigDAOImpl(DataSource dataSource) {
		this.setDataSource(dataSource);

	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	
	//Supplier
	@Override
	public int saveSupplier(Supplier sup) {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		int result = 0;
		try {
			String sql = "INSERT INTO [dbo].[POS_TBLSUPPLIERS]( [ENTERPRISEID],[SUP_FNAME],[SUP_LNAME],[SUP_EMAIL],"
					+    "[SUP_PHONENUMBER],[SUP_ADDRESS1],[SUP_ADDRESS2],[SUP_PROVINCE],[SUP_ZIP],"
					+    "[SUP_COUNTRY],[SUP_CODE],[SUP_INSERTDATE],[SUP_MODIFYDATE])"
					+    " values ("+sup.getEnterpriseID()+",'"+sup.getFirstName()+"','"+sup.getLastName()+"','"+
					sup.getEmail()+"','"+sup.getPhoneNumber()+"','"+sup.getAddress1()+"','"+
					sup.getAddress2()+"','"+sup.getState()+"','"+sup.getZip()+"','"+
					sup.getCountry()+"','"+sup.getCode()+"',GETDATE(),GETDATE())";
			
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
	public int updateSupplier(Supplier sup) {
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
		String sql = " UPDATE [dbo].[POS_TBLSUPPLIERS] "
				   + " SET [SUP_FNAME] = '"+sup.getFirstName()+"',SUP_LNAME = '" + sup.getLastName()+"',"
				   + " [SUP_EMAIL]='"+sup.getEmail()+"',[SUP_PHONENUMBER]='"+sup.getPhoneNumber()+"',"
				   + " [SUP_ADDRESS1]='"+sup.getAddress1()+"',[SUP_ADDRESS2]='"+sup.getAddress2()+"',"
				   + " [SUP_PROVINCE]='"+sup.getState()+"',[SUP_ZIP]='"+sup.getZip()+"',"
				   + " [SUP_COUNTRY]='"+sup.getCountry()+"',[SUP_CODE]='"+sup.getCode()+"',"
				   + " [SUP_MODIFYDATE]=GETDATE()"
				   + " WHERE [ENTERPRISEID] = "+sup.getEnterpriseID()
				   + " and [SUP_ID] = " + sup.getSupplierID();
		
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
	public int deleteSupplier(long supplierid, long EnterprisedID) {
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
		String sql = " DELETE FROM [dbo].[POS_TBLSUPPLIERS] "
				   + " WHERE [ENTERPRISEID] = "+EnterprisedID
				   + " and [SUP_ID] = " + supplierid;
		
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
	public ArrayList<Supplier> getSuppliers(long EnterprisedID) {
		
		String sql= " SELECT [SUP_ID],[ENTERPRISEID],[SUP_FNAME],[SUP_LNAME],[SUP_EMAIL],"
				+   "[SUP_PHONENUMBER],[SUP_ADDRESS1],[SUP_ADDRESS2],[SUP_PROVINCE],[SUP_ZIP],"
				+   "[SUP_COUNTRY],[SUP_CODE],[SUP_INSERTDATE],[SUP_MODIFYDATE] " 
			    +	" FROM [POS_TBLSUPPLIERS] WHERE [ENTERPRISEID] = " + EnterprisedID
			    +	" ORDER BY [SUP_ID] ASC ";
		System.out.println(sql);
		return (ArrayList<Supplier>) this.getJdbcTemplate().query(sql, new RowMapper<Supplier>(){
			@Override
			public Supplier mapRow(ResultSet rs, int row)
					throws SQLException {
				Supplier sup = new Supplier(rs.getLong("SUP_ID"), rs.getLong("ENTERPRISEID"), 
						rs.getString("SUP_FNAME"), rs.getString("SUP_LNAME"), rs.getString("SUP_EMAIL"), 
						rs.getString("SUP_PHONENUMBER"), rs.getString("SUP_ADDRESS1"), rs.getString("SUP_ADDRESS2"), 
						rs.getString("SUP_PROVINCE"), rs.getString("SUP_ZIP"), rs.getString("SUP_COUNTRY"), 
						rs.getString("SUP_CODE"), rs.getString("SUP_INSERTDATE"), rs.getString("SUP_MODIFYDATE"));
				return sup;
			}

		});
	}

	@Override
	public Supplier getSupplier(long supplierid, long EnterprisedID) {
		String sql= " SELECT [SUP_ID],[ENTERPRISEID],[SUP_FNAME],[SUP_LNAME],[SUP_EMAIL],"
				+   "[SUP_PHONENUMBER],[SUP_ADDRESS1],[SUP_ADDRESS2],[SUP_PROVINCE],[SUP_ZIP],"
				+   "[SUP_COUNTRY],[SUP_CODE],[SUP_INSERTDATE],[SUP_MODIFYDATE] " 
			    +	" FROM [POS_TBLSUPPLIERS] WHERE [ENTERPRISEID] = " + EnterprisedID
				+   " AND [SUP_ID] = " + supplierid;
		System.out.println(sql);
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<Supplier>() {
			@Override
			public Supplier extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				Supplier sup = new Supplier();
				if(rs.next()){
					sup = new Supplier(rs.getLong("SUP_ID"), rs.getLong("ENTERPRISEID"), 
							rs.getString("SUP_FNAME"), rs.getString("SUP_LNAME"), rs.getString("SUP_EMAIL"), 
							rs.getString("SUP_PHONENUMBER"), rs.getString("SUP_ADDRESS1"), rs.getString("SUP_ADDRESS2"), 
							rs.getString("SUP_PROVINCE"), rs.getString("SUP_ZIP"), rs.getString("SUP_COUNTRY"), 
							rs.getString("SUP_CODE"), rs.getString("SUP_INSERTDATE"), rs.getString("SUP_MODIFYDATE"));
				}else{
					return null;
				}
				
				return sup;
			}
			

		});
	}

	@Override
	public int importsupplierdata(ArrayList<Supplier> SupplierList) {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		int result = 0;
		try {
			for(int i=0;i<SupplierList.size();i++){
				Supplier sup = (Supplier)SupplierList.get(i);
				String sql = "INSERT INTO [dbo].[POS_TBLSUPPLIERS]( [ENTERPRISEID],[SUP_FNAME],[SUP_LNAME],[SUP_EMAIL],"
						+    "[SUP_PHONENUMBER],[SUP_ADDRESS1],[SUP_ADDRESS2],[SUP_PROVINCE],[SUP_ZIP],"
						+    "[SUP_COUNTRY],[SUP_CODE],[SUP_INSERTDATE],[SUP_MODIFYDATE])"
						+    " values ("+sup.getEnterpriseID()+",'"+sup.getFirstName()+"','"+sup.getLastName()+"','"+
						sup.getEmail()+"','"+sup.getPhoneNumber()+"','"+sup.getAddress1()+"','"+
						sup.getAddress2()+"','"+sup.getState()+"','"+sup.getZip()+"','"+
						sup.getCountry()+"','"+sup.getCode()+"',GETDATE(),GETDATE())";
				
				result = this.getJdbcTemplate().update(sql);
				logger.debug(sql);
			}
			transactionManager.commit(status);
		} catch (DataAccessException e) {
			logger.debug("Error in creating record, rolling back");
			transactionManager.rollback(status);
			throw e;
		}
		
		return result;
	}
	
	
	@Override
	public Supplier getBySupplierCode(String SupplierCode, long EnterprisedID) {
		String sql= " SELECT * " 
			    +	" FROM [POS_TBLSUPPLIERS] WHERE [ENTERPRISEID] = " + EnterprisedID
				+   " AND LOWER([SUP_CODE]) = '" + SupplierCode.toLowerCase()+"'";
		System.out.println(sql);
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<Supplier>() {
			public Supplier extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				Supplier sup = new Supplier();
				if(rs.next()){
					sup = new Supplier(rs.getLong("SUP_ID"), rs.getLong("ENTERPRISEID"), 
							rs.getString("SUP_FNAME"), rs.getString("SUP_LNAME"), rs.getString("SUP_EMAIL"), 
							rs.getString("SUP_PHONENUMBER"), rs.getString("SUP_ADDRESS1"), rs.getString("SUP_ADDRESS2"), 
							rs.getString("SUP_PROVINCE"), rs.getString("SUP_ZIP"), rs.getString("SUP_COUNTRY"), 
							rs.getString("SUP_CODE"), rs.getString("SUP_INSERTDATE"), rs.getString("SUP_MODIFYDATE"));
				}else{
					return null;
				}
				
				return sup;
			}
			

		});
	}

	
	//ProductType
	
	@Override
	public int saveProductType(ProductType producttype) {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		int result = 0;
		try {
			String sql = "INSERT INTO [dbo].[POS_TBLPRODUCTTYPE]( [ENTERPRISEID],[PT_NAME],[PT_INSERTDATE],[PT_MODIFYDATE])"
					+    " values ("+producttype.getEnterprisedID()+",'"+producttype.getProductTypeName()+"',GETDATE(),GETDATE())";
			
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
	public int updateProductType(ProductType producttype) {
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
		String sql = " UPDATE [dbo].[POS_TBLPRODUCTTYPE] "
				   + " SET [PT_NAME] = '"+producttype.getProductTypeName()+"',"
				   + " [PT_MODIFYDATE]=GETDATE()"
				   + " WHERE [ENTERPRISEID] = "+producttype.getEnterprisedID()
				   + " and [PT_ID] = " + producttype.getProductTypeID();
		
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
	public int deleteProductType(long ProductTypeID, long EnterprisedID) {
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
		String sql = " DELETE FROM [dbo].[POS_TBLPRODUCTTYPE] "
				   + " WHERE [ENTERPRISEID] = "+EnterprisedID
				   + " and [PT_ID] = " + ProductTypeID;
		
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
	public ArrayList<ProductType> getProductTypes(long EnterprisedID) {
		String sql= " SELECT [PT_ID],[ENTERPRISEID],[PT_NAME],[PT_INSERTDATE],[PT_MODIFYDATE] " 
			    +	" FROM [POS_TBLPRODUCTTYPE] WHERE [ENTERPRISEID] = " + EnterprisedID
			    +	" ORDER BY [PT_ID] ASC ";
		System.out.println(sql);
		return (ArrayList<ProductType>) this.getJdbcTemplate().query(sql, new RowMapper<ProductType>(){
			@Override
			public ProductType mapRow(ResultSet rs, int row)
					throws SQLException {
				ProductType pt = new ProductType(rs.getLong("PT_ID"), rs.getLong("ENTERPRISEID"), 
						rs.getString("PT_NAME"), rs.getString("PT_INSERTDATE"), rs.getString("PT_MODIFYDATE"));
				return pt;
			}

		});
	}

	@Override
	public ProductType getProductType(long ProductTypeID, long EnterprisedID) {
		String sql= " SELECT [PT_ID],[ENTERPRISEID],[PT_NAME],[PT_INSERTDATE],[PT_MODIFYDATE] " 
			    +	" FROM [POS_TBLPRODUCTTYPE] WHERE [ENTERPRISEID] = " + EnterprisedID
			    +   " AND  [PT_ID] = " + ProductTypeID
			    +	" ORDER BY [PT_ID] DESC ";
		System.out.println(sql);
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<ProductType>() {
			@Override
			public ProductType extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				ProductType pt = new ProductType();
				if(rs.next()){
					pt = new ProductType(rs.getLong("PT_ID"), rs.getLong("ENTERPRISEID"), 
							rs.getString("PT_NAME"), rs.getString("PT_INSERTDATE"), rs.getString("PT_MODIFYDATE"));
				}else{
					return null;
				}
				
				return pt;
			}
			

		});
	}

	@Override
	public int importProductTypedata(ArrayList<ProductType> ProductTypeList) {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		int result = 0;
		try {
			for(int i=0;i<ProductTypeList.size();i++){
				ProductType pt = (ProductType)ProductTypeList.get(i);
				String sql = "INSERT INTO [dbo].[POS_TBLPRODUCTTYPE]( [ENTERPRISEID],[PT_NAME],[PT_INSERTDATE],[PT_MODIFYDATE])"
						+    " values ("+pt.getEnterprisedID()+",'"+pt.getProductTypeName()+"',GETDATE(),GETDATE())";
				
				result = this.getJdbcTemplate().update(sql);
				logger.debug(sql);
			}
			transactionManager.commit(status);
		} catch (DataAccessException e) {
			logger.debug("Error in creating record, rolling back");
			transactionManager.rollback(status);
			throw e;
		}
		
		return result;

	}
	
	
	
}
