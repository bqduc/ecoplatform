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
package net.brilliance.domain.entity.general;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.brilliance.domain.model.BusinessLevel;
import net.brilliance.domain.model.BusinessStatus;
import net.brilliance.framework.entity.BaseObject;
import net.brilliance.framework.global.GlobalConstants;

/**
 * A user.
 * 
 * @author ducbq
 */
@Data
@Entity
//@Document(collection = "biz_service")
@Table(name = "biz_service")
@EqualsAndHashCode(callSuper = true)
public class BusinessService extends BaseObject {
	private static final long serialVersionUID = -3004508967466746861L;

	@NotNull
	@Size(min = 1, max=GlobalConstants.SIZE_SERIAL)
	@Column(name="code")
	private String code;

	@Size(min = 3, max = 60)
	@Column(name="name")
	private String name;

	@Column(name="status")
	@Enumerated(EnumType.ORDINAL)
	private BusinessStatus status;

	@Column(name="business_class")
	@Enumerated(EnumType.ORDINAL)
	private BusinessLevel businessClass;
	
	@ManyToOne
	@JoinColumn(name = "package_id")
	private BusinessPackage businessPackage;

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

	public BusinessStatus getStatus() {
		return status;
	}

	public void setStatus(BusinessStatus status) {
		this.status = status;
	}

	public BusinessPackage getBusinessPackage() {
		return businessPackage;
	}

	public void setBusinessPackage(BusinessPackage businessPackage) {
		this.businessPackage = businessPackage;
	}

	public BusinessLevel getBusinessClass() {
		return businessClass;
	}

	public void setBusinessClass(BusinessLevel businessClass) {
		this.businessClass = businessClass;
	}

	public static BusinessService getInstance(String code, String name, String producer, String holder){
		BusinessService fetchedObject = new BusinessService();
		fetchedObject.setCode(code);
		/*fetchedObject.setPassword("pxd" + code + "@123");
		fetchedObject.setFirstName(firstName);
		fetchedObject.setLastName(lastName);
		fetchedObject.setEmail(email);*/
		return fetchedObject;
	}
}
