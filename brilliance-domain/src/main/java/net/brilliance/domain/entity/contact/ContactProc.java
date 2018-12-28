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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import net.brilliance.model.GenderType;

/**
 * A user.
 * 
 * @author Bui Quy Duc
 */
@Entity
@Table(name = "contact_profile")
@EqualsAndHashCode(callSuper = true)
public class ContactProc extends ContactBase {
	private static final long serialVersionUID = -5019226095410649159L;

	@Size(max = 15)
	@Column(name="code", length=15, unique=true)
	private String code;

	@Size(max = 150)
	@Column(name="full_name")
	private String fullName;

	@NotNull
	@Size(min = 1, max = 150)
	@Column(unique = true)
	private String email;

	@Size(max = 50)
	@Column(name="phones")
	private String phones;

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

  @Column(name="birth_date")
	@DateTimeFormat(iso = ISO.DATE)
  private Date birthDate;

  @Column(name="birth_place", length=120)
  private String birthPlace;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getActivationKey() {
		return activationKey;
	}

	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}

	public String getResetKey() {
		return resetKey;
	}

	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}

	public ZonedDateTime getResetDate() {
		return resetDate;
	}

	public void setResetDate(ZonedDateTime resetDate) {
		this.resetDate = resetDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getTaxOffice() {
		return taxOffice;
	}

	public void setTaxOffice(String taxOffice) {
		this.taxOffice = taxOffice;
	}

	public BigDecimal getDebitLimit() {
		return debitLimit;
	}

	public void setDebitLimit(BigDecimal debitLimit) {
		this.debitLimit = debitLimit;
	}

	public BigDecimal getRiskLimit() {
		return riskLimit;
	}

	public void setRiskLimit(BigDecimal riskLimit) {
		this.riskLimit = riskLimit;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public static ContactProc getInstance(String login, String firstName, String lastName, String email){
		ContactProc fetchedObject = new ContactProc();
		/*fetchedObject.setLogin(login);
		fetchedObject.setPassword("pxd" + login+"@123");*/
		fetchedObject.setFullName(firstName);
		fetchedObject.setEmail(email);
		return fetchedObject;
	}
}
