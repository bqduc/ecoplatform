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

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.brilliance.domain.entity.general.Catalogue;
import net.brilliance.domain.entity.general.Currency;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * A user.
 * 
 * @author ducbq
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_price")
@EqualsAndHashCode(callSuper = true)
public class ProductPrice extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8747504836927986613L;

	@ManyToOne(targetEntity=Currency.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "currency_id")
	private Currency currency;

	@ManyToOne(targetEntity=Product.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;

	@Digits(integer=12, fraction=2)
	@Column(name = "cost")
	private BigDecimal cost;

	@Digits(integer=12, fraction=2)
	@Column(name = "list_price")
	private BigDecimal listPrice;

	@Digits(integer=12, fraction=2)
	@Column(name = "wholesale_price")
	private BigDecimal wholesalePrice;

	@Digits(integer=12, fraction=2)
	@Column(name = "retail_price")
	private BigDecimal retailPrice;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
