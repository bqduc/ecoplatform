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

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.MappedSuperclass;

import net.brilliance.framework.entity.BizObjectBase;

/**
 * This is an object that contains data related to the PRINTER_GROUP table. Do not modify this class because it will be overwritten if the configuration file related to this class
 * is modified.
 *
 * @hibernate.class table="PRINTER_GROUP"
 */
@MappedSuperclass
public abstract class BasePrinterGroup extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5995995875524173702L;
	public static String REF = "PrinterGroup";
	public static String PROP_IS_DEFAULT = "isDefault";
	public static String PROP_ID = "id";
	public static String PROP_NAME = "name";

	// constructors
	public BasePrinterGroup() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BasePrinterGroup(java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BasePrinterGroup(java.lang.Long id, java.lang.String name) {

		this.setId(id);
		this.setName(name);
		initialize();
	}

	protected void initialize() {
	}

	// fields
	
	@Column(name="name")
	protected java.lang.String name;
	
	@Column(name="isDefault")
	protected boolean isDefault;

	// collections
	@ElementCollection
	@CollectionTable(name = "printerNames")
	private java.util.List<String> printerNames;

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
	 * Return the value associated with the column: IS_DEFAULT
	 */
	public boolean isIsDefault() {
		return isDefault;
	}

	/**
	 * Set the value related to the column: IS_DEFAULT
	 * 
	 * @param isDefault
	 *          the IS_DEFAULT value
	 */
	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * Return the value associated with the column: printerNames
	 */
	public java.util.List<String> getPrinterNames() {
		return printerNames;
	}

	/**
	 * Set the value related to the column: printerNames
	 * 
	 * @param printerNames
	 *          the printerNames value
	 */
	public void setPrinterNames(java.util.List<String> printerNames) {
		this.printerNames = printerNames;
	}

}