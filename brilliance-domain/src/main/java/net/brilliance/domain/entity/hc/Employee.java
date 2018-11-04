package net.brilliance.domain.entity.hc;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.contact.ContactBase;
import net.brilliance.domain.entity.general.Category;
import net.brilliance.domain.entity.general.Department;
import net.brilliance.framework.global.GlobalConstants;
import net.brilliance.model.GenderType;

@Data
@Entity
@Table(name = "employee")
@EqualsAndHashCode(callSuper = true)
public class Employee extends ContactBase {
	private static final long serialVersionUID = -1976632449814228419L;

	@Size(min = 1, max=GlobalConstants.SIZE_SERIAL)
	private String code;

	@Column(name="name", length=50)
	private String firstName;

	@Column(name="last_name", length=80)
	private String lastName;
	
	@Column(name="email", length=120)
	private String email;

	@Column(name="email_alternative", length=120)
	private String emailAlternative;

	@Column(name="phone", length=130)
	private String phone;
	
	@Column(name="phone_mobile", length=150)
	private String phoneMobile;

	@Column(name="nationality", length=50)
	private String nationality;

	@Column(name="religion", length=50)
	private String religion;

	@Column(name="address", length=200)
	private String address;

	@Column(name="issue_date")
	private Date issueDate = new Date();

	@Column(name="date_of_birth")
	private Date dateOfBirth = new Date();

	@Column(name="place_of_birth")
	private String placeOfBirth;

	@Column(name="national_id", length=15)
	private String nationalId;

	@Column(name="national_id_issue_date")
	private Date nationalIdIssueDate;

	@Column(name="national_id_expired_date")
	private Date nationalIdExpiredDate;

	@Column(name="national_id_issue_place", length=100)
	private String nationalIdIssuePlace;

	@Column(name="passport", length=15)
	private String passport;

	@Column(name="passport_issue_date")
	private Date passportIssueDate;

	@Column(name="passport_expired_date")
	private Date passportExpiredDate;

	@Column(name="passport_issue_place", length=100)
	private String passportIssuePlace;

	@Column(name="active")
	private boolean active;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	@Lob
	@Column(name = "description", columnDefinition = "TEXT")
	@Type(type = "org.hibernate.type.TextType")
	private String description;

	@Column(name="education", length=150)
	private String education;

	@Column(name="freign_languages", length=150)
	private String freignLanguages;

	@Column(name="marital_status", length=30)
	private String maritalStatus;

	@Column(name="work_experiences", length=120)
	private String workExperiences;

	@Column(name="gender")
  @Enumerated(EnumType.ORDINAL)
  private GenderType gender;

  public Employee() {
		super();
	}
	
	public Employee(String name, String lastName, String email, String phone, boolean active) {
		super();
		this.firstName = name;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.active = active;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public Employee setFirstName(String name) {
		this.firstName = name;
		return this;
	}
	public String getLastName() {
		return lastName;
	}
	public Employee setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	public String getEmail() {
		return email;
	}
	public Employee setEmail(String email) {
		this.email = email;
		return this;
	}
	public String getPhone() {
		return phone;
	}
	public Employee setPhone(String phone) {
		this.phone = phone;
		return this;
	}
	public boolean isActive() {
		return active;
	}
	public Employee setActive(boolean active) {
		this.active = active;
		return this;
	}

	public String getCode() {
		return code;
	}

	public Employee setCode(String code) {
		this.code = code;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public Employee setAddress(String address) {
		this.address = address;
		return this;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public Employee setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
		return this;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public Employee setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public Employee setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
		return this;
	}

	public String getNationalId() {
		return nationalId;
	}

	public Employee setNationalId(String nationalId) {
		this.nationalId = nationalId;
		return this;
	}

	public Date getNationalIdIssueDate() {
		return nationalIdIssueDate;
	}

	public Employee setNationalIdIssueDate(Date nationalIdIssueDate) {
		this.nationalIdIssueDate = nationalIdIssueDate;
		return this;
	}

	public Date getNationalIdExpiredDate() {
		return nationalIdExpiredDate;
	}

	public Employee setNationalIdExpiredDate(Date nationalIdExpiredDate) {
		this.nationalIdExpiredDate = nationalIdExpiredDate;
		return this;
	}

	public String getNationalIdIssuePlace() {
		return nationalIdIssuePlace;
	}

	public Employee setNationalIdIssuePlace(String nationalIdIssuePlace) {
		this.nationalIdIssuePlace = nationalIdIssuePlace;
		return this;
	}

	public String getPassport() {
		return passport;
	}

	public Employee setPassport(String passport) {
		this.passport = passport;
		return this;
	}

	public Date getPassportIssueDate() {
		return passportIssueDate;
	}

	public Employee setPassportIssueDate(Date passportIssueDate) {
		this.passportIssueDate = passportIssueDate;
		return this;
	}

	public Date getPassportExpiredDate() {
		return passportExpiredDate;
	}

	public Employee setPassportExpiredDate(Date passportExpiredDate) {
		this.passportExpiredDate = passportExpiredDate;
		return this;
	}

	public String getPassportIssuePlace() {
		return passportIssuePlace;
	}

	public Employee setPassportIssuePlace(String passportIssuePlace) {
		this.passportIssuePlace = passportIssuePlace;
		return this;
	}

	public Department getDepartment() {
		return department;
	}

	public Employee setDepartment(Department department) {
		this.department = department;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Employee setDescription(String description) {
		this.description = description;
		return this;
	}

	public Category getCategory() {
		return category;
	}

	public Employee setCategory(Category category) {
		this.category = category;
		return this;
	}

	public String getPhoneMobile() {
		return phoneMobile;
	}

	public Employee setPhoneMobile(String phoneMobile) {
		this.phoneMobile = phoneMobile;
		return this;
	}

	public String getNationality() {
		return nationality;
	}

	public Employee setNationality(String nationality) {
		this.nationality = nationality;
		return this;
	}

	public String getReligion() {
		return religion;
	}

	public Employee setReligion(String religion) {
		this.religion = religion;
		return this;
	}

	public String getEducation() {
		return education;
	}

	public Employee setEducation(String education) {
		this.education = education;
		return this;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public Employee setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
		return this;
	}

	public GenderType getGender() {
		return gender;
	}

	public Employee setGender(GenderType gender) {
		this.gender = gender;
		return this;
	}

	public String getFreignLanguages() {
		return freignLanguages;
	}

	public Employee setFreignLanguages(String freignLanguages) {
		this.freignLanguages = freignLanguages;
		return this;
	}

	public String getWorkExperiences() {
		return workExperiences;
	}

	public Employee setWorkExperiences(String workExperiences) {
		this.workExperiences = workExperiences;
		return this;
	}

}
