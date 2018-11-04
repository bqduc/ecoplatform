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
import net.brilliance.domain.entity.epos.base.BaseMenuItemShift;

@Entity
@Table(name = "vpos_menu_item_shift")
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name="menu-item-shift")
public class MenuItemShift extends BaseMenuItemShift {
	private static final long serialVersionUID = 1L;

	public MenuItemShift () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MenuItemShift (java.lang.Long id) {
		super(id);
	}
}