package net.brilliance.domain.entity.epos;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.epos.base.BaseMultiplier;

@Entity
@Table(name = "vpos_multiplier")
@EqualsAndHashCode(callSuper = true)
public class Multiplier extends BaseMultiplier {
	private static final long serialVersionUID = 1L;
	public static final String REGULAR = "Regular";

	public Multiplier() {
		super();
	}

	public Multiplier(java.lang.String name) {
		super(name);
	}

	@Override
	public Integer getButtonColor() {
		return buttonColor;
	}

	@Override
	public Integer getTextColor() {
		return textColor;
	}

}