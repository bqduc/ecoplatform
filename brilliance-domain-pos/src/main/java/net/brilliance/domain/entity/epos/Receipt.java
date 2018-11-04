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

import java.util.ArrayList;
import java.util.List;

import net.brilliance.domain.entity.epos.base.BaseReceipt;

/*@Entity
@Table(name = "vpos_receipt")
@EqualsAndHashCode(callSuper = true)*/
public class Receipt extends BaseReceipt {
	private static final long serialVersionUID = 1L;

	public Receipt () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Receipt (java.lang.Long id) {
		super(id);
	}

	public void addRecepieItem(ReceiptItem recepieItem) {
		List<ReceiptItem> recepieItems = getRecepieItems();
		if(recepieItems == null) {
			recepieItems = new ArrayList<ReceiptItem>(3);
			setRecepieItems(recepieItems);
		}
		
		recepieItem.setRecepie(this);
		recepieItems.add(recepieItem);
	}

}