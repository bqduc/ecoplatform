package net.brilliance.domain.entity.epos;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import net.brilliance.domain.entity.epos.base.BaseOrderType;

@Entity
@Table(name = "vpos_order_type")
@EqualsAndHashCode(callSuper = true)
public class OrderType extends BaseOrderType {
	private static final long serialVersionUID = 1L;

	public static final String BAR_TAB = "BAR_TAB"; //$NON-NLS-1$
	public static final String FOR_HERE="FOR HERE";  //$NON-NLS-1$
	public static final String TO_GO="TO GO";  //$NON-NLS-1$

	public OrderType() {
		super();
	}

	public OrderType(java.lang.Long id) {
		super(id);
	}

	public OrderType(java.lang.Long id, java.lang.String name) {

		super(id, name);
	}

	public String name() {
		return super.getName();
	}

	public OrderType valueOf() {
		return this;
	}

	@Override
	public String toString() {
		return getName().replaceAll("_", " "); //$NON-NLS-1$ //$NON-NLS-2$
	}

}