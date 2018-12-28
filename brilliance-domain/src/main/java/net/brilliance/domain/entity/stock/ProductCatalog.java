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
import net.brilliance.domain.entity.general.Catalogue;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * A user.
 * 
 * @author ducbq
 */
@Data
@Entity
@Table(name = "product_catalog")
@EqualsAndHashCode(callSuper = true)
public class ProductCatalog extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8091363533819119110L;

	@ManyToOne(targetEntity=Catalogue.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "catalog_id")
	private Catalogue catalog;

	@ManyToOne(targetEntity=Product.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Catalogue getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalogue catalog) {
		this.catalog = catalog;
	}

	public static ProductCatalog getInstance(Product product, Catalogue catalog){
		ProductCatalog instance = new ProductCatalog();
		instance.setProduct(product);
		instance.setCatalog(catalog);
		return instance;
	}
}
