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
package net.brilliance.domain.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.brilliance.framework.entity.BizObjectBase;
import net.brilliance.framework.global.GlobalConstants;

/**
 * A language.
 * 
 * @author ducbq
 */
@Data
@Entity
@Table(name = "sys_language")
@EqualsAndHashCode(callSuper = true)
public class Language extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3433618526195751294L;

	@NotNull
	@Size(min = 2, max=GlobalConstants.SIZE_LANGUAGE_CODE)
	@Column(unique = true)
	private String code;

	@Size(max = 50)
	@Column(name="name")
	private String name;

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

	public static Language instance(String code, String name){
		Language fetchedObject = new Language();
		fetchedObject.setCode(code);
		fetchedObject.setName(name);
		return fetchedObject;
	}
}
