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
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.global.GlobalConstants;

/**
 * A user.
 * 
 * @author ducbq
 */
@Data
@Entity
@Table(name = "currency")
@EqualsAndHashCode(callSuper = true)
public class Currency extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2260919441709332618L;

	@NotNull
	@Size(min = 3, max=GlobalConstants.SIZE_CURRENCY_CODE)
	@Column(unique = true)
	private String code;

	@Size(max = 80)
	@Column(name="name")
	private String name;
	
	@Lob
	@Column(name = "description", columnDefinition = "TEXT")
	@Type(type = "org.hibernate.type.TextType")
	private String description;

	public String getCode() {
		return code;
	}

	public Currency setCode(String code) {
		this.code = code;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Currency setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getName() {
		return name;
	}

	public Currency setName(String name) {
		this.name = name;
		return this;
	}

	public static Currency getInstance(String code, String name){
		Currency fetchedObject = new Currency();
		fetchedObject.setCode(code);
		fetchedObject.setName(name);
		return fetchedObject;
	}
}
