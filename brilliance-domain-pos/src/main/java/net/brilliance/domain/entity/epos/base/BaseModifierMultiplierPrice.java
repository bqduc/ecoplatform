package net.brilliance.domain.entity.epos.base;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import net.brilliance.domain.entity.epos.MenuModifier;
import net.brilliance.domain.entity.epos.Multiplier;
import net.brilliance.domain.entity.epos.PizzaModifierPrice;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * This is an object that contains data related to the MODIFIER_MULTIPLIER_PRICE
 * table. Do not modify this class because it will be overwritten if the
 * configuration file related to this class is modified.
 *
 * @hibernate.class table="MODIFIER_MULTIPLIER_PRICE"
 */
@MappedSuperclass
public abstract class BaseModifierMultiplierPrice extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2761130047830755181L;
	public static String REF = "ModifierMultiplierPrice"; //$NON-NLS-1$
	public static String PROP_PIZZA_MODIFIER_PRICE = "pizzaModifierPrice"; //$NON-NLS-1$
	public static String PROP_PRICE = "price"; //$NON-NLS-1$
	public static String PROP_ID = "id"; //$NON-NLS-1$
	public static String PROP_MODIFIER = "modifier"; //$NON-NLS-1$
	public static String PROP_MULTIPLIER = "multiplier"; //$NON-NLS-1$

	// constructors
	public BaseModifierMultiplierPrice() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseModifierMultiplierPrice(java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	protected void initialize() {
	}

	// fields
	@Column(name = "price")
	protected java.lang.Double price;

	// many to one
	@ManyToOne
	@JoinColumn(name = "multiplier_id")
	private Multiplier multiplier;

	@ManyToOne
	@JoinColumn(name = "modifier_id")
	private MenuModifier modifier;

	@ManyToOne
	@JoinColumn(name = "pizzaModifierPrice_id")
	private PizzaModifierPrice pizzaModifierPrice;

	/**
	 * Return the value associated with the column: PRICE
	 */
	public java.lang.Double getPrice() {
		return price;
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
	 * Custom property
	 */
	public static String getPriceDefaultValue() {
		return "null";
	}

	/**
	 * Return the value associated with the column: MULTIPLIER_ID
	 */
	public Multiplier getMultiplier() {
		return multiplier;
	}

	/**
	 * Set the value related to the column: MULTIPLIER_ID
	 * 
	 * @param multiplier
	 *          the MULTIPLIER_ID value
	 */
	public void setMultiplier(Multiplier multiplier) {
		this.multiplier = multiplier;
	}

	/**
	 * Return the value associated with the column: MENUMODIFIER_ID
	 */
	public MenuModifier getModifier() {
		return modifier;
	}

	/**
	 * Set the value related to the column: MENUMODIFIER_ID
	 * 
	 * @param modifier
	 *          the MENUMODIFIER_ID value
	 */
	public void setModifier(MenuModifier modifier) {
		this.modifier = modifier;
	}

	/**
	 * Return the value associated with the column: PIZZA_MODIFIER_PRICE_ID
	 */
	public PizzaModifierPrice getPizzaModifierPrice() {
		return pizzaModifierPrice;
	}

	/**
	 * Set the value related to the column: PIZZA_MODIFIER_PRICE_ID
	 * 
	 * @param pizzaModifierPrice
	 *          the PIZZA_MODIFIER_PRICE_ID value
	 */
	public void setPizzaModifierPrice(PizzaModifierPrice pizzaModifierPrice) {
		this.pizzaModifierPrice = pizzaModifierPrice;
	}

}