package net.brilliance.domain.entity.epos.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import net.brilliance.framework.entity.BizObjectBase;

/**
 * This is an object that contains data related to the MENU_ITEM_SIZE table. Do not modify this class because it will be overwritten if the configuration file related to this class
 * is modified.
 *
 * @hibernate.class table="MENU_ITEM_SIZE"
 */
@MappedSuperclass
public abstract class BaseMenuItemSize extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1940936369022657057L;
	public static String REF = "MenuItemSize"; //$NON-NLS-1$
	public static String PROP_DESCRIPTION = "description"; //$NON-NLS-1$
	public static String PROP_TRANSLATED_NAME = "translatedName"; //$NON-NLS-1$
	public static String PROP_SIZE_IN_INCH = "sizeInInch"; //$NON-NLS-1$
	public static String PROP_SORT_ORDER = "sortOrder"; //$NON-NLS-1$
	public static String PROP_ID = "id"; //$NON-NLS-1$
	public static String PROP_NAME = "name"; //$NON-NLS-1$
	public static String PROP_DEFAULT_SIZE = "defaultSize"; //$NON-NLS-1$

	// constructors
	public BaseMenuItemSize() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMenuItemSize(java.lang.Long id) {
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

	@Column(name = "sizeInInch")
	protected java.lang.Double sizeInInch;

	@Column(name = "defaultSize")
	protected java.lang.Boolean defaultSize;

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
	 * Return the value associated with the column: SIZE_IN_INCH
	 */
	public java.lang.Double getSizeInInch() {
		return sizeInInch == null ? Double.valueOf(0) : sizeInInch;
	}

	/**
	 * Set the value related to the column: SIZE_IN_INCH
	 * 
	 * @param sizeInInch
	 *          the SIZE_IN_INCH value
	 */
	public void setSizeInInch(java.lang.Double sizeInInch) {
		this.sizeInInch = sizeInInch;
	}

	/**
	 * Return the value associated with the column: DEFAULT_SIZE
	 */
	public java.lang.Boolean isDefaultSize() {
		return defaultSize == null ? Boolean.FALSE : defaultSize;
	}

	/**
	 * Set the value related to the column: DEFAULT_SIZE
	 * 
	 * @param defaultSize
	 *          the DEFAULT_SIZE value
	 */
	public void setDefaultSize(java.lang.Boolean defaultSize) {
		this.defaultSize = defaultSize;
	}
}