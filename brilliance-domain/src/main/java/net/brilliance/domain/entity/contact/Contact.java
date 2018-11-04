/*
* Copyright 2017, Bui Quy Duc
* by the @authors tag. See the LICENCE in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package net.brilliance.domain.entity.contact;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.domain.entity.common.Address;
import net.brilliance.domain.entity.config.Item;
import net.brilliance.domain.entity.general.Activity;
import net.brilliance.model.GenderType;

/**
 * A user.
 * 
 * @author Bui Quy Duc
 */
@Entity
@Table(name = "contact")
@EqualsAndHashCode(callSuper = true)
@NamedQuery(
    name="findContactByCode",
    query="SELECT c FROM Contact c WHERE c.code = :code"
)
public class Contact extends ContactBase {
	private static final long serialVersionUID = -5019226095410649159L;

	@Size(max = 15)
	@Column(name="code", length=15, unique=true)
	private String code;

	@Size(max = 50)
	@Column(name="first_name")
	private String firstName;

	@Size(max = 150)
	@Column(name="last_name")
	private String lastName;

	//@NotNull
	@Size(max = 150)
	//@Column(unique = true)
	private String email;

	@Size(max = 50)
	@Column(name="phones")
	private String phones;

	@Size(max = 50)
	@Column(name="cell_phones")
	private String cellPhones;

	@Size(max = 250)
	@Column(name="address")
	private String address;

	@Size(max = 20)
	@Column(name="activation_key")
	@JsonIgnore
	private String activationKey;

	@Size(max = 20)
	@Column(name="reset_key")
	@JsonIgnore
	private String resetKey;

	@Column(name="reset_date")
	private ZonedDateTime resetDate = null;

	@Size(max = 20)
	@Column(name="national_id")
	private String nationalId;

  @Column(name="national_id_issued_date")
	@DateTimeFormat(iso = ISO.DATE)
  private Date nationalIdIssuedDate;

  @Column(name="national_id_expired_date")
	@DateTimeFormat(iso = ISO.DATE)
  private Date nationalIdExpiredDate;

	@Size(max = 150)
  @Column(name="national_id_issued_place")
  private String nationalIdIssuedPlace;

	@Size(max = 20)
	@Column(name="passport_no")
	private String passportNo;

  @Column(name="passport_issued_date")
	@DateTimeFormat(iso = ISO.DATE)
  private Date passportIssuedDate;

  @Column(name="passport_expired_date")
	@DateTimeFormat(iso = ISO.DATE)
  private Date passportExpiredDate;

	@Size(max = 150)
  @Column(name="passport_issued_place")
  private String passportIssuedPlace;

	@Column(name="tax_id", length=12)
  private String taxId; 
  
  @Column(name="tax_office", length=80)
  private String taxOffice;

  @Column(name="debit_limit", precision=10, scale=2)
  private BigDecimal debitLimit = BigDecimal.ZERO;
  
  @Column(name="risk_limit", precision=10, scale=2)
  private BigDecimal riskLimit = BigDecimal.ZERO;

  @Column(name="gender")
  @Enumerated(EnumType.ORDINAL)
  private GenderType gender;

  @Column(name="date_of_birth")
	@DateTimeFormat(iso = ISO.DATE)
  private Date dateOfBirth;

  @Column(name="place_of_birth", length=120)
  private String placeOfBirth;

  @Column(name="overall_experience")
  private String overallExperience;
  
  @Column(name="overall_expectation")
  private String overallExpectation;

	@ManyToOne
	@JoinColumn(name = "marital_status_id")
	private Item maritalStatus;
  
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	//@formatter:off
	@JoinTable(
			name = "contact_qualification", 
			inverseJoinColumns = {@JoinColumn(name = "qualification_id")},
			joinColumns = {@JoinColumn(name = "contact_id")}
	)
	//@formatter:on
	private Set<Item> qualifications;

	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	//@formatter:off
	@JoinTable(
			name = "contact_activity", 
			inverseJoinColumns = {@JoinColumn(name = "activity_id")},
			joinColumns = {@JoinColumn(name = "contact_id")}
	)
	//@formatter:on
	private Set<Activity> activities;

	@Column(name="nationality")
  private String nationality;

  @Column(name="ethnic_group")
  private String ethnicGroup;

	@Column(name="notes", columnDefinition="TEXT")
	private String notes;

  @Column(name="issued_date")
	@DateTimeFormat(iso = ISO.DATE)
  private Date issuedDate;

	@ManyToOne
	@JoinColumn(name = "issued_user_id")
	private UserAccount issuedUser;

	@Lob
	@Column(name = "description", columnDefinition = "TEXT")
	@Type(type = "org.hibernate.type.TextType")
	private String description;

	public String getEmail() {
		return email;
	}

	public Contact setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPhones() {
		return phones;
	}

	public Contact setPhones(String phones) {
		this.phones = phones;
		return this;
	}

	public String getCellPhones() {
		return cellPhones;
	}

