package net.brilliance.domain.entity.epos.base;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import net.brilliance.domain.entity.epos.MenuModifierGroup;
import net.brilliance.domain.entity.epos.ModifierMultiplierPrice;
import net.brilliance.domain.entity.epos.PizzaModifierPrice;
import net.brilliance.domain.entity.general.Tax;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * This is an object that contains data related to the MENU_MODIFIER table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="MENU_MODIFIER"
 */
@MappedSuperclass
public abstract class BaseMenuModifier extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3537581313751564729L;
	public static String REF = "MenuModifier"; //$NON-NLS-1$
	public static String PROP_SHOULD_PRINT_TO_KITCHEN = "shouldPrintToKitchen"; //$NON-NLS-1$
	public static String PROP_EXTRA_PRICE = "extraPrice"; //$NON-NLS-1$
	public static String PROP_MODIFIER_GROUP = "modifierGroup"; //$NON-NLS-1$
	public static String PROP_SORT_ORDER = "sortOrder"; //$NON-NLS-1$
	public static String PROP_TAX = "tax"; //$NON-NLS-1$
	public static String PROP_NAME = "name"; //$NON-NLS-1$
	public static String PROP_BUTTON_COLOR = "buttonColor"; //$NON-NLS-1$
	public static String PROP_TRANSLATED_NAME = "translatedName"; //$NON-NLS-1$
	public static String PROP_PRICE = "price"; //$NON-NLS-1$
	public static String PROP_SHOULD_SECTION_WISE_PRICE = "shouldSectionWisePrice"; //$NON-NLS-1$
	public static String PROP_ENABLE = "enable"; //$NON-NLS-1$
	public static String PROP_TEXT_COLOR = "textColor"; //$NON-NLS-1$
	public static String PROP_PIZZA_MODIFIER = "pizzaModifier"; //$NON-NLS-1$
	public static String PROP_ID = "id"; //$NON-NLS-1$
	public static String PROP_FIXED_PRICE = "fixedPrice"; //$NON-NLS-1$

	// constructors
	public BaseMenuModifier() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMenuModifier(java.lang.Long id) {
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

	@Column(name = "price")
	protected java.lang.Double price;

	@Column(name = "extraPrice")
	protected java.lang.Double extraPrice;

	@Column(name = "sortOrder")
	protected java.lang.Integer sortOrder;

	@Column(name = "buttonColor")
	protected java.lang.Integer buttonColor;

	@Column(name = "textColor")
	protected java.lang.Integer textColor;

	@Column(name = "enable")
	protected java.lang.Boolean enable;

	@Column(name = "fixedPrice")
	protected java.lang.Boolean fixedPrice;

	@Column(name = "shouldPrintToKitchen")
	protected java.lang.Boolean shouldPrintToKitchen;

	@Column(name = "shouldSectionWisePrice")
	protected java.lang.Boolean shouldSectionWisePrice;

	@Column(name = "pizzaModifier")
	protected java.lang.Boolean pizzaModifier;

	// many to one
	@ManyToOne
	@JoinColumn(name="modifierGroup_id")
	private MenuModifierGroup modifierGroup;

	@ManyToOne
	@JoinColumn(name="tax_id")
	private Tax tax;

	// collections

	@ManyToMany
	@JoinTable(
			name = "vpos_menu_modifier_pizza_modifier_price", 
			inverseJoinColumns = {@JoinColumn(name = "menu_modifier_id")},
			joinColumns = {@JoinColumn(name = "menu_pizza_modifier_price_id")}
	)
	private java.util.List<PizzaModifierPrice> pizzaModifierPriceList;

	@ManyToMany
	@JoinTable(
			name = "vpos_menu_modifier_multiplier_price", 
			inverseJoinColumns = {@JoinColumn(name = "menu_modifier_id")},
			joinColumns = {@JoinColumn(name = "menu_modifier_multiplier_price_id")}
	)
	private java.util.List<ModifierMultiplierPrice> multiplierPriceList;

	//ducbq
	@Transient
	private java.util.Map<String, String> properties;

	/**
	 * Return the value associated with the column: NAME
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * Set the value related to the column: NAME
	 * @param name the NAME value
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
	 * @param translatedName the TRANSLATED_NAME value
	 */
	public void setTranslatedName(java.lang.String translatedName) {
		this.translatedName = translatedName;
	}

	/**
	 * Return the value associated with the column: PRICE
	 */
	public java.lang.Double getPrice() {
		return price == null ? Double.valueOf(0) : price;
	}

	/**
	 * Set the value related to the column: PRICE
	 * @param price the PRICE value
	 */
	public void setPrice(java.lang.Double price) {
		this.price = price;
	}

	/**
	 * Return the value associated with the column: EXTRA_PRICE
	 */
	public java.lang.Double getExtraPrice() {
		return extraPrice == null ? Double.valueOf(0) : extraPrice;
	}

	/**
	 * Set the value related to the column: EXTRA_PRICE
	 * @param extraPrice the EXTRA_PRICE value
	 */
	public void setExtraPrice(java.lang.Double extraPrice) {
		this.extraPrice = extraPrice;
	}

	/**
	 * Return the value associated with the column: SORT_ORDER
	 */
	public java.lang.Integer getSortOrder() {
		return sortOrder == null ? Integer.valueOf(0) : sortOrder;
	}

	/**
	 * Set the value related to the column: SORT_ORDER
	 * @param sortOrder the SORT_ORDER value
	 */
	public void setSortOrder(java.lang.Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * Return the value associated with the column: BTN_COLOR
	 */
	public java.lang.Integer getButtonColor() {
		return buttonColor == null ? Integer.valueOf(0) : buttonColor;
	}

	/**
	 * Set the value related to the column: BTN_COLOR
	 * @param buttonColor the BTN_COLOR value
	 */
	public void setButtonColor(java.lang.Integer buttonColor) {
		this.buttonColor = buttonColor;
	}

	/**
	 * Return the value associated with the column: TEXT_COLOR
	 */
	public java.lang.Integer getTextColor() {
		return textColor == null ? Integer.valueOf(0) : textColor;
	}

	/**
	 * Set the value related to the column: TEXT_COLOR
	 * @param textColor the TEXT_COLOR value
	 */
	public void setTextColor(java.lang.Integer textColor) {
		this.textColor = textColor;
	}

	/**
	 * Return the value associated with the column: ENABLE
	 */
	public java.lang.Boolean isEnable() {
		return enable == null ? Boolean.FALSE : enable;
	}

	/**
	 * Set the value related to the column: ENABLE
	 * @param enable the ENABLE value
	 */
	public void setEnable(java.lang.Boolean enable) {
		this.enable = enable;
	}

	/**
	 * Return the value associated with the column: FIXED_PRICE
	 */
	public java.lang.Boolean isFixedPrice() {
		return fixedPrice == null ? Boolean.FALSE : fixedPrice;
	}

	/**
	 * Set the value related to the column: FIXED_PRICE
	 * @param fixedPrice the FIXED_PRICE value
	 */
	public void setFixedPrice(java.lang.Boolean fixedPrice) {
		this.fixedPrice = fixedPrice;
	}

	/**
	 * Return the value associated with the column: PRINT_TO_KITCHEN
	 */
	public java.lang.Boolean isShouldPrintToKitchen() {
		return shouldPrintToKitchen == null ? Boolean.valueOf(true) : shouldPrintToKitchen;
	}

	/**
	 * Set the value related to the column: PRINT_TO_KITCHEN
	 * @param shouldPrintToKitchen the PRINT_TO_KITCHEN value
	 */
	public void setShouldPrintToKitchen(java.lang.Boolean shouldPrintToKitchen) {
		this.shouldPrintToKitchen = shouldPrintToKitchen;
	}

	/**
	 * Custom property
	 */
	public static String getShouldPrintToKitchenDefaultValue() {
		return "true";
	}

	/**
	 * Return the value associated with the column: SECTION_WISE_PRICING
	 */
	public java.lang.Boolean isShouldSectionWisePrice() {
		return shouldSectionWisePrice == null ? Boolean.FALSE : shouldSectionWisePrice;
	}

	/**
	 * Set the value related to the column: SECTION_WISE_PRICING
	 * @param shouldSectionWisePrice the SECTION_WISE_PRICING value
	 */
	public void setShouldSectionWisePrice(java.lang.Boolean shouldSectionWisePrice) {
		this.shouldSectionWisePrice = shouldSectionWisePrice;
	}

	/**
	 * Return the value associated with the column: PIZZA_MODIFIER
	 */
	public java.lang.Boolean isPizzaModifier() {
		return pizzaModifier == null ? Boolean.FALSE : pizzaModifier;
	}

	/**
	 * Set the value related to the column: PIZZA_MODIFIER
	 * @param pizzaModifier the PIZZA_MODIFIER value
	 */
	public void setPizzaModifier(java.lang.Boolean pizzaModifier) {
		this.pizzaModifier = pizzaModifier;
	}

	/**
	 * Return the value associated with the column: GROUP_ID
	 */
	public MenuModifierGroup getModifierGroup() {
		return modifierGroup;
	}

	/**
	 * Set the value related to the column: GROUP_ID
	 * @param modifierGroup the GROUP_ID value
	 */
	public void setModifierGroup(MenuModifierGroup modifierGroup) {
		this.modifierGroup = modifierGroup;
	}

	/**
	 * Return the value associated with the column: TAX_ID
	 */
	public Tax getTax() {
		return tax;
	}

	/**
	 * Set the value related to the column: TAX_ID
	 * @param tax the TAX_ID value
	 */
	public void setTax(Tax tax) {
		this.tax = tax;
	}

	/**
	 * Return the value associated with the column: pizzaModifierPriceList
	 */
	public java.util.List<PizzaModifierPrice> getPizzaModifierPriceList() {
		return pizzaModifierPriceList;
	}

	/**
	 * Set the value related to the column: pizzaModifierPriceList
	 * @param pizzaModifierPriceList the pizzaModifierPriceList value
	 */
	public void setPizzaModifierPriceList(java.util.List<PizzaModifierPrice> pizzaModifierPriceList) {
		this.pizzaModifierPriceList = pizzaModifierPriceList;
	}

	public void addTopizzaModifierPriceList(PizzaModifierPrice pizzaModifierPrice) {
		if (null == getPizzaModifierPriceList())
			setPizzaModifierPriceList(new java.util.ArrayList<PizzaModifierPrice>());
		getPizzaModifierPriceList().add(pizzaModifierPrice);
	}

	/**
	 * Return the value associated with the column: multiplierPriceList
	 */
	public java.util.List<ModifierMultiplierPrice> getMultiplierPriceList() {
		return multiplierPriceList;
	}

	/**
	 * Set the value related to the column: multiplierPriceList
	 * @param multiplierPriceList the multiplierPriceList value
	 */
	public void setMultiplierPriceList(java.util.List<ModifierMultiplierPrice> multiplierPriceList) {
		this.multiplierPriceList = multiplierPriceList;
	}

	public void addTomultiplierPriceList(ModifierMultiplierPrice modifierMultiplierPrice) {
		if (null == getMultiplierPriceList())
			setMultiplierPriceList(new java.util.ArrayList<ModifierMultiplierPrice>());
		getMultiplierPriceList().add(modifierMultiplierPrice);
	}

	/**
	 * Return the value associated with the column: properties
	 */
	public java.util.Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * Set the value related to the column: properties
	 * @param properties the properties value
	 */
	public void setProperties(java.util.Map<String, String> properties) {
		this.properties = properties;
	}

}