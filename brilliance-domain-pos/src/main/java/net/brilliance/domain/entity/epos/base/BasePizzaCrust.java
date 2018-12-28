package net.brilliance.domain.entity.epos.base;

import javax.persistence.Column;

import net.brilliance.framework.entity.BizObjectBase;

/**
 * This is an object that contains data related to the PIZZA_CRUST table. Do not modify this class because it will be overwritten if the configuration file related to this class is
 * modified.
 *
 * @hibernate.class table="PIZZA_CRUST"
 */

public abstract class BasePizzaCrust extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7323189293830170263L;
	public static String REF = "PizzaCrust"; //$NON-NLS-1$
	public static String PROP_DESCRIPTION = "description"; //$NON-NLS-1$
	public static String PROP_TRANSLATED_NAME = "translatedName"; //$NON-NLS-1$
	public static String PROP_SORT_ORDER = "sortOrder"; //$NON-NLS-1$
	public static String PROP_DEFAULT_CRUST = "defaultCrust"; //$NON-NLS-1$
	public static String PROP_ID = "id"; //$NON-NLS-1$
	public static String PROP_NAME = "name"; //$NON-NLS-1$

	// constructors
	public BasePizzaCrust() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BasePizzaCrust(java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	protected void initialize() {
	}

	// fields

	@Column(name = "name")
	protected java.lang.String name;

	@Column(name = "translatedName")
	protected java.lang.String translatedName;

	@Column(name = "description")
	protected java.lang.String description;

	@Column(name = "sortOrder")
	protected java.lang.Integer sortOrder;

	@Column(name = "defaultCrust")
	protected java.lang.Boolean defaultCrust;

	/**
	 * Return the value associated with the column: NAME
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * Set the value related to the column: NAME
	 * 
	 * @param name
	 *          the NAME value
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * Return the value associated with the column: TRANSLATED_NAME
	 */
	public java.lang.String getTranslatedName() {
		return translatedName;
	}

	/**
	 * Set the value related to the column: TRANSLATED_NAME
	 * 
	 * @param translatedName
	 *          the TRANSLATED_NAME value
	 */
	public void setTranslatedName(java.lang.String translatedName) {
		this.translatedName = translatedName;
	}

	/**
	 * Return the value associated with the column: DESCRIPTION
	 */
	public java.lang.String getDescription() {
		return description;
	}

	/**
	 * Set the value related to the column: DESCRIPTION
	 * 
	 * @param description
	 *          the DESCRIPTION value
	 */
	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	/**
	 * Return the value associated with the column: SORT_ORDER
	 */
	public java.lang.Integer getSortOrder() {
		return sortOrder == null ? Integer.valueOf(0) : sortOrder;
	}

	/**
	 * Set the value related to the column: SORT_ORDER
	 * 
	 * @param sortOrder
	 *          the SORT_ORDER value
	 */
	public void setSortOrder(java.lang.Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * Return the value associated with the column: DEFAULT_CRUST
	 */
	public java.lang.Boolean isDefaultCrust() {
		return defaultCrust == null ? Boolean.FALSE : defaultCrust;
	}

	/**
	 * Set the value related to the column: DEFAULT_CRUST
	 * 
	 * @param defaultCrust
	 *          the DEFAULT_CRUST value
	 */
	public void setDefaultCrust(java.lang.Boolean defaultCrust) {
		this.defaultCrust = defaultCrust;
	}
}