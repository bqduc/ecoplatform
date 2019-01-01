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
package net.brilliance.domain.entity.dmx;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.common.Address;
import net.brilliance.domain.entity.contact.ContactProc;
import net.brilliance.domain.entity.stock.Store;
import net.brilliance.domain.model.EnterpriseClassType;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * An enterprise base abstract class .
 * 
 * @author Bui Quy Duc
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dmx_enterprise_detail")
@EqualsAndHashCode(callSuper = true)
public class EnterpriseDetail extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9045813052692751661L;

	@Column(name = "name", length=200)
  private String name;

	@ManyToOne(targetEntity=Enterprise.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

	@ManyToOne(targetEntity=Store.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "main_officer_id")
	private ContactProc mainOfficer;

	@ManyToOne(targetEntity=Store.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "sub_officer_id")
	private ContactProc subOfficer;
	
	@Column(name = "constitution_activities", columnDefinition="TEXT")
	private String constitutionAndActivities;

  @Column(name="class_type")
  @Enumerated(EnumType.ORDINAL)
  private EnterpriseClassType classType;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "issued_date")
	private Date issuedDate;
  
	@Embedded
  @AttributeOverrides({
    @AttributeOverride(name="primary", column=@Column(name="primary_address")),
    @AttributeOverride(name="secondary", column=@Column(name="secondary_address")),
    @AttributeOverride(name="street", column=@Column(name="street")),
    @AttributeOverride(name="ward", column=@Column(name="ward")),
    @AttributeOverride(name="district", column=@Column(name="district")),
    @AttributeOverride(name="city", column=@Column(name="city")),
    @AttributeOverride(name="stateProvince", column=@Column(name="state")),
    @AttributeOverride(name="postalCode", column=@Column(name="postal_code")),
    @AttributeOverride(name="country", column=@Column(name="country")),
  })
  private Address registeredAddress;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public ContactProc getMainOfficer() {
		return mainOfficer;
	}

	public void setMainOfficer(ContactProc mainOfficer) {
		this.mainOfficer = mainOfficer;
	}

	public ContactProc getSubOfficer() {
		return subOfficer;
	}

	public void setSubOfficer(ContactProc subOfficer) {
		this.subOfficer = subOfficer;
	}

	public String getConstitutionAndActivities() {
		return constitutionAndActivities;
	}

	public void setConstitutionAndActivities(String constitutionAndActivities) {
		this.constitutionAndActivities = constitutionAndActivities;
	}

	public EnterpriseClassType getClassType() {
		return classType;
	}

	public void setClassType(EnterpriseClassType classType) {
		this.classType = classType;
	}

	public Address getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(Address registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

}
