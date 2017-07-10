package com.soft.pharmacy.model;

public class SysUser {
	int sysUserID;
	int EnterpriseID;
	String sysLogin;
	String sysPassword;
	String sysUserName;
	String email;
	String EnterpriseName;
	int isSuperAdmin;
	int SU_ISMANAGER;

	String REG_MODIFYDATE;

	String REG_INSERTDATE;

	String REG_BYUSERIPM;

	public SysUser() {

		sysUserID = 0;
		EnterpriseID = 0;
		sysLogin = "";
		sysPassword = "";
		sysUserName = "";
		email = "";
		isSuperAdmin = 0;
		SU_ISMANAGER = 0;

		EnterpriseName = "";
		REG_MODIFYDATE = "";

		REG_INSERTDATE = "";

		REG_BYUSERIPM = "";

	}

	public SysUser(int sysUserID, int EnterpriseID, String sysLogin,
			String sysPassword, String sysUserName, String email,
			int issuperadmin, int SU_ISMANAGER,

			String REG_MODIFYDATE,

			String REG_INSERTDATE) {

		this.sysUserID = sysUserID;
		this.EnterpriseID = EnterpriseID;
		if (sysLogin == null)
			sysLogin = "";
		this.sysLogin = sysLogin;
		this.sysPassword = sysPassword;
		if (sysUserName == null)
			sysUserName = "";
		this.sysUserName = sysUserName;
		if (email == null)
			email = "";
		this.email = email;
		this.isSuperAdmin = issuperadmin;
		this.SU_ISMANAGER = SU_ISMANAGER;

		this.EnterpriseName = "";
		this.REG_MODIFYDATE = REG_MODIFYDATE;

		this.REG_INSERTDATE = REG_INSERTDATE;

	}

	public int getsysUserID() {
		return this.sysUserID;
	}

	public void setsysUserID(int sysUserID) {
		this.sysUserID = sysUserID;
	}

	public int getEnterpriseID() {
		return EnterpriseID;
	}

	public void setEnterpriseID(int EnterpriseID) {
		this.EnterpriseID = EnterpriseID;
	}

	public String getsysLogin() {
		return this.sysLogin;
	}

	public void setsysLogin(String sysLogin) {
		this.sysLogin = sysLogin;
	}

	public String getsysPassword() {
		return this.sysPassword;
	}

	public void setsysPassword(String sysPassword) {
		this.sysPassword = sysPassword;
	}

	public String getsysUserName() {
		return this.sysUserName;
	}

	public void setsysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setemail(String email) {
		this.email = email;
	}

	public int getIsSuperAdmin() {
		return this.isSuperAdmin;
	}

	public void setIsSuperAdmin(int isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public String getREG_INSERTDATE() {
		return this.REG_INSERTDATE;
	}

	public void setREG_INSERTDATE(String REG_INSERTDATE) {
		if (REG_INSERTDATE == null)
			REG_INSERTDATE = "";
		this.REG_INSERTDATE = REG_INSERTDATE;
	}

	public String getREG_BYUSERIPM() {
		return this.REG_BYUSERIPM;
	}

	public void setREG_BYUSERIPM(String REG_BYUSERIPM) {
		if (REG_BYUSERIPM == null || REG_BYUSERIPM.length() == 0)
			REG_BYUSERIPM = "0";
		this.REG_BYUSERIPM = REG_BYUSERIPM;
	}

	public String getREG_MODIFYDATE() {
		return this.REG_MODIFYDATE;
	}

	public void setREG_MODIFYDATE(String REG_MODIFYDATE) {
		if (REG_MODIFYDATE == null)
			REG_MODIFYDATE = "";
		this.REG_MODIFYDATE = REG_MODIFYDATE;
	}

	public String getEnterpriseName() {
		return this.EnterpriseName;
	}

	public void setEnterpriseName(String EnterpriseName) {
		this.EnterpriseName = EnterpriseName;
	}
}
