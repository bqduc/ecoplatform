package net.brilliance.domain.entity.epos.base;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import net.brilliance.domain.entity.epos.MenuItemSize;
import net.brilliance.domain.entity.epos.OrderType;
import net.brilliance.domain.entity.epos.PizzaCrust;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * This is an object that contains data related to the PIZZA_PRICE table. Do not modify this class because it will be overwritten if the configuration file related to this class is
 * modified.
 *
 * @hibernate.class table="PIZZA_PRICE"
 */
@MappedSuperclass
public abstract class BasePizzaPrice extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2976920559705189683L;
	public static String REF = "PizzaPrice"; //$NON-NLS-1$
	public static String PROP_CRUST = "crust"; //$NON-NLS-1$
	public static String PROP_ORDER_TYPE = "orderType"; //$NON-NLS-1$
	public static String PROP_PRICE = "price"; //$NON-NLS-1$
	public static String PROP_SIZE = "size"; //$NON-NLS-1$
	public static String PROP_ID = "id"; //$NON-NLS-1$

	// constructors
	public BasePizzaPrice() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BasePizzaPrice(java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	protected void initialize() {
	}

	// fields
	protected java.lang.Double price;

	// many to one
	@ManyToOne
	@JoinColumn(name = "size_id")
	private MenuItemSize size;

	@ManyToOne
	@JoinColumn(name = "crust_id")
	private PizzaCrust crust;

	@ManyToOne
	@JoinColumn(name = "orderType_id")
	private OrderType orderType;

	/**
	 * Return the value associated with the column: PRICE
	 */
	public java.lang.Double getPrice() {
		return price == null ? Double.valueOf(0) : price;
	}

	/**
	 * Set the value related to the column: PRICE
	 * 
	 * @param price
	 *          the PRICE value
	 */
	public void setPrice(java.lang.Double price) {
		this.price = price;
	}

	/**
	 * Return the value associated with the column: MENU_ITEM_SIZE
	 */
	public MenuItemSize getSize() {
		return size;
	}

	/**
	 * Set the value related to the column: MENU_ITEM_SIZE
	 * 
	 * @param size
	 *          the MENU_ITEM_SIZE value
	 */
	public void setSize(MenuItemSize size) {
		this.size = size;
	}

	/**
	 * Return the value associated with the column: CRUST
	 */
	public PizzaCrust getCrust() {
		return crust;
	}

	/**
	 * Set the value related to the column: CRUST
	 * 
	 * @param crust
	 *          the CRUST value
	 */
	public void setCrust(PizzaCrust crust) {
		this.crust = crust;
	}

	/**
	 * Return the value associated with the column: ORDER_TYPE
	 */
	public OrderType getOrderType() {
		return orderType;
	}

	/**
	 * Set the value related to the column: ORDER_TYPE
	 * 
	 * @param orderType
	 *          the ORDER_TYPE value
	 */
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
}