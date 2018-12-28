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

import net.brilliance.framework.entity.BizObjectBase;


/**
 * This is an object that contains data related to the SHIFT table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="SHIFT"
 */
@MappedSuperclass
public abstract class BaseShift extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6440968615843282565L;
	public static String REF = "Shift"; //$NON-NLS-1$
	public static String PROP_NAME = "name"; //$NON-NLS-1$
	public static String PROP_SHIFT_LENGTH = "shiftLength"; //$NON-NLS-1$
	public static String PROP_ID = "id"; //$NON-NLS-1$
	public static String PROP_END_TIME = "endTime"; //$NON-NLS-1$
	public static String PROP_START_TIME = "startTime"; //$NON-NLS-1$


	// constructors
	public BaseShift () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseShift (java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseShift (
		java.lang.Long id,
		java.lang.String name) {

		this.setId(id);
		this.setName(name);
		initialize();
	}

	protected void initialize () {}

	// fields

	@Column(name = "name")
	private java.lang.String name;

	@Column(name = "startTime")
	private java.util.Date startTime;

	@Column(name = "endTime")
	private java.util.Date endTime;

	@Column(name = "shiftLength")
	private java.lang.Long shiftLength;

	/**
	 * Return the value associated with the column: NAME
	 */
	public java.lang.String getName () {
			return name;
	}

	/**
	 * Set the value related to the column: NAME
	 * @param name the NAME value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: START_TIME
	 */
	public java.util.Date getStartTime () {
			return startTime;
	}

	/**
	 * Set the value related to the column: START_TIME
	 * @param startTime the START_TIME value
	 */
	public void setStartTime (java.util.Date startTime) {
		this.startTime = startTime;
	}



	/**
	 * Return the value associated with the column: END_TIME
	 */
	public java.util.Date getEndTime () {
			return endTime;
	}

	/**
	 * Set the value related to the column: END_TIME
	 * @param endTime the END_TIME value
	 */
	public void setEndTime (java.util.Date endTime) {
		this.endTime = endTime;
	}



	/**
	 * Return the value associated with the column: SHIFT_LEN
	 */
	public java.lang.Long getShiftLength () {
			return shiftLength;
	}

	/**
	 * Set the value related to the column: SHIFT_LEN
	 * @param shiftLength the SHIFT_LEN value
	 */
	public void setShiftLength (java.lang.Long shiftLength) {
		this.shiftLength = shiftLength;
	}

}