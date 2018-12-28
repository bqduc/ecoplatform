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

import net.brilliance.framework.entity.BizObjectBase;

/**
 * This is an object that contains data related to the INVENTORY_GROUP table. Do not modify this class because it will be overwritten if the configuration file related to this
 * class is modified.
 *
 * @hibernate.class table="INVENTORY_GROUP"
 */

@MappedSuperclass
public abstract class BaseInventoryGroup extends BizObjectBase/*extends RepositoryEntity */ {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6956347981172829655L;
	public static String REF = "InventoryGroup"; //$NON-NLS-1$
	public static String PROP_NAME = "name"; //$NON-NLS-1$
	public static String PROP_ID = "id"; //$NON-NLS-1$

	// constructors
	public BaseInventoryGroup() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseInventoryGroup(Long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseInventoryGroup(Long id, java.lang.String name) {

		this.setId(id);
		this.setName(name);
		initialize();
	}

	protected void initialize() {
	}

	// primary key
	// private java.lang.Integer id;

	// fields
	@Size(max = 100)
	@Column(name = "name")
	protected java.lang.String name;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="identity" column="ID"
	 */
	/*
	 * public java.lang.Integer getId () { return id; }
	 * 
	 *//**
		 * Set the unique identifier of this class
		 * 
		 * @param id
		 *          the new ID
		 *//*
			 * public void setId (java.lang.Integer id) { this.id = id; this.hashCode = Integer.MIN_VALUE; }
			 */

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
}