	public Contact setCellPhones(String cellPhones) {
		this.cellPhones = cellPhones;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public Contact setAddress(String address) {
		this.address = address;
		return this;
	}

	public Contact setPresentAddress(String address, String country) {
		super.setCurrentAddress(Address.instance(address, country));
		return this;
	}

	public Contact setBillingAddress(String address, String country) {
		super.setBillingAddress(Address.instance(address, country));
		return this;
	}

	public Contact setResidentAddress(String address, String country) {
		super.setPermanentAddress(Address.instance(address, country));
		return this;
	}

	public String getActivationKey() {
		return activationKey;
	}

	public Contact setActivationKey(String activationKey) {
		this.activationKey = activationKey;
		return this;
	}

	public String getResetKey() {
		return resetKey;
	}

	public Contact setResetKey(String resetKey) {
		this.resetKey = resetKey;
		return this;
	}

	public ZonedDateTime getResetDate() {
		return resetDate;
	}

	public Contact setResetDate(ZonedDateTime resetDate) {
		this.resetDate = resetDate;
		return this;
	}

	public String getCode() {
		return code;
	}

	public Contact setCode(String code) {
		this.code = code;
		return this;
	}

	public String getTaxId() {
		return taxId;
	}

	public Contact setTaxId(String taxId) {
		this.taxId = taxId;
		return this;
	}

	public String getTaxOffice() {
		return taxOffice;
	}

	public Contact setTaxOffice(String taxOffice) {
		this.taxOffice = taxOffice;
		return this;
	}

	public BigDecimal getDebitLimit() {
		return debitLimit;
	}

	public Contact setDebitLimit(BigDecimal debitLimit) {
		this.debitLimit = debitLimit;
		return this;
	}

	public BigDecimal getRiskLimit() {
		return riskLimit;
	}

	public Contact setRiskLimit(BigDecimal riskLimit) {
		this.riskLimit = riskLimit;
		return this;
	}

	public GenderType getGender() {
		return gender;
	}

	public Contact setGender(GenderType gender) {
		this.gender = gender;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public Contact setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public Contact setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getNationalId() {
		return nationalId;
	}

	public Contact setNationalId(String nationalId) {
		this.nationalId = nationalId;
		return this;
	}

	public Date getNationalIdIssuedDate() {
		return nationalIdIssuedDate;
	}

	public Contact setNationalIdIssuedDate(Date nationalIdIssuedDate) {
		this.nationalIdIssuedDate = nationalIdIssuedDate;
		return this;
	}

	public Date getNationalIdExpiredDate() {
		return nationalIdExpiredDate;
	}

	public Contact setNationalIdExpiredDate(Date nationalIdExpiredDate) {
		this.nationalIdExpiredDate = nationalIdExpiredDate;
		return this;
	}

	public String getNationalIdIssuedPlace() {
		return nationalIdIssuedPlace;
	}

	public Contact setNationalIdIssuedPlace(String nationalIdIssuedPlace) {
		this.nationalIdIssuedPlace = nationalIdIssuedPlace;
		return this;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public Contact setPassportNo(String passportNo) {
		this.passportNo = passportNo;
		return this;
	}

	public Date getPassportIssuedDate() {
		return passportIssuedDate;
	}

	public Contact setPassportIssuedDate(Date passportIssuedDate) {
		this.passportIssuedDate = passportIssuedDate;
		return this;
	}

	public Date getPassportExpiredDate() {
		return passportExpiredDate;
	}

	public Contact setPassportExpiredDate(Date passportExpiredDate) {
		this.passportExpiredDate = passportExpiredDate;
		return this;
	}

	public String getPassportIssuedPlace() {
		return passportIssuedPlace;
	}

	public Contact setPassportIssuedPlace(String passportIssuedPlace) {
		this.passportIssuedPlace = passportIssuedPlace;
		return this;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public Contact setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public Contact setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
		return this;
	}

	public String getOverallExperience() {
		return overallExperience;
	}

	public Contact setOverallExperience(String overallExperience) {
		this.overallExperience = overallExperience;
		return this;
	}

	public String getOverallExpectation() {
		return overallExpectation;
	}

	public Contact setOverallExpectation(String overallExpectation) {
		this.overallExpectation = overallExpectation;
		return this;
	}

	public Item getMaritalStatus() {
		return maritalStatus;
	}

	public Contact setMaritalStatus(Item maritalStatus) {
		this.maritalStatus = maritalStatus;
		return this;
	}

	public Set<Item> getQualifications() {
		return qualifications;
	}

	public Contact setQualifications(Set<Item> qualifications) {
		this.qualifications = qualifications;
		return this;
	}

	public Contact addQualification(Item qualification) {
		this.qualifications.add(qualification);
		return this;
	}

	public String getNationality() {
		return nationality;
	}

	public Contact setNationality(String nationality) {
		this.nationality = nationality;
		return this;
	}

	public String getEthnicGroup() {
		return ethnicGroup;
	}

	public Contact setEthnicGroup(String ethnicGroup) {
		this.ethnicGroup = ethnicGroup;
		return this;
	}

	public String getNotes() {
		return notes;
	}

	public Contact setNotes(String notes) {
		this.notes = notes;
		return this;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public Contact setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
		return this;
	}

	public UserAccount getIssuedUser() {
		return issuedUser;
	}

	public Contact setIssuedUser(UserAccount issuedUser) {
		this.issuedUser = issuedUser;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Activity> getActivities() {
		return activities;
	}

	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}

	public static Contact getInstance(String code, String firstName, String lastName){
		/*fetchedObject.setLogin(login);
		fetchedObject.setPassword("pxd" + login+"@123");*/
		return new Contact()
				.setCode(code)
				.setFirstName(firstName)
				.setLastName(lastName)
		;
	}
}
