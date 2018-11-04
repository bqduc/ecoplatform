/**
 * ************************************************************************
 * * The contents of this file are subject to the MRPL 1.2
 * * (the  "License"),  being   the  Mozilla   Public  License
 * * Version 1.1  with a permitted attribution clause; you may not  use this
 * * file except in compliance with the License. You  may  obtain  a copy of
 * * the License at http://www.floreantpos.org/license.html
 * * Software distributed under the License  is  distributed  on  an "AS IS"
 * * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * * License for the specific  language  governing  rights  and  limitations
 * * under the License.
 * * The Original Code is FLOREANT POS.
 * * The Initial Developer of the Original Code is OROCUBE LLC
 * * All portions are Copyright (C) 2015 OROCUBE LLC
 * * All Rights Reserved.
 * ************************************************************************
 */
package net.brilliance.domain.entity.epos;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.epos.base.BaseInventoryVendor;

@Entity
@Table(name = "vpos_inventory_vendor")
@EqualsAndHashCode(callSuper = true)
public class InventoryVendor extends BaseInventoryVendor {
	private static final long serialVersionUID = 1L;

	public InventoryVendor() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public InventoryVendor(java.lang.Long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public InventoryVendor(java.lang.Long id, java.lang.String name, java.lang.String address, java.lang.String city, java.lang.String state,
			java.lang.String zip, java.lang.String country, java.lang.String email, java.lang.String phone) {

		super(id, name, address, city, state, zip, country, email, phone);
	}

	@Override
	public String toString() {
		return getName();
	}
}