package net.brilliance.domain.entity.epos.base;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import net.brilliance.domain.entity.epos.Discount;
import net.brilliance.domain.entity.epos.MenuCategory;
import net.brilliance.framework.entity.BizObjectBase;


/**
 * This is an object that contains data related to the MENU_GROUP table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="MENU_GROUP"
 */
@MappedSuperclass
public abstract class BaseMenuGroup extends BizObjectBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2957864584274337193L;
	public static String REF = "MenuGroup";
	public static String PROP_NAME = "name";
	public static String PROP_PARENT = "parent";
	public static String PROP_TEXT_COLOR_CODE = "textColorCode";
	public static String PROP_SORT_ORDER = "sortOrder";
	public static String PROP_BUTTON_COLOR_CODE = "buttonColorCode";
	public static String PROP_ID = "id";
	public static String PROP_TRANSLATED_NAME = "translatedName";


	// constructors
	public BaseMenuGroup () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMenuGroup (java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMenuGroup (
		java.lang.Long id,
		java.lang.String name) {

		this.setId(id);
		this.setName(name);
		initialize();
	}

	protected void initialize () {}

	// fields
	@Column
	protected java.lang.String name;

	@Column
	protected java.lang.String translatedName;
	
	@Column
	protected java.lang.Integer sortOrder;
	
	@Column
	protected java.lang.Integer buttonColorCode;
	
	@Column
	protected java.lang.Integer textColorCode;

	// many to one
	@ManyToOne
	@JoinColumn(name="parent_id")
	private MenuCategory parent;

	// collections
	@ElementCollection
	@CollectionTable(name = "vpos_discounts")
	private java.util.List<Discount> discounts;

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
	 * Return the value associated with the column: TRANSLATED_NAME
	 */
	public java.lang.String getTranslatedName () {
					return translatedName;
			}

	/**
	 * Set the value related to the column: TRANSLATED_NAME
	 * @param translatedName the TRANSLATED_NAME value
	 */
	public void setTranslatedName (java.lang.String translatedName) {
		this.translatedName = translatedName;
	}

	/**
	 * Return the value associated with the column: SORT_ORDER
	 */
	public java.lang.Integer getSortOrder () {
									return sortOrder == null ? Integer.valueOf(0) : sortOrder;
					}

	/**
	 * Set the value related to the column: SORT_ORDER
	 * @param sortOrder the SORT_ORDER value
	 */
	public void setSortOrder (java.lang.Integer sortOrder) {
		this.sortOrder = sortOrder;
	}



	/**
	 * Return the value associated with the column: BTN_COLOR
	 */
	public java.lang.Integer getButtonColorCode () {
									return buttonColorCode == null ? Integer.valueOf(0) : buttonColorCode;
					}

	/**
	 * Set the value related to the column: BTN_COLOR
	 * @param buttonColorCode the BTN_COLOR value
	 */
	public void setButtonColorCode (java.lang.Integer buttonColorCode) {
		this.buttonColorCode = buttonColorCode;
	}



	/**
	 * Return the value associated with the column: TEXT_COLOR
	 */
	public java.lang.Integer getTextColorCode () {
									return textColorCode == null ? Integer.valueOf(0) : textColorCode;
					}

	/**
	 * Set the value related to the column: TEXT_COLOR
	 * @param textColorCode the TEXT_COLOR value
	 */
	public void setTextColorCode (java.lang.Integer textColorCode) {
		this.textColorCode = textColorCode;
	}



	/**
	 * Return the value associated with the column: CATEGORY_ID
	 */
	public MenuCategory getParent () {
					return parent;
			}

	/**
	 * Set the value related to the column: CATEGORY_ID
	 * @param parent the CATEGORY_ID value
	 */
	public void setParent (MenuCategory parent) {
		this.parent = parent;
	}



	/**
	 * Return the value associated with the column: discounts
	 */
	public java.util.List<Discount> getDiscounts () {
					return discounts;
			}

	/**
	 * Set the value related to the column: discounts
	 * @param discounts the discounts value
	 */
	public void setDiscounts (java.util.List<Discount> discounts) {
		this.discounts = discounts;
	}

	public void addTodiscounts (Discount discount) {
		if (null == getDiscounts()) setDiscounts(new java.util.ArrayList<Discount>());
		getDiscounts().add(discount);
	}
}