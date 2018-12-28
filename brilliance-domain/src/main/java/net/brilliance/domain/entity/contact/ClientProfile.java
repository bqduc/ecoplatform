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

import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.global.GlobalConstants;

/**
 * A user.
 * 
 * @author ducbq
 */
@Entity
@Table(name = "client_profile")
@EqualsAndHashCode(callSuper = true)
@NamedQueries({
  @NamedQuery(name="ClientProfile.searchObjects",
              query="SELECT profile " +
                    "FROM ClientProfile profile " +
                    "WHERE profile.code LIKE CONCAT('%', :keyword, '%') OR profile.fullName LIKE CONCAT('%', :keyword, '%') ")
})
public class ClientProfile extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2666623046995863658L;

	//@NotNull
	@Size(min = 1, max=GlobalConstants.SIZE_SERIAL)
	//@Column(unique = true)
	private String code;

	@Size(max = 150)
	@Column(name="full_name")
	private String fullName;

	@Size(max = 50)
	@Column(name="phones")
	private String phones;

	@Size(max = 50)
	@Column(name="email")
	private String email;

	@Size(max = 80)
	@Column(name="nationality")
	private String nationality;

	@Size(max = 80)
	@Column(name="type")
	private String type;

	@Size(max = 80)
	@Column(name="profile_type")
	private String profileType;

	@Size(max = 250)
	@Column(name="address")
	private String address;

	@Size(max = 120)
	@Column(name="activation_key")
	@JsonIgnore
	private String activationKey;

	@Column(name="activation_date")
	private ZonedDateTime activationDate = null;

	@Column(name="reset_key")
	@JsonIgnore
	private String resetKey;

	@Column(name="reset_date")
	private ZonedDateTime resetDate = null;

	@Column(name="issue_date")
	private Date issueDate = new Date();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserAccount user;

	@Column(name="notes", columnDefinition="TEXT")
	private String notes;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public ZonedDateTime getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(ZonedDateTime activationDate) {
		this.activationDate = activationDate;
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

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public UserAccount getUser() {
		return user;
	}

	public void setUser(UserAccount user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getProfileType() {
		return profileType;
	}

	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}

	public static ClientProfile getInstance(){
		ClientProfile instance = new ClientProfile();
		return instance;
	}

	public static ClientProfile getInstance(UserAccount clientUser){
		ClientProfile fetchedObject = new ClientProfile();
		//fetchedObject.setCode(clientUser.getEmail());
		fetchedObject.setFullName(clientUser.getFirstName() + " " + clientUser.getLastName());
		fetchedObject.setUser(clientUser);
		return fetchedObject;
	}

	public static ClientProfile getInstance(String code, String fullName, String address, String phones, String email){
		ClientProfile fetchedObject = new ClientProfile();
		fetchedObject.setCode(code);
		//fetchedObject.setPassword("pxd" + code + "@123");
		fetchedObject.setFullName(fullName);
		fetchedObject.setAddress(address);
		fetchedObject.setPhones(phones);
		//fetchedObject.setEmail(email);
		return fetchedObject;
	}
}
