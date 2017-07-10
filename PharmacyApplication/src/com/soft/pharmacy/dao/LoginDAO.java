package com.soft.pharmacy.dao;


import javax.servlet.http.HttpServletRequest;

import com.soft.pharmacy.model.Department;
import com.soft.pharmacy.model.SysUser;

public interface LoginDAO {

	int save(Department department);

	SysUser getLoginByUsernameaDMIN(String USERNAME, String Password,
			HttpServletRequest request);
}
