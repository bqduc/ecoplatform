/*
* Copyright 2019, Bui Quy Duc
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
package net.brilliance.domain;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.contact.ClientProfile;
import net.brilliance.domain.model.GeneralStatus;
import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.global.GlobalConstants;

/**
 * A user.
 * 
 * @author ducbq
 */
@Data
@Entity
@Table(name = "brx_business_package_price")
@EqualsAndHashCode(callSuper = true)
public class BusinessPackagePricing extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3262457468487428529L;

	@Column(name="code", nullable=false, length=GlobalConstants.SIZE_SERIAL)
	private String code;

	@ManyToOne
	@JoinColumn(name = "business_package_id")
	private BusinessPackage businessPackage;

	@ManyToOne
	@JoinColumn(name = "route_id")
	private Route route;

	@Column(name="date_begin")
	private Date dateBegin;

	@Column(name="date_end")
	private Date dateEnd;

	@Size(min = 3, max = 60)
	@Column(name="name")
	private String name;

	@Size(max = 50)
	@Column(name="producer")
	private String producer;

	@Size(max = 50)
	@Column(name="holder")
	private String holder;

	@Size(max = 50)
	@Column(name="phones")
	private String phones;

	@Column(name="status")
	@Enumerated(EnumType.ORDINAL)
	private GeneralStatus status;

	@Column(name="reset_date")
	private ZonedDateTime resetDate = null;

	@Column(name="publication_date")
	private ZonedDateTime publicationDate = null;

	@ManyToOne
	@JoinColumn(name = "client_id")
	private ClientProfile clientProfile;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public GeneralStatus getStatus() {
		return status;
	}

	public void setStatus(GeneralStatus status) {
		this.status = status;
	}

	public ZonedDateTime getResetDate() {
		return resetDate;
	}

	public void setResetDate(ZonedDateTime resetDate) {
		this.resetDate = resetDate;
	}

	public ZonedDateTime getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(ZonedDateTime publicationDate) {
		this.publicationDate = publicationDate;
	}

	public ClientProfile getClientProfile() {
		return clientProfile;
	}

	public void setClientProfile(ClientProfile clientProfile) {
		this.clientProfile = clientProfile;
	}

	public static BusinessPackagePricing getInstance(String code, String name, String producer, String holder){
		BusinessPackagePricing fetchedObject = new BusinessPackagePricing();
		fetchedObject.setCode(code);
		/*fetchedObject.setPassword("pxd" + code + "@123");
		fetchedObject.setFirstName(firstName);
		fetchedObject.setLastName(lastName);
		fetchedObject.setEmail(email);*/
		return fetchedObject;
	}
}
