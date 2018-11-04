package net.brilliance.domain.entity.epos;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.epos.base.BaseModifierMultiplierPrice;

@Entity
@Table(name = "vpos_modifier_multiplier_price")
@EqualsAndHashCode(callSuper = true)
public class ModifierMultiplierPrice extends BaseModifierMultiplierPrice {
	private static final long serialVersionUID = 1L;

	public ModifierMultiplierPrice () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ModifierMultiplierPrice (java.lang.Long id) {
		super(id);
	}
}