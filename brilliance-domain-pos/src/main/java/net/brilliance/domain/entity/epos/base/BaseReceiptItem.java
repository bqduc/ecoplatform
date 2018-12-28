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

import net.brilliance.domain.entity.epos.InventoryItemPos;
import net.brilliance.domain.entity.epos.Receipt;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * This is an object that contains data related to the RECEPIE_ITEM table. Do not modify this class because it will be overwritten if the configuration file related to this class
 * is modified.
 *
 * @hibernate.class table="RECEPIE_ITEM"
 */
@MappedSuperclass
public abstract class BaseReceiptItem extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9152200927960033759L;
	public static String REF = "RecepieItem"; //$NON-NLS-1$
	public static String PROP_INVENTORY_ITEM = "inventoryItem"; //$NON-NLS-1$
	public static String PROP_PERCENTAGE = "percentage"; //$NON-NLS-1$
	public static String PROP_RECEPIE = "recepie"; //$NON-NLS-1$
	public static String PROP_ID = "id"; //$NON-NLS-1$
	public static String PROP_INVENTORY_DEDUCTABLE = "inventoryDeductable"; //$NON-NLS-1$

	// constructors
	public BaseReceiptItem() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseReceiptItem(java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseReceiptItem(java.lang.Long id, Receipt recepie) {

		this.setId(id);
		this.setRecepie(recepie);
		initialize();
	}

	protected void initialize() {
	}

	// fields

	@Column(name = "percentage")
	protected java.lang.Double percentage;

	@Column(name = "inventoryDeductable")
	protected java.lang.Boolean inventoryDeductable;

	// many to one
	private InventoryItemPos inventoryItem;
	private Receipt recepie;

	/**
	 * Return the value associated with the column: PERCENTAGE
	 */
	public java.lang.Double getPercentage() {
		return percentage == null ? Double.valueOf(0) : percentage;
	}

	/**
	 * Set the value related to the column: PERCENTAGE
	 * 
	 * @param percentage
	 *          the PERCENTAGE value
	 */
	public void setPercentage(java.lang.Double percentage) {
		this.percentage = percentage;
	}

	/**
	 * Return the value associated with the column: INVENTORY_DEDUCTABLE
	 */
	public java.lang.Boolean isInventoryDeductable() {
		return inventoryDeductable == null ? Boolean.FALSE : inventoryDeductable;
	}

	/**
	 * Set the value related to the column: INVENTORY_DEDUCTABLE
	 * 
	 * @param inventoryDeductable
	 *          the INVENTORY_DEDUCTABLE value
	 */
	public void setInventoryDeductable(java.lang.Boolean inventoryDeductable) {
		this.inventoryDeductable = inventoryDeductable;
	}

	/**
	 * Return the value associated with the column: INVENTORY_ITEM
	 */
	public InventoryItemPos getInventoryItem() {
		return inventoryItem;
	}

	/**
	 * Set the value related to the column: INVENTORY_ITEM
	 * 
	 * @param inventoryItem
	 *          the INVENTORY_ITEM value
	 */
	public void setInventoryItem(InventoryItemPos inventoryItem) {
		this.inventoryItem = inventoryItem;
	}

	/**
	 * Return the value associated with the column: RECEPIE_ID
	 */
	public Receipt getRecepie() {
		return recepie;
	}

	/**
	 * Set the value related to the column: RECEPIE_ID
	 * 
	 * @param recepie
	 *          the RECEPIE_ID value
	 */
	public void setRecepie(Receipt recepie) {
		this.recepie = recepie;
	}

}