package com.soft.pharmacy.model;

public class Customer {

	long CustomerID;
	long EnterpriseID;
	String FirstName;
	String LastName;
	String Email;
	String PhoneNumber;
	String Address1;
	String Address2;
	String State;
	String Zip;
	String Country;
	String Comments;
	String InsertionDate;
	String ModificationDate;

	public Customer() {
		this.CustomerID = 0;
		this.EnterpriseID = 0;
		this.FirstName = "";
		this.LastName = "";
		this.Email = "";
		this.PhoneNumber = "";
		this.Address1 = "";
		this.Address2 = "";
		this.State = "";
		this.Zip = "";
		this.Country = "";
		this.Comments = "";
		this.InsertionDate = "";
		this.ModificationDate = "";
	}

	public Customer(long CustomerID,long EnterpriseID, String FirstName, String LastName,
			String Email, String PhoneNumber, String Address1, String Address2,
			String State, String Zip, String Country, String Comments,
			String InsertionDate, String ModificationDate) {
		this.CustomerID = CustomerID;
		this.EnterpriseID = EnterpriseID;
		if (FirstName == null)
			FirstName = "";
		this.FirstName = FirstName;

		if (LastName == null)
			LastName = "";
		this.LastName = LastName;

		if (Email == null)
			Email = "";
		this.Email = Email;

		if (PhoneNumber == null)
			PhoneNumber = "";
		this.PhoneNumber = PhoneNumber;

		if (Address1 == null)
			Address1 = "";
		this.Address1 = Address1;

		if (Address2 == null)
			Address2 = "";
		this.Address2 = Address2;

		if (State == null)
			State = "";
		this.State = State;

		if (Zip == null)
			Zip = "";
		this.Zip = Zip;

		if (Country == null)
			Country = "";
		this.Country = Country;

		if (Comments == null)
			Comments = "";
		this.Comments = Comments;

		if (InsertionDate == null)
			InsertionDate = "";
		this.InsertionDate = InsertionDate;

		if (ModificationDate == null)
			ModificationDate = "";
		this.ModificationDate = ModificationDate;
	}

	public long getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(long customerID) {
		CustomerID = customerID;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		if (firstName == null)firstName = "";
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		if (lastName == null)lastName = "";
		LastName = lastName;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		if (email == null)email = "";
		Email = email;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		if (phoneNumber == null)phoneNumber = "";
		PhoneNumber = phoneNumber;
	}

	public String getAddress1() {
		return Address1;
	}

	public void setAddress1(String address1) {
		if (address1 == null)address1 = "";
		Address1 = address1;
	}

	public String getAddress2() {
		return Address2;
	}

	public void setAddress2(String address2) {
		if (address2 == null)address2 = "";
		Address2 = address2;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		if (state == null)state = "";
		State = state;
	}

	public String getZip() {
		return Zip;
	}

	public void setZip(String zip) {
		if (zip == null)zip = "";
		Zip = zip;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		if (country == null)country = "";
		Country = country;
	}

	public String getComments() {
		return Comments;
	}

	public void setComments(String comments) {
		if (comments == null)comments = "";
		Comments = comments;
	}

	public String getInsertionDate() {
		return InsertionDate;
	}

	public void setInsertionDate(String insertionDate) {
		if (insertionDate == null)insertionDate = "";
		InsertionDate = insertionDate;
	}

	public String getModificationDate() {
		return ModificationDate;
	}

	public void setModificationDate(String modificationDate) {
		if (modificationDate == null)modificationDate = "";
		ModificationDate = modificationDate;
	}

	public long getEnterpriseID() {
		return EnterpriseID;
	}

	public void setEnterpriseID(long enterpriseID) {
		EnterpriseID = enterpriseID;
	}
	
	

}
