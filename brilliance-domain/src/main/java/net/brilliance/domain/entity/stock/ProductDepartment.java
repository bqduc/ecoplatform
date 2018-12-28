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
package net.brilliance.domain.entity.stock;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.general.Department;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * A user.
 * 
 * @author ducbq
 */
@Data
@Entity
@Table(name = "product_department")
@EqualsAndHashCode(callSuper = true)
public class ProductDepartment extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4809857464243736743L;

	@ManyToOne(targetEntity=Department.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "department_id")
	private Department department;

	@ManyToOne(targetEntity=Product.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
