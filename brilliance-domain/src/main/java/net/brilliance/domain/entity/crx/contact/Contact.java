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
package net.brilliance.domain.entity.crx.contact;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.common.CommonUtility;
import net.brilliance.common.ListUtility;
import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.domain.entity.common.Phone;
import net.brilliance.domain.entity.config.Item;
import net.brilliance.domain.entity.contact.ContactTeam;
import net.brilliance.domain.entity.general.Document;
import net.brilliance.domain.model.ContactType;
import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.global.GlobalConstants;
import net.brilliance.model.GenderType;

/**
 * A contact.
 * 
 * @author Bui Quy Duc
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact")
@EqualsAndHashCode(callSuper = true)
public class Contact extends BizObjectBase {
	private static final long serialVersionUID = -5019226095410649159L;

	@Column(name="code", length=GlobalConstants.SIZE_SERIAL, unique=true)
	private String code;

	@Column(name="saluation", length=5)
	private String saluation;

	@Column(name="first_name", length=50)
	private String firstName;

	@Column(name="last_name", length=150)
	private String lastName;

	@Column(name="account_name", length=150)
	private String accountName;

	@Column(name="title", length=50)
	private String title;

	@Column(name="department", length=50)
	private String department;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name="office", column=@Column(name="phone_office")),
    @AttributeOverride(name="mobile", column=@Column(name="phone_mobile")),
    @AttributeOverride(name="home", column=@Column(name="phone_home")),
    @AttributeOverride(name="others", column=@Column(name="phone_others")),
  })
  private Phone phone;

	@Column(name="portal_name", length=50)
	private String portalName;

	@JsonIgnore
	@Column(name="portal_secret_key", length=50)
	private String portalSecretKey;

	@Builder.Default
	@Column(name = "portal_active")
	private java.lang.Boolean portalActive = false;

	@Column(name="email", /*nullable=false, unique = true, */length=120)
	private String email;

	@Column(name="email_others", length=120)
	private String emailOthers;

	@Builder.Default
	@Column(name = "email_opt_out")
	private java.lang.Boolean emailOptOut = false;

	@Builder.Default
	@Column(name = "email_invalid")
	private java.lang.Boolean emailInvalid = false;

	@ManyToOne
	@JoinColumn(name = "sms_opt_in_id")
	private Item smsOptIn;

	@ManyToOne
	@JoinColumn(name = "lead_source_id")
	private Item leadSource;

	@Column(name="fax", length=20)
	private String fax;

  @Column(name="birthdate")
	@DateTimeFormat(iso = ISO.DATE)
  private Date birthdate;

	@Column(name="birthplace", length=50)
	private String birthplace;

	@Column(name="gender")
  @Enumerated(EnumType.ORDINAL)
  private GenderType gender;

	@Column(name="type")
  @Enumerated(EnumType.ORDINAL)
  private ContactType type;

	@Column(name="activation_key", length=20)
	@JsonIgnore
	private String activationKey;

	@Column(name="reset_key", length=20)
	@JsonIgnore
	private String resetKey;

	@Column(name="reset_date")
	private ZonedDateTime resetDate;

	@Lob
	@Column(name = "description", columnDefinition = "TEXT")
	@Type(type = "org.hibernate.type.TextType")
	private String description;

	@ManyToOne
	@JoinColumn(name = "reports_contact_id")
	private Contact reportsTo;

	@ManyToOne
	@JoinColumn(name = "assistant_contact_id")
	private Contact assistant;

	@Builder.Default
	@Column(name = "sync_contact")
	private java.lang.Boolean syncContact = false;

	@Builder.Default
	@Column(name = "do_not_call")
	private java.lang.Boolean doNotCall = false;

	/*@Builder.Default
	@Lazy
	@OneToMany(mappedBy="contact", cascade = CascadeType.ALL)
	private Set<ContactAddress> contactAddresses = ListUtility.newHashSet();

	@Builder.Default
	@Lazy
	@OneToMany(mappedBy="contact", cascade = CascadeType.ALL)
	private Set<ContactTeam> contactTeams = ListUtility.newHashSet();*/

	@Transient
	@Builder.Default
	private Set<ContactAddress> contactAddresses = ListUtility.newHashSet();

	@Builder.Default
	@Transient
	private Set<ContactTeam> contactTeams = ListUtility.newHashSet();

	@Builder.Default
	@ManyToMany(cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
  })
  @JoinTable(name = "contact_document",
      joinColumns = @JoinColumn(name = "contact_id"),
      inverseJoinColumns = @JoinColumn(name = "document_id")
  )
  private List<Document> documents = ListUtility.createArrayList();
	
	@ManyToOne
	@JoinColumn(name = "owner_user_id")
	private UserAccount ownerUserAccount;

  @Column(name="issue_date")
	@DateTimeFormat(iso = ISO.DATE_TIME)
  private Date issueDate;
	
  @Column(name="issue_user_id")
	@DateTimeFormat(iso = ISO.DATE)
  private UserAccount issueUserAccount;

  public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSaluation() {
		return saluation;
	}

	public void setSaluation(String saluation) {
		this.saluation = saluation;
	}

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailOthers() {
		return emailOthers;
	}

	public void setEmailOthers(String emailOthers) {
		this.emailOthers = emailOthers;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public java.lang.Boolean getEmailOptOut() {
		return emailOptOut;
	}

	public void setEmailOptOut(java.lang.Boolean emailOptOut) {
		this.emailOptOut = emailOptOut;
	}

	public java.lang.Boolean getEmailInvalid() {
		return emailInvalid;
	}

	public void setEmailInvalid(java.lang.Boolean emailInvalid) {
		this.emailInvalid = emailInvalid;
	}

	public Item getSmsOptIn() {
		return smsOptIn;
	}

	public void setSmsOptIn(Item smsOptIn) {
		this.smsOptIn = smsOptIn;
	}

	public Item getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(Item leadSource) {
		this.leadSource = leadSource;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	public ContactType getType() {
		return type;
	}

	public void setType(ContactType type) {
		this.type = type;
	}

	public Contact getReportsTo() {
		return reportsTo;
	}

	public void setReportsTo(Contact reportsTo) {
		this.reportsTo = reportsTo;
	}

	public Contact getAssistant() {
		return assistant;
	}

	public void setAssistant(Contact assistant) {
		this.assistant = assistant;
	}

	public java.lang.Boolean getSyncContact() {
		return syncContact;
	}

	public void setSyncContact(java.lang.Boolean syncContact) {
		this.syncContact = syncContact;
	}

	public java.lang.Boolean getDoNotCall() {
		return doNotCall;
	}

	public void setDoNotCall(java.lang.Boolean doNotCall) {
		this.doNotCall = doNotCall;
	}

	public Set<ContactAddress> getContactAddresses() {
		return contactAddresses;
	}

	public void setContactAddresses(List<ContactAddress> contactAddresses) {
		if (null==this.contactAddresses)
			this.contactAddresses = ListUtility.newHashSet();

		if (CommonUtility.isNotEmpty(contactAddresses)){
			this.contactAddresses.addAll(contactAddresses);
		}
	}

	public void setContactAddresses(Set<ContactAddress> contactAddresses) {
		this.contactAddresses = contactAddresses;
	}

	public Set<ContactTeam> getContactTeams() {
		return contactTeams;
	}

	public void setContactTeams(List<ContactTeam> contactTeams) {
		if (null==this.contactTeams)
			this.contactTeams = ListUtility.newHashSet();

		if (CommonUtility.isNotEmpty(contactTeams)){
			this.contactTeams.addAll(contactTeams);
		}
	}

	public void setContactTeams(Set<ContactTeam> contactTeams) {
		this.contactTeams = contactTeams;
	}

	public UserAccount getOwnerUserAccount() {
		return ownerUserAccount;
	}

	public void setOwnerUserAccount(UserAccount ownerUserAccount) {
		this.ownerUserAccount = ownerUserAccount;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	/*public PersonalInfo getPersonalInfo() {
		return personalInfo;
	}

	public void setPersonalInfo(PersonalInfo personalInfo) {
		this.personalInfo = personalInfo;
	}*/

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPortalName() {
		return portalName;
	}

	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}

	public String getPortalSecretKey() {
		return portalSecretKey;
	}

	public void setPortalSecretKey(String portalSecretKey) {
		this.portalSecretKey = portalSecretKey;
	}

	public java.lang.Boolean getPortalActive() {
		return portalActive;
	}

	public void setPortalActive(java.lang.Boolean portalActive) {
		this.portalActive = portalActive;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public UserAccount getIssueUserAccount() {
		return issueUserAccount;
	}

	public void setIssueUserAccount(UserAccount issueUserAccount) {
		this.issueUserAccount = issueUserAccount;
	}

}
