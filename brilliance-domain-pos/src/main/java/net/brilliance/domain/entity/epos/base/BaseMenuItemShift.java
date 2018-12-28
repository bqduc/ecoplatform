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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import net.brilliance.domain.entity.epos.Shift;
import net.brilliance.framework.entity.BizObjectBase;


/**
 * This is an object that contains data related to the MENU_ITEM_SHIFT table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="MENU_ITEM_SHIFT"
 */
@MappedSuperclass
public abstract class BaseMenuItemShift extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1942524920936517214L;
	public static String REF = "MenuItemShift"; //$NON-NLS-1$
	public static String PROP_SHIFT_PRICE = "shiftPrice"; //$NON-NLS-1$
	public static String PROP_SHIFT = "shift"; //$NON-NLS-1$
	public static String PROP_ID = "id"; //$NON-NLS-1$


	// constructors
	public BaseMenuItemShift () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMenuItemShift (java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}

	// fields
	@Column(name = "shiftPrice")
	private java.lang.Double shiftPrice;

	// many to one
	@ManyToOne
	@JoinColumn(name = "shift_id")
	private Shift shift;

	/**
	 * Return the value associated with the column: SHIFT_PRICE
	 */
	public java.lang.Double getShiftPrice () {
			return shiftPrice == null ? Double.valueOf(0) : shiftPrice;
	}

	/**
	 * Set the value related to the column: SHIFT_PRICE
	 * @param shiftPrice the SHIFT_PRICE value
	 */
	public void setShiftPrice (java.lang.Double shiftPrice) {
		this.shiftPrice = shiftPrice;
	}



	/**
	 * Return the value associated with the column: SHIFT_ID
	 */
	public Shift getShift () {
			return shift;
	}

	/**
	 * Set the value related to the column: SHIFT_ID
	 * @param shift the SHIFT_ID value
	 */
	public void setShift (Shift shift) {
		this.shift = shift;
	}

}