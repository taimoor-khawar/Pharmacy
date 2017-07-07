package com.soft.pharmacy.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.soft.pharmacy.model.Department;

public interface LoginDAO {

	int save(Department department);
}
