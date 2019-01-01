package net.brilliance.domain.entity.general;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.base.BaseTaxGroup;

@Entity
@Table(name = "vpos_tax_group")
@EqualsAndHashCode(callSuper = true)
public class TaxGroup extends BaseTaxGroup {
	private static final long serialVersionUID = 1L;

	public TaxGroup() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TaxGroup(java.lang.Long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public TaxGroup(java.lang.Long id, java.lang.String name) {

		super(id, name);
	}

	@Override
	public String toString() {
		String name = super.getName();
		List<Tax> taxes = getTaxes();
		if (taxes == null || taxes.isEmpty()) {
			return name;
		}
		name += " (";
		for (Iterator<?> iterator = taxes.iterator(); iterator.hasNext();) {
			Tax tax = (Tax) iterator.next();
			name += tax.getName() + ":" + tax.getRate();
			if (iterator.hasNext()) {
				name += ", ";
			}
		}
		name += ")";
		return name;
	}

}