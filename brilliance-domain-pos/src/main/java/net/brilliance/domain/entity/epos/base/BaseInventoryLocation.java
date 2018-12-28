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
package net.brilliance.domain.entity.epos.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

import net.brilliance.domain.entity.epos.InventoryWarehouse;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * This is an object that contains data related to the INVENTORY_LOCATION table. Do not modify this class because it will be overwritten if the configuration file related to this
 * class is modified.
 *
 * @hibernate.class table="INVENTORY_LOCATION"
 */
@MappedSuperclass
public abstract class BaseInventoryLocation extends BizObjectBase {
	private static final long serialVersionUID = 7308312550693160924L;
	public static String REF = "InventoryLocation"; //$NON-NLS-1$
	public static String PROP_NAME = "name"; //$NON-NLS-1$
	public static String PROP_WAREHOUSE = "warehouse"; //$NON-NLS-1$
	public static String PROP_SORT_ORDER = "sortOrder"; //$NON-NLS-1$
	public static String PROP_ID = "id"; //$NON-NLS-1$

	// constructors
	public BaseInventoryLocation() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseInventoryLocation(java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseInventoryLocation(java.lang.Long id, java.lang.String name) {

		this.setId(id);
		this.setName(name);
		initialize();
	}

	protected void initialize() {
	}

	// fields
	@Size(max = 100)
	@Column(name = "name")
	protected java.lang.String name;

	@Column(name = "sortOrder")
	protected java.lang.Integer sortOrder;

	// many to one
	private InventoryWarehouse warehouse;

	/**
	 * Return the value associated with the column: NAME
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * Set the value related to the column: NAME
	 * 
	 * @param name
	 *          the NAME value
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * Return the value associated with the column: SORT_ORDER
	 */
	public java.lang.Integer getSortOrder() {
		return sortOrder == null ? Integer.valueOf(0) : sortOrder;
	}

	/**
	 * Set the value related to the column: SORT_ORDER
	 * 
	 * @param sortOrder
	 *          the SORT_ORDER value
	 */
	public void setSortOrder(java.lang.Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * Return the value associated with the column: WAREHOUSE_ID
	 */
	public InventoryWarehouse getWarehouse() {
		return warehouse;
	}

	/**
	 * Set the value related to the column: WAREHOUSE_ID
	 * 
	 * @param warehouse
	 *          the WAREHOUSE_ID value
	 */
	public void setWarehouse(InventoryWarehouse warehouse) {
		this.warehouse = warehouse;
	}
}