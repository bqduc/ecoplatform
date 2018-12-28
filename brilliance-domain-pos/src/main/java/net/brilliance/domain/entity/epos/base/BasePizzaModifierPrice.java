package net.brilliance.domain.entity.epos.base;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import net.brilliance.domain.entity.epos.MenuItemSize;
import net.brilliance.domain.entity.epos.ModifierMultiplierPrice;
import net.brilliance.framework.entity.BizObjectBase;


/**
 * This is an object that contains data related to the PIZZA_MODIFIER_PRICE table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="PIZZA_MODIFIER_PRICE"
 */
@MappedSuperclass
public abstract class BasePizzaModifierPrice extends BizObjectBase {
	private static final long serialVersionUID = -4909096607129297097L;
	public static String REF = "PizzaModifierPrice"; //$NON-NLS-1$
	public static String PROP_ID = "id"; //$NON-NLS-1$
	public static String PROP_SIZE = "size"; //$NON-NLS-1$


	// constructors
	public BasePizzaModifierPrice () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BasePizzaModifierPrice (java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}

	// many to one
	@ManyToOne
	@JoinColumn(name="MenuItemSize_id")
	private MenuItemSize size;

	// collections
	@ElementCollection
	@CollectionTable(name = "multiplierPriceList")
	private java.util.List<ModifierMultiplierPrice> multiplierPriceList;

	/**
	 * Return the value associated with the column: ITEM_SIZE
	 */
	public MenuItemSize getSize () {
					return size;
			}

	/**
	 * Set the value related to the column: ITEM_SIZE
	 * @param size the ITEM_SIZE value
	 */
	public void setSize (MenuItemSize size) {
		this.size = size;
	}



	/**
	 * Return the value associated with the column: multiplierPriceList
	 */
	public java.util.List<ModifierMultiplierPrice> getMultiplierPriceList () {
					return multiplierPriceList;
			}

	/**
	 * Set the value related to the column: multiplierPriceList
	 * @param multiplierPriceList the multiplierPriceList value
	 */
	public void setMultiplierPriceList (java.util.List<ModifierMultiplierPrice> multiplierPriceList) {
		this.multiplierPriceList = multiplierPriceList;
	}

	public void addTomultiplierPriceList (ModifierMultiplierPrice modifierMultiplierPrice) {
		if (null == getMultiplierPriceList()) setMultiplierPriceList(new java.util.ArrayList<ModifierMultiplierPrice>());
		getMultiplierPriceList().add(modifierMultiplierPrice);
	}

}