package com.soft.pharmacy.model;


public class SysUserSession {
    int userID = 0;
    SysUser sysuser;
 
    public SysUserSession() {

    }

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public SysUser getSysuser() {
		return sysuser;
	}

	public void setSysuser(SysUser sysuser) {
		this.sysuser = sysuser;
	}

	
    
    
}

