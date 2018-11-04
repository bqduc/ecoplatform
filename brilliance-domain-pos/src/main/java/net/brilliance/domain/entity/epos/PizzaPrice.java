package net.brilliance.domain.entity.epos;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.epos.base.BasePizzaPrice;

@Entity
@Table(name = "vpos_pizza_price")
@EqualsAndHashCode(callSuper = true)
public class PizzaPrice extends BasePizzaPrice {
	private static final long serialVersionUID = 1L;

	public PizzaPrice() {
		super();
	}

	public PizzaPrice(java.lang.Long id) {
		super(id);
	}

	public Double getPrice(int defaultSellPortion) {
		return super.getPrice() * defaultSellPortion / 100;
	}

}