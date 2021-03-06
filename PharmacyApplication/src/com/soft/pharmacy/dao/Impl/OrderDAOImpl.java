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

import com.soft.pharmacy.dao.OrderDAO;
import com.soft.pharmacy.model.Bill;
import com.soft.pharmacy.model.Billline;
import com.soft.pharmacy.model.Purchase;

public class OrderDAOImpl extends JdbcDaoSupport implements OrderDAO {

	
	private static final Logger logger = Logger.getLogger(OrderDAOImpl.class);
	// private JdbcTemplate jdbcTemplateObject;
	private PlatformTransactionManager transactionManager;

	// private DataSource dataSource;

	@Autowired
	public OrderDAOImpl(DataSource dataSource) {
		this.setDataSource(dataSource);

	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	
	//------------------------------------------Bill-----------------------------------
	@Override
	public long getBillID() {
		// TODO Auto-generated method stub
		String sql= " SELECT isnull(MAX(BILL_ID),0)+1 from POS_TBLBILL ";
		System.out.println(sql);
		long BillID = 0;
		try{
			BillID = this.getJdbcTemplate().queryForLong(sql);
		}catch(EmptyResultDataAccessException e ){
			BillID = 0;
		}
		
		return BillID;
	}

	@Override
	public int saveBill(Bill bill) {
		// TODO Auto-generated method stub
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		int result = 0;
		try {

			String sql = "INSERT INTO [dbo].[POS_TBLBILL] ([ENTERPRISEID],[BILL_CUSID],"
					   + "[BILL_AMOUNT],[BILL_DISCOUNT],[BILL_DUEAMOUNT],[BILL_INSERTDATE],[BILL_MODIFYDATE],[BILL_NAME])"
					   + " values ("+bill.getEnterprisedID()+","+bill.getCustomerID()+","+bill.getBillAmount()+","
					   +bill.getBillDiscount()+","+bill.getBillDueAmount()+",GETDATE(),GETDATE(),'"+bill.getBillName()+"')";
			
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
	public int updateBill(Bill bill) {
		// TODO Auto-generated method stub
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
		String sql = " UPDATE [dbo].[POS_TBLBILL] "
				   + " SET [BILL_CUSID] = "+bill.getCustomerID()+",[BILL_AMOUNT] = " + bill.getBillAmount()+","
				   + " [BILL_DISCOUNT]="+bill.getBillDiscount()+","
				   + " [BILL_DUEAMOUNT]="+bill.getBillDueAmount()+","
				   + " [BILL_MODIFYDATE]=GETDATE()"
				   + " WHERE [ENTERPRISEID] = "+bill.getEnterprisedID()
				   + " and [BILL_ID] = " + bill.getBillID();
		
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
	public int deleteBill(long Billid, long EnterprisedID) {
		// TODO Auto-generated method stub
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
		String sql = " DELETE FROM [dbo].[POS_TBLBILL] "
				   + " WHERE [ENTERPRISEID] = "+EnterprisedID
				   + " and [BILL_ID] = " + Billid;
		
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
	public ArrayList<Bill> getBills(long EnterprisedID) {
		// TODO Auto-generated method stub
		String sql= " SELECT  [BILL_ID],bill.[ENTERPRISEID],[BILL_CUSID],[BILL_NAME],[BILL_AMOUNT],"
				  + " [BILL_DISCOUNT],[BILL_DUEAMOUNT],[BILL_INSERTDATE],[BILL_MODIFYDATE],"
				  + " cus.[CUS_FNAME],cus.[CUS_LNAME],cus.[CUS_ADDRESS1]" 
			      +	" FROM [POS_TBLBILL] bill "
			      +	" INNER JOIN [POS_TBLCUSTOMERS] cus ON cus.CUS_ID = bill.[BILL_CUSID] "
			      + " WHERE bill.[ENTERPRISEID] = " + EnterprisedID
			      +	" ORDER BY [BILL_ID] ASC ";
		System.out.println(sql);
		return (ArrayList<Bill>) this.getJdbcTemplate().query(sql, new RowMapper<Bill>(){
			@Override
			public Bill mapRow(ResultSet rs, int row)
					throws SQLException {
				Bill bill = new Bill(rs.getLong("BILL_ID"),rs.getLong("ENTERPRISEID"),rs.getLong("BILL_CUSID"),rs.getString("BILL_NAME")
						,rs.getDouble("BILL_AMOUNT"),rs.getDouble("BILL_DISCOUNT"),rs.getDouble("BILL_DUEAMOUNT"),
						rs.getString("BILL_INSERTDATE"),rs.getString("BILL_MODIFYDATE"));
				bill.setCustomerName(rs.getString("CUS_FNAME")+rs.getString("CUS_LNAME"));
				bill.setCustomerAddress(rs.getString("CUS_ADDRESS1"));
				return bill;
			}

		});
	}

	@Override
	public Bill getBill(long Billid, long EnterprisedID) {
		// TODO Auto-generated method stub
		String sql= " SELECT  [BILL_ID],bill.[ENTERPRISEID],[BILL_CUSID],[BILL_NAME],[BILL_AMOUNT],"
				  + " [BILL_DISCOUNT],[BILL_DUEAMOUNT],[BILL_INSERTDATE],[BILL_MODIFYDATE],"
				  + " cus.[CUS_FNAME],cus.[CUS_LNAME],cus.[CUS_ADDRESS1]" 
			      +	" FROM [POS_TBLBILL] bill "
			      +	" INNER JOIN [POS_TBLCUSTOMERS] cus ON cus.CUS_ID = bill.[BILL_CUSID] "
			      + " WHERE bill.[ENTERPRISEID] = " + EnterprisedID
				+   " AND [BILL_ID] = " + Billid;
		System.out.println(sql);
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<Bill>() {
			@Override
			public Bill extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				Bill bill = new Bill();
				if(rs.next()){
					bill = new Bill(rs.getLong("BILL_ID"),rs.getLong("ENTERPRISEID"),rs.getLong("BILL_CUSID"),rs.getString("BILL_NAME")
							,rs.getDouble("BILL_AMOUNT"),rs.getDouble("BILL_DISCOUNT"),rs.getDouble("BILL_DUEAMOUNT"),
							rs.getString("BILL_INSERTDATE"),rs.getString("BILL_MODIFYDATE"));
					bill.setCustomerName(rs.getString("CUS_FNAME")+rs.getString("CUS_LNAME"));
					bill.setCustomerAddress(rs.getString("CUS_ADDRESS1"));
					
				}else{
					return null;
				}
				
				return bill;
			}
			

		});
	}

	
	
	/////////////////////////Bill- Line ////////////////////////////////////
	@Override
	public int saveBillline(Billline billline) {
		// TODO Auto-generated method stub
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		int result = 0;
		try {

			String sql = " INSERT INTO [dbo].[POS_TBLBILLLINE] ([ENTERPRISEID],[BL_BILLID],"
					   + " [BL_PROID],[BL_AMOUNT],[BL_DISCOUNT],[BL_DUEAMOUNT],[BL_INSERTDATE],"
					   + " [BL_MODIFYDATE],[BL_PROQuantity],[BL_PROPrice])"
					   + " values ("+billline.getEnterprisedID()+","+billline.getBillID()+","+billline.getProductID()+","+
					   				 billline.getBilllineAmount()+","+billline.getBilllineDiscount()+","+
					                 billline.getBilllineDueAmount()+",GETDATE(),GETDATE(),"+billline.getProductQuantity()+","+billline.getProductAmount()+")";
			
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
	public int updateBillline(Billline billline) {
		// TODO Auto-generated method stub
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
		String sql = " UPDATE [dbo].[POS_TBLBILLLINE] "
				   + " SET [BL_AMOUNT] = " + billline.getBilllineAmount()+","
				   + " [BL_DISCOUNT]="+billline.getBilllineDiscount()+","
				   + " [BL_DUEAMOUNT]="+billline.getBilllineDueAmount()+","
				   + " [BL_MODIFYDATE]=GETDATE(),"
				   + " [BL_PROQuantity]="+billline.getProductQuantity()+","
				   + " [BL_PROPrice]="+billline.getProductAmount()
				   + " WHERE [ENTERPRISEID] = "+billline.getEnterprisedID()
				   + " and [BL_ID] = " + billline.getBilllineID();
		
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
	public int deleteBillline(long Billlineid, long EnterprisedID) {
		// TODO Auto-generated method stub
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
		String sql = " DELETE FROM [dbo].[POS_TBLBILLLINE] "
				   + " WHERE [ENTERPRISEID] = "+EnterprisedID
				   + " and [BL_ID] = " + Billlineid;
		
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
	public ArrayList<Billline> getBilllines(long EnterprisedID) {
		// TODO Auto-generated method stub
		String sql= " SELECT [BL_ID],[ENTERPRISEID],[BL_BILLID],[BL_PROID],[BL_AMOUNT],"
				  + " [BL_DISCOUNT],[BL_DUEAMOUNT],[BL_INSERTDATE],[BL_MODIFYDATE],[BL_PROPrice],"
				  + " [BL_PROQuantity],pro.[PR_NAME]" 
			      +	" FROM [POS_TBLBILLLINE] billline "
			      +	" INNER JOIN [POS_TBLPRODUCTS] pro ON pro.[PR_ID] = billline.[BL_PROID] "
			      + " WHERE [ENTERPRISEID] = " + EnterprisedID;
		System.out.println(sql);
		return (ArrayList<Billline>) this.getJdbcTemplate().query(sql, new RowMapper<Billline>(){
			@Override
			public Billline mapRow(ResultSet rs, int row)
					throws SQLException {
				Billline billline = new Billline(rs.getLong("BL_ID"),rs.getLong("ENTERPRISEID"),rs.getLong("BL_BILLID"),
						rs.getLong("BL_PROID"),rs.getInt("BL_PROQuantity"),rs.getDouble("BL_PROPrice"),rs.getDouble("BL_AMOUNT"),rs.getDouble("BL_DISCOUNT"),
						rs.getDouble("BL_DUEAMOUNT"),rs.getString("BL_INSERTDATE"),rs.getString("BL_MODIFYDATE"));
				billline.setProductName(rs.getString("PR_NAME"));
				return billline;
			}

		});
	}

	@Override
	public Billline getBillline(long Billlineid, long EnterprisedID) {
		// TODO Auto-generated method stub
		String sql= " SELECT [BL_ID],[ENTERPRISEID],[BL_BILLID],[BL_PROID],[BL_AMOUNT],"
				  + " [BL_DISCOUNT],[BL_DUEAMOUNT],[BL_INSERTDATE],[BL_MODIFYDATE],[BL_PROPrice],"
				  + " [BL_PROQuantity],pro.[PR_NAME]" 
			      +	" FROM [POS_TBLBILLLINE] billline "
			      +	" INNER JOIN [POS_TBLPRODUCTS] pro ON pro.[PR_ID] = billline.[BL_PROID] "
			      + " WHERE [ENTERPRISEID] = " + EnterprisedID
				  + " AND [BL_ID] = " + Billlineid;
		System.out.println(sql);
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<Billline>() {
			@Override
			public Billline extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				Billline billline = new Billline();
				if(rs.next()){
					billline = new Billline(rs.getLong("BL_ID"),rs.getLong("ENTERPRISEID"),rs.getLong("BL_BILLID"),
							rs.getLong("BL_PROID"),rs.getInt("BL_PROQuantity"),rs.getDouble("BL_PROPrice"),rs.getDouble("BL_AMOUNT"),
							rs.getDouble("BL_DISCOUNT"),rs.getDouble("BL_DUEAMOUNT"),rs.getString("BL_INSERTDATE"),rs.getString("BL_MODIFYDATE"));
					billline.setProductName(rs.getString("PR_NAME"));
				}else{
					return null;
				}
				
				return billline;
			}
			

		});
	}
	
	
	//------------------------------------------Order-----------------------------------
	@Override
	public int createOrder(Bill bill, ArrayList<Billline> billines) {
		// TODO Auto-generated method stub
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		String sql = "";
		try {
			
			for(int i=0;i<billines.size();i++){
				Billline billline = (Billline)billines.get(i);
				sql = " INSERT INTO [dbo].[POS_TBLBILLLINE] ([ENTERPRISEID],[BL_BILLID],"
						   + " [BL_PROID],[BL_AMOUNT],[BL_DISCOUNT],[BL_DUEAMOUNT],[BL_INSERTDATE],"
						   + " [BL_MODIFYDATE],[BL_PROQuantity],[BL_PROPrice])"
						   + " values ("+billline.getEnterprisedID()+","+billline.getBillID()+","+billline.getProductID()+","+
						   				 billline.getBilllineAmount()+","+billline.getBilllineDiscount()+","+
						                 billline.getBilllineDueAmount()+",GETDATE(),GETDATE(),"+billline.getProductQuantity()+","+billline.getProductAmount()+")";
				
				result = this.getJdbcTemplate().update(sql);
				logger.debug(sql);
			}
			
			sql = "INSERT INTO [dbo].[POS_TBLBILL] ([BILL_ID],[ENTERPRISEID],[BILL_CUSID],"
					   + "[BILL_AMOUNT],[BILL_DISCOUNT],[BILL_DUEAMOUNT],[BILL_INSERTDATE],[BILL_MODIFYDATE],[BILL_NAME])"
					   + " values ("+bill.getBillID()+","+bill.getEnterprisedID()+","+bill.getCustomerID()+","+bill.getBillAmount()+","
					   +bill.getBillDiscount()+","+bill.getBillDueAmount()+",GETDATE(),GETDATE(),'"+bill.getBillName()+"')";
			
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
	public int updateOrder(Bill bill, ArrayList<Billline> billines) {
		// TODO Auto-generated method stub
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		String sql = "";
		try {
			
			for(int i=0;i<billines.size();i++){
				Billline billline = (Billline)billines.get(i);
				sql = " UPDATE [dbo].[POS_TBLBILLLINE] "
						   + " SET [BL_AMOUNT] = " + billline.getBilllineAmount()+","
						   + " [BL_DISCOUNT]="+billline.getBilllineDiscount()+","
						   + " [BL_DUEAMOUNT]="+billline.getBilllineDueAmount()+","
						   + " [BL_MODIFYDATE]=GETDATE(),"
						   + " [BL_PROQuantity]="+billline.getProductQuantity()+","
						   + " [BL_PROPrice]="+billline.getProductAmount()
						   + " WHERE [ENTERPRISEID] = "+billline.getEnterprisedID()
						   + " and [BL_ID] = " + billline.getBilllineID();
				
				result = this.getJdbcTemplate().update(sql);
				logger.debug(sql);
			}
			
			sql = " UPDATE [dbo].[POS_TBLBILL] "
					   + " SET [BILL_CUSID] = "+bill.getCustomerID()+",[BILL_AMOUNT] = " + bill.getBillAmount()+","
					   + " [BILL_DISCOUNT]="+bill.getBillDiscount()+","
					   + " [BILL_DUEAMOUNT]="+bill.getBillDueAmount()+","
					   + " [BILL_MODIFYDATE]=GETDATE()"
					   + " WHERE [ENTERPRISEID] = "+bill.getEnterprisedID()
					   + " and [BILL_ID] = " + bill.getBillID();
			
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
	public int deleteOrder(long Billid, long EnterprisedID) {
		// TODO Auto-generated method stub
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		String sql = "";
		try {
		sql = " DELETE FROM [dbo].[POS_TBLBILL] "
			 + " WHERE [ENTERPRISEID] = "+EnterprisedID
			 + " and [BILL_ID] = " + Billid;
		
		result = this.getJdbcTemplate().update(sql);
		
		sql = " DELETE FROM [dbo].[POS_TBLBILLLINE] "
				   + " WHERE [ENTERPRISEID] = "+EnterprisedID
				   + " and [BL_BILLID] = " + Billid;
		
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
	public ArrayList<Billline> getBilllines(long BillID, long EnterprisedID) {
		// TODO Auto-generated method stub
		String sql= " SELECT [BL_ID],billline.[ENTERPRISEID],[BL_BILLID],[BL_PROID],[BL_AMOUNT],"
				  + " [BL_DISCOUNT],[BL_DUEAMOUNT],[BL_INSERTDATE],[BL_MODIFYDATE],[BL_PROPrice],"
				  + " [BL_PROQuantity],pro.[PR_NAME]" 
			      +	" FROM [POS_TBLBILLLINE] billline "
			      +	" INNER JOIN [POS_TBLPRODUCTS] pro ON pro.[PR_ID] = billline.[BL_PROID] "
			      + " WHERE billline.[ENTERPRISEID] = " + EnterprisedID
		          + " AND [BL_BILLID] = " + BillID;
		System.out.println(sql);
		return (ArrayList<Billline>) this.getJdbcTemplate().query(sql, new RowMapper<Billline>(){
			@Override
			public Billline mapRow(ResultSet rs, int row)
					throws SQLException {
				Billline billline = new Billline(rs.getLong("BL_ID"),rs.getLong("ENTERPRISEID"),rs.getLong("BL_BILLID"),
						rs.getLong("BL_PROID"),rs.getInt("BL_PROQuantity"),rs.getDouble("BL_PROPrice"),rs.getDouble("BL_AMOUNT"),rs.getDouble("BL_DISCOUNT"),
						rs.getDouble("BL_DUEAMOUNT"),rs.getString("BL_INSERTDATE"),rs.getString("BL_MODIFYDATE"));
				billline.setProductName(rs.getString("PR_NAME"));
				return billline;
			}

		});
	}
	
	
	//------------------------------------------Purchase-----------------------------------
	@Override
	public int savePurchaseOrder(Purchase pr) {
		// TODO Auto-generated method stub
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		int result = 0;
		try {
			double OldQuantity = 0;
			if(pr.getOLDQuantity() == 0)
				OldQuantity = pr.getQuantity();
			else
				OldQuantity = pr.getOLDQuantity();

			String sql = " INSERT INTO [dbo].[POS_TBLPURCHASE] ([ENTERPRISEID],[PUR_PROID],"
					   + " [PUR_SUPID],[PUR_Quantity],[PUR_OLDQuantity],[PUR_INSERTDATE],"
					   + " [PUR_MODIFYDATE],[PUR_BUYINGPRICE])"
					   + " values ("+pr.getEnterprisedID()+","+pr.getProductID()+","+
					   				pr.getSupplierID()+","+pr.getQuantity()+","+OldQuantity+","+
					                "GETDATE(),GETDATE(),"+pr.getBuyingPrice()+")";
			
			result = this.getJdbcTemplate().update(sql);
			
			sql = " UPDATE [dbo].[POS_TBLPRODUCTS] "
					   + " SET [PR_PROQUANTITY]= [PR_PROQUANTITY] + "+pr.getQuantity()+","
					   + " [PR_MODIFYDATE] = GETDATE()"
					   + " WHERE [ENTERPRISEID] = "+pr.getEnterprisedID()
					   + " and [PR_ID] = " + pr.getProductID();
			
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
	public int updatePurchaseOrder(Purchase pr) {
		// TODO Auto-generated method stub
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
		String sql = " UPDATE [dbo].[POS_TBLPURCHASE] "
				   + " SET [PUR_PROID] = " + pr.getProductID()+","
				   + " [PUR_SUPID]="+pr.getSupplierID()+","
				   + " [PUR_Quantity]="+pr.getQuantity()+","
				   + " [PUR_OLDQuantity]="+pr.getOLDQuantity()+","
				   + " [PUR_BUYINGPRICE]="+pr.getBuyingPrice()+","
				   + " [PUR_MODIFYDATE]=GETDATE()"
				   + " WHERE [ENTERPRISEID] = "+pr.getEnterprisedID()
				   + " and [PUR_ID] = " + pr.getPurchaseID();
		
		result = this.getJdbcTemplate().update(sql);
		
		sql = " UPDATE [dbo].[POS_TBLPRODUCTS] "
				   + " SET [PR_PROQUANTITY] = [PR_PROQUANTITY] + "+pr.getQuantity()+" - "+pr.getOLDQuantity()+","
				   + " [PR_MODIFYDATE] = GETDATE()"
				   + " WHERE [ENTERPRISEID] = "+pr.getEnterprisedID()
				   + " and [PR_ID] = " + pr.getProductID();
		
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
	public int deletePurchaseOrder(long Purchaseid, long EnterprisedID) {
		// TODO Auto-generated method stub
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
		String sql = " DELETE FROM [dbo].[POS_TBLPURCHASE] "
				 	+ " WHERE [ENTERPRISEID] = "+EnterprisedID
				    + " and [PUR_ID] = " + Purchaseid;
		
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
	public ArrayList<Purchase> getPurchaseOrders(long EnterprisedID) {
		// TODO Auto-generated method stub
		String sql= " SELECT [PUR_ID],pr.[ENTERPRISEID],[PUR_PROID],[PUR_SUPID],[PUR_Quantity],"
				  + " [PUR_OLDQuantity],[PUR_INSERTDATE],[PUR_MODIFYDATE],[PUR_BUYINGPRICE],"
				  + " pro.[PR_NAME],sup.[SUP_FNAME],sup.[SUP_LNAME]" 
			      +	" FROM [POS_TBLPURCHASE] pr "
			      +	" INNER JOIN [POS_TBLPRODUCTS] pro ON pro.[PR_ID] = pr.[PUR_PROID] "
			      +	" INNER JOIN [POS_TBLSUPPLIERS] sup ON sup.[SUP_ID] = pr.[PUR_SUPID] "
			      + " WHERE pr.[ENTERPRISEID] = " + EnterprisedID;
		System.out.println(sql);
		return (ArrayList<Purchase>) this.getJdbcTemplate().query(sql, new RowMapper<Purchase>(){
			@Override
			public Purchase mapRow(ResultSet rs, int row)
					throws SQLException {
				Purchase pr = new Purchase(rs.getLong("PUR_ID"),rs.getLong("ENTERPRISEID"),rs.getLong("PUR_PROID"),
						rs.getLong("PUR_SUPID"),rs.getDouble("PUR_Quantity"),rs.getDouble("PUR_OLDQuantity"),
						rs.getDouble("PUR_BUYINGPRICE"),rs.getString("PUR_INSERTDATE"),rs.getString("PUR_MODIFYDATE"));
				pr.setProductName(rs.getString("PR_NAME"));
				pr.setSupplierName(rs.getString("SUP_FNAME")+ " "+rs.getString("SUP_LNAME"));
				return pr;
			}

		});
	}

	@Override
	public Purchase getPurchaseOrder(long Purchaseid, long EnterprisedID) {
		// TODO Auto-generated method stub
		String sql= " SELECT [PUR_ID],pr.[ENTERPRISEID],[PUR_PROID],[PUR_SUPID],[PUR_Quantity],"
				  + " [PUR_OLDQuantity],[PUR_INSERTDATE],[PUR_MODIFYDATE],[PUR_BUYINGPRICE],"
				  + " pro.[PR_NAME],sup.[SUP_FNAME],sup.[SUP_LNAME]" 
			      +	" FROM [POS_TBLPURCHASE] pr "
			      +	" INNER JOIN [POS_TBLPRODUCTS] pro ON pro.[PR_ID] = pr.[PUR_PROID] "
			      +	" INNER JOIN [POS_TBLSUPPLIERS] sup ON sup.[SUP_ID] = pr.[PUR_SUPID] "
			      + " WHERE pr.[ENTERPRISEID] = " + EnterprisedID
				  + " AND [PUR_ID] = " + Purchaseid;
		System.out.println(sql);
		return this.getJdbcTemplate().query(sql, new ResultSetExtractor<Purchase>() {
			@Override
			public Purchase extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				Purchase pr = new Purchase();
				if(rs.next()){
					pr = new Purchase(rs.getLong("PUR_ID"),rs.getLong("ENTERPRISEID"),rs.getLong("PUR_PROID"),
							rs.getLong("PUR_SUPID"),rs.getDouble("PUR_Quantity"),rs.getDouble("PUR_OLDQuantity"),
							rs.getDouble("PUR_BUYINGPRICE"),rs.getString("PUR_INSERTDATE"),rs.getString("PUR_MODIFYDATE"));
					pr.setProductName(rs.getString("PR_NAME"));
					pr.setSupplierName(rs.getString("SUP_FNAME")+ " "+rs.getString("SUP_LNAME"));
					
				}else{
					return null;
				}
				
				return pr;
			}
			

		});
	}

}
