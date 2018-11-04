package net.brilliance.domain.entity.epos;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.epos.base.BasePizzaCrust;

@Entity
@Table(name = "vpos_pizza_crust")
@EqualsAndHashCode(callSuper = true)
public class PizzaCrust extends BasePizzaCrust {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public PizzaCrust () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public PizzaCrust (java.lang.Long id) {
		super(id);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	@Override
	public String getTranslatedName() {
		String translatedName = super.getTranslatedName();
		if(StringUtils.isEmpty(translatedName)) {
			return getName();
		}
		
		return translatedName;
	}

	@Override
	public String toString() {
		return getName();
	}
}