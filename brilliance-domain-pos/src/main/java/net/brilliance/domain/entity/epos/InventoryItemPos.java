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

import org.springframework.util.StringUtils;

import lombok.EqualsAndHashCode;
import net.brilliance.common.CommonUtility;
import net.brilliance.domain.entity.epos.base.BaseInventoryItem;



@Entity
@Table(name = "vpos_inventory_item")
@EqualsAndHashCode(callSuper = true)
public class InventoryItemPos extends BaseInventoryItem {
	private static final long serialVersionUID = 1L;

	public InventoryItemPos () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public InventoryItemPos (Long id) {
		super(id);
	}

	@Override
	public String toString() {
		return getName();
	}
	
	public static InventoryItemPos fromCSV(String csvLine) {
		if(StringUtils.isEmpty(csvLine)) {
			return null;
		}
		
		String[] strings = csvLine.split(","); //$NON-NLS-1$
		
		InventoryItemPos inventoryItem = new InventoryItemPos();
		
		int index = 0;
		
		try {
			
//			"NAME", "UNIT_PER_PACKAGE", "TOTAL_PACKAGES", "AVERAGE_PACKAGE_PRICE", "TOTAL_RECEPIE_UNITS",
//			"UNIT_PURCHASE_PRICE", "PACKAGE_BARCODE", "UNIT_BARCODE", "PACKAGE_DESC", "SORT_ORDER", "PACKAGE_REORDER_LEVEL",
//			"PACKAGE_REPLENISH_LEVEL","DESCRIPTION","UNIT_SELLING_PRICE"
			
			inventoryItem.setName(strings[index++]);
			inventoryItem.setUnitPerPackage(CommonUtility.parseDouble(strings[index++]));
			inventoryItem.setTotalPackages(CommonUtility.parseDouble(strings[index++]));
			inventoryItem.setAveragePackagePrice(CommonUtility.parseDouble(strings[index++]));
			inventoryItem.setTotalRecepieUnits(CommonUtility.parseDouble(strings[index++]));
			inventoryItem.setUnitPurchasePrice(CommonUtility.parseDouble(strings[index++]));
			inventoryItem.setPackageBarcode(strings[index++]);
			inventoryItem.setUnitBarcode(strings[index++]);
//			inventoryItem.setPackagingUnit(strings[index++]);
			inventoryItem.setSortOrder(CommonUtility.parseInteger(strings[index++]));
			inventoryItem.setPackageReorderLevel(CommonUtility.parseInteger(strings[index++]));
			inventoryItem.setPackageReplenishLevel(CommonUtility.parseInteger(strings[index++]));
			inventoryItem.setDescription(strings[index++]);
			inventoryItem.setUnitSellingPrice(CommonUtility.parseDouble(strings[index++]));
			

		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		return inventoryItem;
	}

}