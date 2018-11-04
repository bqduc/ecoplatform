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
package net.brilliance.domain.entity.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.admin.Authority;
import net.brilliance.framework.entity.BaseObject;

/**
 * A user.
 * 
 * @author ducbq
 */
@Data
@Entity
@Table(name = "auth_permission")
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseObject{
	private static final long serialVersionUID = 3067524972341936718L;

	@ManyToOne
	private Authority authority;
	
	@ManyToOne
	private Module module;

	@ManyToOne
	private AccessRight accessRight;

	public Authority getAuthority() {
		return authority;
	}

	public Permission setAuthority(Authority authority) {
		this.authority = authority;
		return this;
	}

	public Module getModule() {
		return module;
	}

	public Permission setModule(Module module) {
		this.module = module;
		return this;
	}

	public AccessRight getAccessRight() {
		return accessRight;
	}

	public Permission setAccessRight(AccessRight accessRight) {
		this.accessRight = accessRight;
		return this;
	}

	public static Permission getInstance(Authority authority, Module module, AccessRight accessRight){
		return new Permission()
				.setAuthority(authority)
				.setModule(module)
				.setAccessRight(accessRight);
	}
}