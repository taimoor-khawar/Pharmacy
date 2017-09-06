package com.soft.pharmacy.dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.PlatformTransactionManager;

import com.soft.pharmacy.dao.ReportDAO;
import com.soft.pharmacy.model.Bill;
import com.soft.pharmacy.model.Purchase;

public class ReportDAOImpl extends JdbcDaoSupport implements ReportDAO {

	private static final Logger logger = Logger.getLogger(ReportDAOImpl.class);
	// private JdbcTemplate jdbcTemplateObject;
	private PlatformTransactionManager transactionManager;

	// private DataSource dataSource;

	@Autowired
	public ReportDAOImpl(DataSource dataSource) {
		this.setDataSource(dataSource);

	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	@Override
	public ArrayList<Bill> getBillsByCustomerID(long CustomerID,
			long EnterprisedID,String FromDate,String ToDate) {
		// TODO Auto-generated method stub
		String sql= " SELECT  [BILL_ID],bill.[ENTERPRISEID],[BILL_CUSID],[BILL_NAME],[BILL_AMOUNT],"
				  + " [BILL_DISCOUNT],[BILL_DUEAMOUNT],[BILL_INSERTDATE],[BILL_MODIFYDATE],"
				  + " cus.[CUS_FNAME],cus.[CUS_LNAME]" 
			      +	" FROM [POS_TBLBILL] bill "
			      +	" INNER JOIN [POS_TBLCUSTOMERS] cus ON cus.CUS_ID = bill.[BILL_CUSID] "
			      + " WHERE 1=1 ";
		if(CustomerID > 0)
			sql+=" and bill.[BILL_CUSID]="+CustomerID;
		if(EnterprisedID > 0)
			sql+=" and bill.[ENTERPRISEID] = " + EnterprisedID;
		if(FromDate.length() > 0)
			sql+=" and bill.[BILL_INSERTDATE] >= CONVERT(Date,'" + FromDate+"')";
		if(ToDate.length() > 0)
			sql+=" and bill.[BILL_INSERTDATE] < CONVERT(Date,'" + ToDate+"')";
			  
		sql+= " ORDER BY [BILL_ID] ASC ";
		
		System.out.println(sql);
		return (ArrayList<Bill>) this.getJdbcTemplate().query(sql, new RowMapper<Bill>(){
			@Override
			public Bill mapRow(ResultSet rs, int row)
					throws SQLException {
				Bill bill = new Bill(rs.getLong("BILL_ID"),rs.getLong("ENTERPRISEID"),rs.getLong("BILL_CUSID"),rs.getString("BILL_NAME")
						,rs.getDouble("BILL_AMOUNT"),rs.getDouble("BILL_DISCOUNT"),rs.getDouble("BILL_DUEAMOUNT"),
						rs.getString("BILL_INSERTDATE"),rs.getString("BILL_MODIFYDATE"));
				bill.setCustomerName(rs.getString("CUS_FNAME")+rs.getString("CUS_LNAME"));
				return bill;
			}

		});
	}

	@Override
	public ArrayList<Purchase> getPurchasesByProduct(long ProductID,
			long EnterprisedID, String FromDate, String ToDate) {
		// TODO Auto-generated method stub
		String sql= " SELECT [PUR_ID],pr.[ENTERPRISEID],[PUR_PROID],[PUR_SUPID],[PUR_Quantity],"
				  + " [PUR_OLDQuantity],[PUR_INSERTDATE],[PUR_MODIFYDATE],[PUR_BUYINGPRICE],"
				  + " pro.[PR_NAME],sup.[SUP_FNAME],sup.[SUP_LNAME]" 
			      +	" FROM [POS_TBLPURCHASE] pr "
			      +	" INNER JOIN [POS_TBLPRODUCTS] pro ON pro.[PR_ID] = pr.[PUR_PROID] "
			      +	" INNER JOIN [POS_TBLSUPPLIERS] sup ON sup.[SUP_ID] = pr.[PUR_SUPID] "
			      + " WHERE 1 = 1 ";
		
		if(ProductID > 0)
			sql+=" and pr.[PUR_PROID]="+ProductID;
		if(EnterprisedID > 0)
			sql+=" and pr.[ENTERPRISEID] = " + EnterprisedID;
		if(FromDate.length() > 0)
			sql+=" and pr.[PUR_INSERTDATE] >= CONVERT(Date,'" + FromDate+"')";
		if(ToDate.length() > 0)
			sql+=" and pr.[PUR_INSERTDATE] < CONVERT(Date,'" + ToDate+"')";
			  
		sql+= " ORDER BY [PUR_ID] ASC ";
		
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

}
