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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;

import net.brilliance.domain.entity.epos.MenuModifier;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * This is an object that contains data related to the MENU_MODIFIER_GROUP table. Do not modify this class because it will be overwritten if the configuration file related to this
 * class is modified.
 *
 * @hibernate.class table="MENU_MODIFIER_GROUP"
 */
@MappedSuperclass
public abstract class BaseMenuModifierGroup extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1366979651910106728L;
	public static String REF = "MenuModifierGroup"; //$NON-NLS-1$
	public static String PROP_NAME = "name"; //$NON-NLS-1$
	public static String PROP_EXCLUSIVE = "exclusive"; //$NON-NLS-1$
	public static String PROP_REQUIRED = "required"; //$NON-NLS-1$
	public static String PROP_ENABLE = "enable"; //$NON-NLS-1$
	public static String PROP_ID = "id"; //$NON-NLS-1$
	public static String PROP_TRANSLATED_NAME = "translatedName"; //$NON-NLS-1$

	// constructors
	public BaseMenuModifierGroup() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMenuModifierGroup(java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	protected void initialize() {
	}

	// fields

	@Column(name = "full_name")
	protected java.lang.String name;

	@Column(name = "translatedName")
	protected java.lang.String translatedName;

	@Column(name = "enable")
	protected java.lang.Boolean enable;

	@Column(name = "exclusive")
	protected java.lang.Boolean exclusive;

	@Column(name = "required")
	protected java.lang.Boolean required;

	// collections
	@ManyToMany
	@JoinTable(name = "vpos_menu_modifier_group_menu_modifier", inverseJoinColumns = { @JoinColumn(name = "menu_modifier_group_id") }, joinColumns = {
			@JoinColumn(name = "menu_menu_modifier_id") })
	private java.util.Set<MenuModifier> modifiers;

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
	 * Return the value associated with the column: TRANSLATED_NAME
	 */
	public java.lang.String getTranslatedName() {
		return translatedName;
	}

	/**
	 * Set the value related to the column: TRANSLATED_NAME
	 * 
	 * @param translatedName
	 *          the TRANSLATED_NAME value
	 */
	public void setTranslatedName(java.lang.String translatedName) {
		this.translatedName = translatedName;
	}

	/**
	 * Return the value associated with the column: ENABLED
	 */
	public java.lang.Boolean isEnable() {
		return enable == null ? Boolean.FALSE : enable;
	}

	/**
	 * Set the value related to the column: ENABLED
	 * 
	 * @param enable
	 *          the ENABLED value
	 */
	public void setEnable(java.lang.Boolean enable) {
		this.enable = enable;
	}

	/**
	 * Return the value associated with the column: EXCLUSIVED
	 */
	public java.lang.Boolean isExclusive() {
		return exclusive == null ? Boolean.FALSE : exclusive;
	}

	/**
	 * Set the value related to the column: EXCLUSIVED
	 * 
	 * @param exclusive
	 *          the EXCLUSIVED value
	 */
	public void setExclusive(java.lang.Boolean exclusive) {
		this.exclusive = exclusive;
	}

	/**
	 * Return the value associated with the column: REQUIRED
	 */
	public java.lang.Boolean isRequired() {
		return required == null ? Boolean.FALSE : required;
	}

	/**
	 * Set the value related to the column: REQUIRED
	 * 
	 * @param required
	 *          the REQUIRED value
	 */
	public void setRequired(java.lang.Boolean required) {
		this.required = required;
	}

	/**
	 * Return the value associated with the column: modifiers
	 */
	public java.util.Set<MenuModifier> getModifiers() {
		return modifiers;
	}

	/**
	 * Set the value related to the column: modifiers
	 * 
	 * @param modifiers
	 *          the modifiers value
	 */
	public void setModifiers(java.util.Set<MenuModifier> modifiers) {
		this.modifiers = modifiers;
	}

	public void addTomodifiers(MenuModifier menuModifier) {
		if (null == getModifiers())
			setModifiers(new java.util.TreeSet<MenuModifier>());
		getModifiers().add(menuModifier);
	}

}