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
package net.brilliance.domain.entity.contact;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import net.brilliance.domain.entity.common.Address;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * A contact base abstract class.
 * 
 * @author Bui Quy Duc
 */
@MappedSuperclass
public abstract class ContactBase extends BizObjectBase {
  /**
	 * 
	 */
	private static final long serialVersionUID = -3341757859940654082L;

	@Embedded
  @AttributeOverrides({
    @AttributeOverride(name="address", column=@Column(name="billing_primary")),
    @AttributeOverride(name="city", column=@Column(name="billing_city")),
    @AttributeOverride(name="state", column=@Column(name="billing_state")),
    @AttributeOverride(name="postalCode", column=@Column(name="billing_postal_code")),
    @AttributeOverride(name="country", column=@Column(name="billing_country")),
  })
  private Address billingAddress;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name="address", column=@Column(name="shipping_primary")),
    @AttributeOverride(name="city", column=@Column(name="shipping_city")),
    @AttributeOverride(name="state", column=@Column(name="shipping_state")),
    @AttributeOverride(name="postalCode", column=@Column(name="shipping_postal_code")),
    @AttributeOverride(name="country", column=@Column(name="shipping_country")),
  })
  private Address shippingAddress;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name="address", column=@Column(name="current_primary")),
    @AttributeOverride(name="city", column=@Column(name="current_city")),
    @AttributeOverride(name="state", column=@Column(name="current_state")),
    @AttributeOverride(name="postalCode", column=@Column(name="current_postal_code")),
    @AttributeOverride(name="country", column=@Column(name="current_country")),
  })
  private Address currentAddress;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name="address", column=@Column(name="permanent_primary")),
    @AttributeOverride(name="city", column=@Column(name="permanent_city")),
    @AttributeOverride(name="state", column=@Column(name="permanent_state")),
    @AttributeOverride(name="postalCode", column=@Column(name="permanent_postal_code")),
    @AttributeOverride(name="country", column=@Column(name="permanent_country")),
  })
  private Address permanentAddress;

	public Address getBillingAddress() {
		return billingAddress;
	}

	public ContactBase setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
		return this;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public ContactBase setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
		return this;
	}

	public Address getPermanentAddress() {
		return permanentAddress;
	}

	public ContactBase setPermanentAddress(Address permanentAddress) {
		this.permanentAddress = permanentAddress;
		return this;
	}

	public Address getCurrentAddress() {
		return currentAddress;
	}

	public ContactBase setCurrentAddress(Address currentAddress) {
		this.currentAddress = currentAddress;
		return this;
	}
}
