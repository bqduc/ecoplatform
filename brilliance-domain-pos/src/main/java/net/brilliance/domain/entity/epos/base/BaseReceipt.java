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

import javax.persistence.MappedSuperclass;

import net.brilliance.domain.entity.epos.MenuItem;
import net.brilliance.domain.entity.epos.ReceiptItem;
import net.brilliance.framework.entity.BizObjectBase;


/**
 * This is an object that contains data related to the RECEPIE table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="RECEPIE"
 */
@MappedSuperclass
public abstract class BaseReceipt extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -886588890715576050L;
	public static String REF = "Recepie"; //$NON-NLS-1$
	public static String PROP_MENU_ITEM = "menuItem"; //$NON-NLS-1$
	public static String PROP_ID = "id"; //$NON-NLS-1$


	// constructors
	public BaseReceipt () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseReceipt (java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}

	// many to one
	private MenuItem menuItem;

	// collections
	private java.util.List<ReceiptItem> recepieItems;

	/**
	 * Return the value associated with the column: MENU_ITEM
	 */
	public MenuItem getMenuItem () {
					return menuItem;
			}

	/**
	 * Set the value related to the column: MENU_ITEM
	 * @param menuItem the MENU_ITEM value
	 */
	public void setMenuItem (MenuItem menuItem) {
		this.menuItem = menuItem;
	}



	/**
	 * Return the value associated with the column: recepieItems
	 */
	public java.util.List<ReceiptItem> getRecepieItems () {
					return recepieItems;
			}

	/**
	 * Set the value related to the column: recepieItems
	 * @param recepieItems the recepieItems value
	 */
	public void setRecepieItems (java.util.List<ReceiptItem> recepieItems) {
		this.recepieItems = recepieItems;
	}

	public void addTorecepieItems (ReceiptItem recepieItem) {
		if (null == getRecepieItems()) setRecepieItems(new java.util.ArrayList<ReceiptItem>());
		getRecepieItems().add(recepieItem);
	}

}