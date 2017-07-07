package com.soft.pharmacy.dao.Impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.soft.pharmacy.dao.LoginDAO;
import com.soft.pharmacy.model.Department;

@Transactional
public class LoginDAOImpl extends JdbcDaoSupport implements LoginDAO {

	@Autowired
	
    public LoginDAOImpl(DataSource dataSource) {
        this.setDataSource(dataSource);
    }
	
	@Override
	public int save(Department department) {
		String sql = "Insert into Department (dept_id,dept_no,dept_name,location) "//
                + " values (?,?,?,?) ";
        int deptId = this.getMaxDeptId() + 1;
        String deptNo = "D" + deptId;
        Object[] params = new Object[] { deptId, deptNo, department.getDeptName(), department.getLocation() };
        return this.getJdbcTemplate().update(sql, params); 
		//return 0;
	}
	
	private int getMaxDeptId() {
        String sql = "Select max(d.dept_id) from Department d";
 
        Integer value = this.getJdbcTemplate().queryForObject(sql, Integer.class);
        if (value == null) {
            return 0;
        }
        return value;
    }

}
