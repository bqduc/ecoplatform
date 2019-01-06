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
package net.brilliance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.domain.model.BusinessClass;
import net.brilliance.domain.model.GeneralStatus;
import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.global.GlobalConstants;

/**
 * A user.
 * 
 * @author ducbq
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "biz_business_subpackage")
@EqualsAndHashCode(callSuper = true)
public class BusinessSubPackage extends BizObjectBase {
	private static final long serialVersionUID = -3004508967466746861L;

	@Column(name="code", nullable=false, length=GlobalConstants.SIZE_SERIAL)
	private String code;

	@Column(name="name", length=60)
	private String name;

	@Column(name="status")
	@Enumerated(EnumType.ORDINAL)
	private GeneralStatus status;

	@Column(name="business_class")
	@Enumerated(EnumType.ORDINAL)
	private BusinessClass businessClass;
	
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

	public GeneralStatus getStatus() {
		return status;
	}

	public void setStatus(GeneralStatus status) {
		this.status = status;
	}

	public BusinessPackage getBusinessPackage() {
		return businessPackage;
	}

	public void setBusinessPackage(BusinessPackage businessPackage) {
		this.businessPackage = businessPackage;
	}

	public BusinessClass getBusinessClass() {
		return businessClass;
	}

	public void setBusinessClass(BusinessClass businessClass) {
		this.businessClass = businessClass;
	}

	public static BusinessSubPackage getInstance(String code, String name, String producer, String holder){
		BusinessSubPackage fetchedObject = new BusinessSubPackage();
		fetchedObject.setCode(code);
		/*fetchedObject.setPassword("pxd" + code + "@123");
		fetchedObject.setFirstName(firstName);
		fetchedObject.setLastName(lastName);
		fetchedObject.setEmail(email);*/
		return fetchedObject;
	}
}
