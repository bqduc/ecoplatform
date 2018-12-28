package net.brilliance.domain.entity.epos.base;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.MappedSuperclass;

import net.brilliance.domain.entity.epos.MenuCategory;
import net.brilliance.domain.entity.epos.MenuGroup;
import net.brilliance.domain.entity.epos.MenuItem;
import net.brilliance.framework.entity.BizObjectBase;


/**
 * This is an object that contains data related to the COUPON_AND_DISCOUNT table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="COUPON_AND_DISCOUNT"
 */
@MappedSuperclass
public abstract class BaseDiscount extends BizObjectBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2105208706961100883L;
	public static String REF = "Discount";
	public static String PROP_EXPIRY_DATE = "expiryDate";
	public static String PROP_ENABLED = "enabled";
	public static String PROP_MINIMUN_BUY = "minimunBuy";
	public static String PROP_MODIFIABLE = "modifiable";
	public static String PROP_NAME = "name";
	public static String PROP_APPLY_TO_ALL = "applyToAll";
	public static String PROP_MIXIMUM_OFF = "miximumOff";
	public static String PROP_AUTO_APPLY = "autoApply";
	public static String PROP_TYPE = "type";
	public static String PROP_QUALIFICATION_TYPE = "qualificationType";
	public static String PROP_NEVER_EXPIRE = "neverExpire";
	public static String PROP_BARCODE = "barcode";
	public static String PROP_VALUE = "value";
	public static String PROP_ID = "id";
	public static String PROP_UUID = "UUID";


	// constructors
	public BaseDiscount () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDiscount (java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	// fields
	
	@Column(name = "name")
	protected java.lang.String name;

	@Column(name = "type")
	protected java.lang.Integer type;

	@Column(name = "barcode")
	protected java.lang.String barcode;

	@Column(name = "qualificationType")
	protected java.lang.Integer qualificationType;

	@Column(name = "applyToAll")
	protected java.lang.Boolean applyToAll;

	@Column(name = "minimunBuy")
	protected java.lang.Integer minimunBuy;

	@Column(name = "miximumOff")
	protected java.lang.Integer miximumOff;

	@Column(name = "value")
	protected java.lang.Double value;

	@Column(name = "expiryDate")
	protected java.util.Date expiryDate;

	@Column(name = "enabled")
	protected java.lang.Boolean enabled;

	@Column(name = "autoApply")
	protected java.lang.Boolean autoApply;

	@Column(name = "modifiable")
	protected java.lang.Boolean modifiable;

	@Column(name = "neverExpire")
	protected java.lang.Boolean neverExpire;

	@Column(name = "uUID")
	protected java.lang.String uUID;

	// collections
	@ElementCollection
	@CollectionTable(name = "menuItems")
	private java.util.List<MenuItem> menuItems;

	@ElementCollection
	@CollectionTable(name = "menuGroups")
	private java.util.List<MenuGroup> menuGroups;

	@ElementCollection
	@CollectionTable(name = "menuCategories")
	private java.util.List<MenuCategory> menuCategories;

	/**
	 * Return the value associated with the column: NAME
	 */
	public java.lang.String getName () {
					return name;
			}

	/**
	 * Set the value related to the column: NAME
	 * @param name the NAME value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: TYPE
	 */
	public java.lang.Integer getType () {
									return type == null ? Integer.valueOf(0) : type;
					}

	/**
	 * Set the value related to the column: TYPE
	 * @param type the TYPE value
	 */
	public void setType (java.lang.Integer type) {
		this.type = type;
	}



	/**
	 * Return the value associated with the column: BARCODE
	 */
	public java.lang.String getBarcode () {
					return barcode;
			}

	/**
	 * Set the value related to the column: BARCODE
	 * @param barcode the BARCODE value
	 */
	public void setBarcode (java.lang.String barcode) {
		this.barcode = barcode;
	}



	/**
	 * Return the value associated with the column: QUALIFICATION_TYPE
	 */
	public java.lang.Integer getQualificationType () {
									return qualificationType == null ? Integer.valueOf(0) : qualificationType;
					}

	/**
	 * Set the value related to the column: QUALIFICATION_TYPE
	 * @param qualificationType the QUALIFICATION_TYPE value
	 */
	public void setQualificationType (java.lang.Integer qualificationType) {
		this.qualificationType = qualificationType;
	}



	/**
	 * Return the value associated with the column: APPLY_TO_ALL
	 */
	public java.lang.Boolean isApplyToAll () {
								return applyToAll == null ? Boolean.FALSE : applyToAll;
					}

	/**
	 * Set the value related to the column: APPLY_TO_ALL
	 * @param applyToAll the APPLY_TO_ALL value
	 */
	public void setApplyToAll (java.lang.Boolean applyToAll) {
		this.applyToAll = applyToAll;
	}



	/**
	 * Return the value associated with the column: MINIMUM_BUY
	 */
	public java.lang.Integer getMinimunBuy () {
									return minimunBuy == null ? Integer.valueOf(0) : minimunBuy;
					}

	/**
	 * Set the value related to the column: MINIMUM_BUY
	 * @param minimunBuy the MINIMUM_BUY value
	 */
	public void setMinimunBuy (java.lang.Integer minimunBuy) {
		this.minimunBuy = minimunBuy;
	}



	/**
	 * Return the value associated with the column: MAXIMUM_OFF
	 */
	public java.lang.Integer getMiximumOff () {
									return miximumOff == null ? Integer.valueOf(0) : miximumOff;
					}

	/**
	 * Set the value related to the column: MAXIMUM_OFF
	 * @param miximumOff the MAXIMUM_OFF value
	 */
	public void setMiximumOff (java.lang.Integer miximumOff) {
		this.miximumOff = miximumOff;
	}



	/**
	 * Return the value associated with the column: VALUE
	 */
	public java.lang.Double getValue () {
									return value == null ? Double.valueOf(0) : value;
					}

	/**
	 * Set the value related to the column: VALUE
	 * @param value the VALUE value
	 */
	public void setValue (java.lang.Double value) {
		this.value = value;
	}



	/**
	 * Return the value associated with the column: EXPIRY_DATE
	 */
	public java.util.Date getExpiryDate () {
					return expiryDate;
			}

	/**
	 * Set the value related to the column: EXPIRY_DATE
	 * @param expiryDate the EXPIRY_DATE value
	 */
	public void setExpiryDate (java.util.Date expiryDate) {
		this.expiryDate = expiryDate;
	}



	/**
	 * Return the value associated with the column: ENABLED
	 */
	public java.lang.Boolean isEnabled () {
								return enabled == null ? Boolean.FALSE : enabled;
					}

	/**
	 * Set the value related to the column: ENABLED
	 * @param enabled the ENABLED value
	 */
	public void setEnabled (java.lang.Boolean enabled) {
		this.enabled = enabled;
	}



	/**
	 * Return the value associated with the column: AUTO_APPLY
	 */
	public java.lang.Boolean isAutoApply () {
								return autoApply == null ? Boolean.FALSE : autoApply;
					}

	/**
	 * Set the value related to the column: AUTO_APPLY
	 * @param autoApply the AUTO_APPLY value
	 */
	public void setAutoApply (java.lang.Boolean autoApply) {
		this.autoApply = autoApply;
	}



	/**
	 * Return the value associated with the column: MODIFIABLE
	 */
	public java.lang.Boolean isModifiable () {
								return modifiable == null ? Boolean.FALSE : modifiable;
					}

	/**
	 * Set the value related to the column: MODIFIABLE
	 * @param modifiable the MODIFIABLE value
	 */
	public void setModifiable (java.lang.Boolean modifiable) {
		this.modifiable = modifiable;
	}



	/**
	 * Return the value associated with the column: NEVER_EXPIRE
	 */
	public java.lang.Boolean isNeverExpire () {
								return neverExpire == null ? Boolean.FALSE : neverExpire;
					}

	/**
	 * Set the value related to the column: NEVER_EXPIRE
	 * @param neverExpire the NEVER_EXPIRE value
	 */
	public void setNeverExpire (java.lang.Boolean neverExpire) {
		this.neverExpire = neverExpire;
	}



	/**
	 * Return the value associated with the column: UUID
	 */
	public java.lang.String getUUID () {
					return uUID;
			}

	/**
	 * Set the value related to the column: UUID
	 * @param uUID the UUID value
	 */
	public void setUUID (java.lang.String uUID) {
		this.uUID = uUID;
	}



	/**
	 * Return the value associated with the column: menuItems
	 */
	public java.util.List<MenuItem> getMenuItems () {
					return menuItems;
			}

	/**
	 * Set the value related to the column: menuItems
	 * @param menuItems the menuItems value
	 */
	public void setMenuItems (java.util.List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public void addTomenuItems (MenuItem menuItem) {
		if (null == getMenuItems()) setMenuItems(new java.util.ArrayList<MenuItem>());
		getMenuItems().add(menuItem);
	}



	/**
	 * Return the value associated with the column: menuGroups
	 */
	public java.util.List<MenuGroup> getMenuGroups () {
					return menuGroups;
			}

	/**
	 * Set the value related to the column: menuGroups
	 * @param menuGroups the menuGroups value
	 */
	public void setMenuGroups (java.util.List<MenuGroup> menuGroups) {
		this.menuGroups = menuGroups;
	}

	public void addTomenuGroups (MenuGroup menuGroup) {
		if (null == getMenuGroups()) setMenuGroups(new java.util.ArrayList<MenuGroup>());
		getMenuGroups().add(menuGroup);
	}



	/**
	 * Return the value associated with the column: menuCategories
	 */
	public java.util.List<MenuCategory> getMenuCategories () {
					return menuCategories;
			}

	/**
	 * Set the value related to the column: menuCategories
	 * @param menuCategories the menuCategories value
	 */
	public void setMenuCategories (java.util.List<MenuCategory> menuCategories) {
		this.menuCategories = menuCategories;
	}

	public void addTomenuCategories (MenuCategory menuCategory) {
		if (null == getMenuCategories()) setMenuCategories(new java.util.ArrayList<MenuCategory>());
		getMenuCategories().add(menuCategory);
	}

}