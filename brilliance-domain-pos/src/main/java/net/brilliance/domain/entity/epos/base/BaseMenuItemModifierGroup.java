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

import net.brilliance.domain.entity.epos.MenuModifierGroup;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * This is an object that contains data related to the MENUITEM_MODIFIERGROUP table. Do not modify this class because it will be overwritten if the configuration file related to
 * this class is modified.
 *
 * @hibernate.class table="MENUITEM_MODIFIERGROUP"
 */
@MappedSuperclass
public abstract class BaseMenuItemModifierGroup extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6370740710538408150L;
	public static String REF = "MenuItemModifierGroup"; //$NON-NLS-1$
	public static String PROP_MIN_QUANTITY = "minQuantity"; //$NON-NLS-1$
	public static String PROP_SORT_ORDER = "sortOrder"; //$NON-NLS-1$
	public static String PROP_ID = "id"; //$NON-NLS-1$
	public static String PROP_MODIFIER_GROUP = "modifierGroup"; //$NON-NLS-1$
	public static String PROP_MAX_QUANTITY = "maxQuantity"; //$NON-NLS-1$

	// constructors
	public BaseMenuItemModifierGroup() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMenuItemModifierGroup(java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	protected void initialize() {
	}

	// fields

	@Column(name = "minQuantity")
	protected java.lang.Integer minQuantity;

	@Column(name = "maxQuantity")
	protected java.lang.Integer maxQuantity;

	@Column(name = "sortOrder")
	protected java.lang.Integer sortOrder;

	// many to one
	@ManyToOne
	@JoinColumn(name="modifierGroup_id")
	private MenuModifierGroup modifierGroup;

	/**
	 * Return the value associated with the column: MIN_QUANTITY
	 */
	public java.lang.Integer getMinQuantity() {
		return minQuantity == null ? Integer.valueOf(0) : minQuantity;
	}

	/**
	 * Set the value related to the column: MIN_QUANTITY
	 * 
	 * @param minQuantity
	 *          the MIN_QUANTITY value
	 */
	public void setMinQuantity(java.lang.Integer minQuantity) {
		this.minQuantity = minQuantity;
	}

	/**
	 * Return the value associated with the column: MAX_QUANTITY
	 */
	public java.lang.Integer getMaxQuantity() {
		return maxQuantity == null ? Integer.valueOf(0) : maxQuantity;
	}

	/**
	 * Set the value related to the column: MAX_QUANTITY
	 * 
	 * @param maxQuantity
	 *          the MAX_QUANTITY value
	 */
	public void setMaxQuantity(java.lang.Integer maxQuantity) {
		this.maxQuantity = maxQuantity;
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
	 * Return the value associated with the column: MODIFIER_GROUP
	 */
	public MenuModifierGroup getModifierGroup() {
		return modifierGroup;
	}

	/**
	 * Set the value related to the column: MODIFIER_GROUP
	 * 
	 * @param modifierGroup
	 *          the MODIFIER_GROUP value
	 */
	public void setModifierGroup(MenuModifierGroup modifierGroup) {
		this.modifierGroup = modifierGroup;
	}

}