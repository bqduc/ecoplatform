/**
 * 
 */
package net.brilliance.domain.model.enums;

/**
 * @author ducbq
 *
 */
public enum EmployeeConfigurationModel {
	DataSource("/config/data/salesman.xlsx", 2, "sheet1"), 
	Code("code", 0, ""), //Header name, Index, Data format
	FirstName("first_name", 2, ""),
	LastName("last_name", 1, ""),
	DateOfBirth("date_of_birth", 4, "dd-MM-yyyy"),
	PlaceOfBirth("place_of_birth", 5, ""),
	NationalId("national_id", 6, ""),
	NationalIdIssueDate("national_id_issue_date", 7, "dd/MM/yyyy"),
	NationalIdIssuePlace("national_id_issue_palce", 8, ""),
	NationalIdExpiredDate("national_id_expired_date", 9, "dd/MM/yyyy"),
	PassportNo("passport_no", 10, ""),
	PassportIssueDate("passport_issue_date", 11, "dd/MM/yyyy"),
	PassportIssuePlace("passport_issue_palce", 12, ""),
	PassportExpiredDate("passport_expired_date", 13, "dd/MM/yyyy"),
	Address("address", 14, ""),
	BillingAddress("billing_address", 15, ""),
	ShippingAddress("shipping_address", 16, ""),
	PermanentAddress("permanent_address", 17, ""),
	Phone("phone", 18, ""),
	PhoneMobile("mobile_phone", 19, ""),
	Email("email", 20, ""),
	Gender("gender", 21, ""),
	MaritalStatus("marital_status", 23, ""),
	Education("education", 24, ""),
	ForeignLanguage("foreignLanguage", 25, ""),
	WorkExperiences("workExperiences", 27, ""),
	Nationality("nationality", 22, ""),
	Religion("religion", 31, ""),
	Ethnic("ethnic", 30, ""),
	Info("info", 28, ""),

	GenderTypeMale("configGenderMale", -1, "nam;NAM"),
	GenderTypeFemale("configGenderFemale", -1, "nữ;NỮ")
	;

	private String model;
	private int index;
	private String dataPattern;
	
	private EmployeeConfigurationModel(String model, int index, String dataPattern){
		this.model = model;
		this.index = index;
		this.dataPattern = dataPattern;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getDataPattern() {
		return dataPattern;
	}

	public void setDataPattern(String dataPattern) {
		this.dataPattern = dataPattern;
	}
}
