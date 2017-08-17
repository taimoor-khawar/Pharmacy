package com.soft.pharmacy.dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.soft.pharmacy.dao.ProductDAO;
import com.soft.pharmacy.model.Product;

public class ProductDAOImpl extends JdbcDaoSupport implements ProductDAO {

	private static final Logger logger = Logger.getLogger(CustomerDAOImpl.class);
	
	private PlatformTransactionManager transactionManager;
	
	@Autowired
	public ProductDAOImpl(DataSource dataSource) {
		this.setDataSource(dataSource);

	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	@Override
	public int saveproduct(Product product) {
		// TODO Auto-generated method stub
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		int result = 0;
		try {
			String sql = "INSERT INTO [dbo].[POS_TBLPRODUCTS]([ENTERPRISEID],[PR_NAME],[PR_CODE],"
					+    "[PR_PRODUCTTYPEID],[PR_SUPPLIERID],[PR_SUPPLYPRICE],[PR_PROQUANTITY],"
					+    "[PR_MARKUP],[PR_RETAILPRICE],[PR_INSERTDATE],[PR_MODIFYDATE])"
					+    "values ("+product.getEnterprisedID()+",'"+product.getProductName()+"','"+product.getProductCode()+"',"+
						 product.getProductTypeID()+","+product.getSupplierID()+","+product.getSupplyPrice()+","+
						 product.getProductQuantity()+","+product.getProductMarkUp()+","+product.getRetailPrice()+","+
					     "GETDATE(),GETDATE())";
			
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
	public int updateproduct(Product product) {
		// TODO Auto-generated method stub
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
		String sql = " UPDATE [dbo].[POS_TBLPRODUCTS] "
				   + " SET [PR_NAME] = '"+product.getProductName()+"',PR_CODE = '" + product.getProductCode()+"',"
				   + " [PR_PRODUCTTYPEID]="+product.getProductTypeID()+",[PR_SUPPLIERID]="+product.getSupplierID()+","
				   + " [PR_PROQUANTITY]="+product.getProductQuantity()+",[PR_MARKUP]="+product.getProductMarkUp()+","
				   + " [PR_RETAILPRICE]="+product.getRetailPrice()+","
				   + " [PR_MODIFYDATE]=GETDATE()"
				   + " WHERE [ENTERPRISEID] = "+product.getEnterprisedID()
				   + " and [PR_ID] = " + product.getProductID();
		
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
	public int deleteproduct(long productid, long EnterprisedID) {
		// TODO Auto-generated method stub
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
		String sql = " DELETE FROM [dbo].[POS_TBLPRODUCTS] "
				   + " WHERE [ENTERPRISEID] = "+EnterprisedID
				   + " and [PR_ID] = " + productid;
		
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
	public ArrayList<Product> getProducts(long EnterprisedID) {
		// TODO Auto-generated method stub
		String sql= "SELECT [PR_ID],pr.[ENTERPRISEID],[PR_NAME],[PR_CODE],"
					+ " [PR_PRODUCTTYPEID],[PR_SUPPLIERID],[PR_SUPPLYPRICE],[PR_PROQUANTITY],"
					+ " [PR_MARKUP],[PR_RETAILPRICE],[PR_INSERTDATE],[PR_MODIFYDATE], " 
					+ " pt.[PT_NAME],sup.[SUP_FNAME],sup.[SUP_CODE] "
					+ " FROM [POS_TBLPRODUCTS] pr "
					+ " INNER JOIN [POS_TBLPRODUCTTYPE] pt ON pt.[PT_ID] = pr.PR_PRODUCTTYPEID "
					+ " INNER JOIN [POS_TBLSUPPLIERS] sup ON sup.[SUP_ID] = pr.PR_SUPPLIERID "
					+ " WHERE pr.[ENTERPRISEID] = " + EnterprisedID
					+ " ORDER BY [PR_ID] ASC ";
		System.out.println(sql);
		return (ArrayList<Product>) this.getJdbcTemplate().query(sql, new RowMapper<Product>(){
			@Override
			public Product mapRow(ResultSet rs, int row)
					throws SQLException {
				Product pr = new Product(rs.getLong("PR_ID"), rs.getLong("ENTERPRISEID"), 
						rs.getString("PR_NAME"), rs.getString("PR_CODE"), rs.getLong("PR_PRODUCTTYPEID"), 
						rs.getLong("PR_SUPPLIERID"), rs.getDouble("PR_SUPPLYPRICE"), rs.getInt("PR_PROQUANTITY"), 
						rs.getDouble("PR_MARKUP"), rs.getDouble("PR_RETAILPRICE"),
						rs.getString("PR_INSERTDATE"), rs.getString("PR_MODIFYDATE"));
				pr.setProducTypeName(rs.getString("PT_NAME"));
				pr.setSupplierName(rs.getString("SUP_FNAME")+"-"+rs.getString("SUP_CODE"));
				return pr;
			}

		});
	}

	@Override
	public Product getProduct(long productid, long EnterprisedID) {
		// TODO Auto-generated method stub
		String sql= "SELECT [PR_ID],pr.[ENTERPRISEID],[PR_NAME],[PR_CODE],"
				+ " [PR_PRODUCTTYPEID],[PR_SUPPLIERID],[PR_SUPPLYPRICE],[PR_PROQUANTITY],"
				+ " [PR_MARKUP],[PR_RETAILPRICE],[PR_INSERTDATE],[PR_MODIFYDATE], " 
				+ " pt.[PT_NAME],sup.[SUP_FNAME],sup.[SUP_CODE] "
				+ " FROM [POS_TBLPRODUCTS] pr "
				+ " INNER JOIN [POS_TBLPRODUCTTYPE] pt ON pt.[PT_ID] = pr.PR_PRODUCTTYPEID "
				+ " INNER JOIN [POS_TBLSUPPLIERS] sup ON sup.[SUP_ID] = pr.PR_SUPPLIERID "
				+ " WHERE pr.[ENTERPRISEID] = " + EnterprisedID
				+ " AND [PR_ID] = " + productid;
		System.out.println(sql);
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<Product>() {
			@Override
			public Product extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				Product pr = new Product();
				if(rs.next()){
					pr = new Product(rs.getLong("PR_ID"), rs.getLong("ENTERPRISEID"), 
							rs.getString("PR_NAME"), rs.getString("PR_CODE"), rs.getLong("PR_PRODUCTTYPEID"), 
							rs.getLong("PR_SUPPLIERID"), rs.getDouble("PR_SUPPLYPRICE"), rs.getInt("PR_PROQUANTITY"), 
							rs.getDouble("PR_MARKUP"), rs.getDouble("PR_RETAILPRICE"),
							rs.getString("PR_INSERTDATE"), rs.getString("PR_MODIFYDATE"));
					pr.setProducTypeName(rs.getString("PT_NAME"));
					pr.setSupplierName(rs.getString("SUP_FNAME")+"-"+rs.getString("SUP_CODE"));
					
				}else{
					return null;
				}
				
				return pr;
			}
			

		});
	}

	@Override
	public Product getByproductCode(String productCode, long enterprisedID) {
		// TODO Auto-generated method stub
		String sql= " SELECT * " 
			    +	" FROM [POS_TBLPRODUCTS] WHERE [ENTERPRISEID] = " + enterprisedID
				+   " AND LOWER([PR_CODE]) = '" + productCode.toLowerCase()+"'";
		System.out.println(sql);
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<Product>() {
			public Product extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				Product pr = new Product();
				if(rs.next()){
					pr = new Product(rs.getLong("PR_ID"), rs.getLong("ENTERPRISEID"), 
							rs.getString("PR_NAME"), rs.getString("PR_CODE"), rs.getLong("PR_PRODUCTTYPEID"), 
							rs.getLong("PR_SUPPLIERID"), rs.getDouble("PR_SUPPLYPRICE"), rs.getInt("PR_PROQUANTITY"), 
							rs.getDouble("PR_MARKUP"), rs.getDouble("PR_RETAILPRICE"),
							rs.getString("PR_INSERTDATE"), rs.getString("PR_MODIFYDATE"));
				}else{
					return null;
				}
				
				return pr;
			}
			

		});
	}

	@Override
	public long getProductTypeID(String TypeName, long EnterprisedID) {
		// TODO Auto-generated method stub
		String sql= " SELECT PT_ID " 
			    +	" FROM [POS_TBLPRODUCTTYPE] WHERE [ENTERPRISEID] = " + EnterprisedID
				+   " AND LOWER([PT_NAME]) = '" + TypeName.toLowerCase()+"'";
		System.out.println(sql);
		long ProductTypeId = 0;
		try{
			ProductTypeId = this.getJdbcTemplate().queryForLong(sql);
		}catch(EmptyResultDataAccessException e ){
			ProductTypeId = 0;
		}
		return ProductTypeId;
	}

	@Override
	public long getSupplierID(String SupplierCode, long EnterprisedID) {
		// TODO Auto-generated method stub
		String sql= " SELECT SUP_ID " 
			    +	" FROM [POS_TBLSUPPLIERS] WHERE [ENTERPRISEID] = " + EnterprisedID
				+   " AND LOWER([SUP_CODE]) = '" + SupplierCode.toLowerCase()+"'";
		System.out.println(sql);
		long SupplierID = 0;
		try{
			SupplierID = this.getJdbcTemplate().queryForLong(sql);
		}catch(EmptyResultDataAccessException e ){
			SupplierID = 0;
		}
		
		return SupplierID;
	}

	@Override
	public int importproductdata(ArrayList<Product> ProductList) {
		// TODO Auto-generated method stub
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		int result = 0;
		try {
			for(int i=0;i<ProductList.size();i++){
				Product product = (Product)ProductList.get(i);
				String sql = "INSERT INTO [dbo].[POS_TBLPRODUCTS]([ENTERPRISEID],[PR_NAME],[PR_CODE],"
						+    "[PR_PRODUCTTYPEID],[PR_SUPPLIERID],[PR_SUPPLYPRICE],[PR_PROQUANTITY],"
						+    "[PR_MARKUP],[PR_RETAILPRICE],[PR_INSERTDATE],[PR_MODIFYDATE])"
						+    "values ("+product.getEnterprisedID()+",'"+product.getProductName()+"','"+product.getProductCode()+"',"+
							 product.getProductTypeID()+","+product.getSupplierID()+","+product.getSupplyPrice()+","+
							 product.getProductQuantity()+","+product.getProductMarkUp()+","+product.getRetailPrice()+","+
						     "GETDATE(),GETDATE())";
				
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
