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
import javax.xml.bind.annotation.XmlRootElement;

import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.epos.base.BaseMenuItemModifierGroup;


@Entity
@Table(name = "vpos_menu_item_modifier_group")
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name="menuItemModifierGroup")
public class MenuItemModifierGroup extends BaseMenuItemModifierGroup {
	private static final long serialVersionUID = 1L;

	public MenuItemModifierGroup () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MenuItemModifierGroup (java.lang.Long id) {
		super(id);
	}

	@Override
	public String toString() {
		if(getModifierGroup() != null) {
			return getModifierGroup().getName();
		}
		return ""; //$NON-NLS-1$
	}

	public String getUniqueId() {
		return ("menuitem_modifiergroup_" + toString() + "_" + getId()).replaceAll("\\s+", "_"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

}