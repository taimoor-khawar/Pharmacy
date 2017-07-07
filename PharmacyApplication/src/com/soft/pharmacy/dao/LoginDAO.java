package com.soft.pharmacy.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class LoginDAO {
	JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {  
		this.template = template;  
	}
	
	public int save(){
		return template.queryForInt("SELECT Position FROM tblGoodContestant WHERE SeriesNumber = 2");
	}
}
