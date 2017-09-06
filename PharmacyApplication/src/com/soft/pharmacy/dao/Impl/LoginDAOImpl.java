package com.soft.pharmacy.dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.soft.pharmacy.dao.LoginDAO;
import com.soft.pharmacy.model.Department;
import com.soft.pharmacy.model.SysUser;
import com.soft.pharmacy.model.SysUserSession;

public class LoginDAOImpl extends JdbcDaoSupport implements LoginDAO {

	private static final Logger logger = Logger.getLogger(LoginDAOImpl.class);
	// private JdbcTemplate jdbcTemplateObject;
	private PlatformTransactionManager transactionManager;

	// private DataSource dataSource;

	@Autowired
	public LoginDAOImpl(DataSource dataSource) {
		this.setDataSource(dataSource);

	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	@Override
	public int save(Department department) {
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		try {

			String sql = "Insert into Departmentt (dept_id,dept_no,dept_name,location) "//
					+ " values (?,?,?,?) ";
			int deptId = this.getMaxDeptId() + 1;
			String deptNo = "D" + deptId;
			Object[] params = new Object[] { deptId, deptNo,
					department.getDeptName(), department.getLocation() };
			this.getJdbcTemplate().update(sql, params);
			transactionManager.commit(status);
		} catch (DataAccessException e) {
			logger.debug("Error in creating record, rolling back");
			transactionManager.rollback(status);
			throw e;
		}
		return 0;
	}

	private int getMaxDeptId() {
		String sql = "Select max(d.dept_id) from Department d";

		Integer value = this.getJdbcTemplate().queryForObject(sql,
				Integer.class);
		if (value == null) {
			return 0;
		}
		return value;
	}

	@Override
	public SysUser getLoginByUsernameaDMIN(String USERNAME, String Password,
			final HttpServletRequest request) {
		String sql = " SELECT *  FROM SS_TBLSYSUSERS "
				+ " WHERE lower(SU_SYSLOGIN) = '" + USERNAME.toLowerCase() + "'"
				+ " and lower(SU_SYSPASSWORD) = '" + Password.toLowerCase() + "'";
		logger.debug(sql);
		return (SysUser) this.getJdbcTemplate().query(sql,
				new ResultSetExtractor<SysUser>() {
					public SysUser extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						SysUser sysuser = new SysUser();
						SysUserSession sus = null;
						boolean result = rs.next();
						if (result) {
							while (result) {
								try {
									sysuser = new SysUser(rs
											.getInt("SU_SYSUSERID"), rs
											.getInt("ENTERPRISEID"), rs
											.getString("SU_SYSLOGIN"), rs
											.getString("SU_SYSPASSWORD"), rs
											.getString("SU_SYSUSERNAME"), rs
											.getString("SU_SYSUSEREMAIL"), rs
											.getInt("SU_ISSUPERADMIN"), rs
											.getInt("SU_ISMANAGER"), rs
											.getString("REG_MODIFYDATE"), rs
											.getString("REG_INSERTDATE"));
									logger.debug(sysuser.getsysUserID());
									sus = getSysUserSession(sysuser);
									HttpSession session = request
											.getSession(true);
									session.setAttribute(session.getId(), sus);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								result = rs.next();
							}
						} else {
							return null;
						}

						return sysuser;
					}
				});
	}

	public SysUserSession getSysUserSession(SysUser sysuser)
			throws SQLException, Exception {

		SysUserSession us = new SysUserSession();

		try {
			us.setSysuser(sysuser);
			us.setUserID(sysuser.getsysUserID());
		} catch (Exception e) {
			throw e;
		} finally {
			try {

			} catch (Exception ex1) {
			}
		}

		return us;
	}

}
