package com.soft.pharmacy.model;

public class Enterprise {

	int EnterpriseID;
	String EnterpriseName;
	String LoginName;

	String Address;
	String City;
	String Country;
	String State;
	String ZipCode;
	String Phone;
	String Fax;
	String Email;
	String Cell;
	String Website;
	String Brand;
	
	String ExpiryDate;
	String Message;
	String Response;

	String InsertDate;

	String ModifyDate;
	int isDemo;

	public Enterprise() {
		this.EnterpriseID = 0;
		this.EnterpriseName = "";
		this.Address = "";
		this.City = "";
		this.Country = "";
		this.State = "";
		this.ZipCode = "";
		this.Phone = "";
		this.Fax = "";
		this.Email = "";
		this.Cell = "";
		this.Website = "";
		this.Brand = "";
		this.ExpiryDate = "";
		this.Message = "";
		this.Response = "";
		this.InsertDate = "";
		this.ModifyDate = "";
		this.isDemo = 0;
	}

	public Enterprise(int EnterpriseID, String EnterpriseName, String Address,
			String City, String Country, String State, String ZipCode,
			String Phone, String Fax, String Email, String Cell,
			String Website, String Brand, String LoginName,String expdate,
			String message,String InsertDate,  String ModifyDate,String response) {
		this.EnterpriseID = EnterpriseID;
		if (EnterpriseName == null)
			EnterpriseName = "";
		this.EnterpriseName = EnterpriseName;

		if (Address == null)
			Address = "";
		this.Address = Address;

		if (City == null)
			City = "";
		this.City = City;
		if (Country == null)
			Country = "";
		this.Country = Country;
		if (State == null)
			State = "";
		this.State = State;
		if (ZipCode == null)
			ZipCode = "";
		this.ZipCode = ZipCode;
		if (Phone == null)
			Phone = "";
		this.Phone = Phone;
		if (Fax == null)
			Fax = "";
		this.Fax = Fax;
		if (Email == null)
			Email = "";
		this.Email = Email;
		if (Cell == null)
			Cell = "";
		this.Cell = Cell;
		if (Website == null)
			Website = "";
		this.Website = Website;
		if (Brand == null)
			Brand = "";
		this.Brand = Brand;
		if (LoginName == null)
			LoginName = "";
		this.LoginName = LoginName;

		if (expdate == null)
			expdate = "";
		this.ExpiryDate = expdate;

		if (message == null)
			message = "";
		this.Message = message;

		if (response == null)
			response = "";
		this.Response = response;

		// Message

		if (ModifyDate == null)
			ModifyDate = "";
		this.ModifyDate = ModifyDate;

		this.isDemo = 0;
	}

	public int getEnterpriseID() {
		return EnterpriseID;
	}

	public void setEnterpriseID(int EnterpriseID) {
		this.EnterpriseID = EnterpriseID;
	}

	public int getIsDemo() {
		return isDemo;
	}

	public void setIsDemo(int isDemo) {
		this.isDemo = isDemo;
	}

	public String getEnterpriseName() {
		return EnterpriseName;
	}

	public void setEnterpriseName(String EnterpriseName) {
		if (EnterpriseName == null)
			EnterpriseName = "";
		this.EnterpriseName = EnterpriseName;
	}

	public String getLoginName() {
		return LoginName;
	}

	public void setLoginName(String LoginName) {
		if (LoginName == null)
			LoginName = "";
		this.LoginName = LoginName;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String Address) {
		if (Address == null)
			Address = "";
		this.Address = Address;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String City) {
		if (City == null)
			City = "";
		this.City = City;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String Country) {
		if (Country == null)
			Country = "";
		this.Country = Country;
	}

	public String getState() {
		return State;
	}

	public void setState(String State) {
		if (State == null)
			State = "";
		this.State = State;
	}

	public String getZipCode() {
		return ZipCode;
	}

	public void setZipCode(String ZipCode) {
		if (ZipCode == null)
			ZipCode = "";
		this.ZipCode = ZipCode;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String Phone) {
		if (Phone == null)
			Phone = "";
		this.Phone = Phone;
	}

	public String getFax() {
		return Fax;
	}

	public void setFax(String Fax) {
		if (Fax == null)
			Fax = "";
		this.Fax = Fax;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String Email) {
		if (Email == null)
			Email = "";
		this.Email = Email;
	}

	public String getCell() {
		return Cell;
	}

	public void setCell(String Cell) {
		if (Cell == null)
			Cell = "";
		this.Cell = Cell;
	}

	public String getWebsite() {
		return Website;
	}

	public void setWebsite(String Website) {
		if (Website == null)
			Website = "";
		this.Website = Website;
	}

	public String getBrand() {
		return Brand;
	}

	public void setBrand(String Brand) {
		if (Brand == null)
			Brand = "";
		this.Brand = Brand;
	}

	public String getExpiryDate() {
		return this.ExpiryDate;
	}

	public void setExpiryDate(String ExpiryDate) {
		if (ExpiryDate == null || ExpiryDate.length() == 0)
			ExpiryDate = "";
		this.ExpiryDate = ExpiryDate;
	}

	// Message
	public String getMessage() {
		return this.Message;
	}

	public void setMessage(String Message) {
		if (Message == null || Message.length() == 0)
			Message = "";
		this.Message = Message;
	}

	public String getResponse() {
		return this.Response;
	}

	public void setResponse(String Response) {
		if (Response == null || Response.length() == 0)
			Response = "";
		this.Response = Response;
	}

	public String getModifyDate() {
		return this.ModifyDate;
	}

	public void setModifyDate(String date) {
		if (date == null)
			date = "";
		this.ModifyDate = date;
	}
}
