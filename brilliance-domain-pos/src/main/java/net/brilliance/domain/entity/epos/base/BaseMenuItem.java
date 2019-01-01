package net.brilliance.domain.entity.epos.base;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.MappedSuperclass;

import jdk.nashorn.internal.ir.Terminal;
import net.brilliance.domain.entity.epos.Discount;
import net.brilliance.domain.entity.epos.MenuGroup;
import net.brilliance.domain.entity.epos.MenuItemModifierGroup;
import net.brilliance.domain.entity.epos.MenuItemShift;
import net.brilliance.domain.entity.epos.OrderType;
import net.brilliance.domain.entity.epos.PizzaPrice;
import net.brilliance.domain.entity.epos.PrinterGroup;
import net.brilliance.domain.entity.epos.Receipt;
import net.brilliance.domain.entity.general.TaxGroup;
import net.brilliance.framework.entity.BizObjectBase;


/**
 * This is an object that contains data related to the MENU_ITEM table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="MENU_ITEM"
 */
@MappedSuperclass
public abstract class BaseMenuItem extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2307005267542894422L;
	public static String REF = "MenuItem"; //$NON-NLS-1$
	public static String PROP_SHOW_IMAGE_ONLY = "showImageOnly"; //$NON-NLS-1$
	public static String PROP_DESCRIPTION = "description"; //$NON-NLS-1$
	public static String PROP_PRINTER_GROUP = "printerGroup"; //$NON-NLS-1$
	public static String PROP_PARENT = "parent"; //$NON-NLS-1$
	public static String PROP_PIZZA_TYPE = "pizzaType"; //$NON-NLS-1$
	public static String PROP_STOCK_AMOUNT = "stockAmount"; //$NON-NLS-1$
	public static String PROP_SORT_ORDER = "sortOrder"; //$NON-NLS-1$
	public static String PROP_UNIT_NAME = "unitName"; //$NON-NLS-1$
	public static String PROP_DEFAULT_SELL_PORTION = "defaultSellPortion"; //$NON-NLS-1$
	public static String PROP_RECEPIE = "recepie"; //$NON-NLS-1$
	public static String PROP_DISCOUNT_RATE = "discountRate"; //$NON-NLS-1$
	public static String PROP_DISABLE_WHEN_STOCK_AMOUNT_IS_ZERO = "disableWhenStockAmountIsZero"; //$NON-NLS-1$
	public static String PROP_NAME = "name"; //$NON-NLS-1$
	public static String PROP_TEXT_COLOR_CODE = "textColorCode"; //$NON-NLS-1$
	public static String PROP_TRANSLATED_NAME = "translatedName"; //$NON-NLS-1$
	public static String PROP_PRICE = "price"; //$NON-NLS-1$
	public static String PROP_BARCODE = "barcode"; //$NON-NLS-1$
	public static String PROP_IMAGE_DATA = "imageData"; //$NON-NLS-1$
	public static String PROP_FRACTIONAL_UNIT = "fractionalUnit"; //$NON-NLS-1$
	public static String PROP_TAX_GROUP = "taxGroup"; //$NON-NLS-1$
	public static String PROP_ID = "id"; //$NON-NLS-1$
	public static String PROP_BUY_PRICE = "buyPrice"; //$NON-NLS-1$
	public static String PROP_BUTTON_COLOR_CODE = "buttonColorCode"; //$NON-NLS-1$


	// constructors
	public BaseMenuItem () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMenuItem (java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMenuItem (
		java.lang.Long id,
		java.lang.String name,
		java.lang.Double buyPrice,
		java.lang.Double price) {

		this.setId(id);
		this.setName(name);
		this.setBuyPrice(buyPrice);
		this.setPrice(price);
		initialize();
	}

	protected void initialize () {}



	// fields
		protected java.lang.String name;
		protected java.lang.String description;
		protected java.lang.String unitName;
		protected java.lang.String translatedName;
		protected java.lang.String barcode;
		protected java.lang.Double buyPrice;
		protected java.lang.Double stockAmount;
		protected java.lang.Double price;
		protected java.lang.Double discountRate;
		protected java.lang.Boolean disableWhenStockAmountIsZero;
		protected java.lang.Integer sortOrder;
		protected java.lang.Integer buttonColorCode;
		protected java.lang.Integer textColorCode;
		protected byte[] imageData;
		protected java.lang.Boolean showImageOnly;
		protected java.lang.Boolean fractionalUnit;
		protected java.lang.Boolean pizzaType;
		protected java.lang.Integer defaultSellPortion;

	// many to one
	private MenuGroup parent;
	private TaxGroup taxGroup;
	private Receipt recepie;
	private PrinterGroup printerGroup;

	// collections
	@ElementCollection
	@CollectionTable(name = "vpos_pizzaPriceList")
	private java.util.List<PizzaPrice> pizzaPriceList;

	@ElementCollection
	@CollectionTable(name = "vpos_shifts")
	private java.util.List<MenuItemShift> shifts;

	@ElementCollection
	@CollectionTable(name = "vpos_discounts")
	private java.util.List<Discount> discounts;

	@ElementCollection
	@CollectionTable(name = "vpos_menuItemModiferGroups")
	private java.util.List<MenuItemModifierGroup> menuItemModiferGroups;

	@ElementCollection
	@CollectionTable(name = "vpos_terminals")
	private java.util.List<Terminal> terminals;

	@ElementCollection
	@CollectionTable(name = "vpos_properties")
	private java.util.Map<String,String> properties;

	@ElementCollection
	@CollectionTable(name = "vpos_orderTypeList")
	private java.util.List<OrderType> orderTypeList;


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
	 * Return the value associated with the column: DESCRIPTION
	 */
	public java.lang.String getDescription () {
					return description;
			}

	/**
	 * Set the value related to the column: DESCRIPTION
	 * @param description the DESCRIPTION value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
	}



	/**
	 * Return the value associated with the column: UNIT_NAME
	 */
	public java.lang.String getUnitName () {
					return unitName;
			}

	/**
	 * Set the value related to the column: UNIT_NAME
	 * @param unitName the UNIT_NAME value
	 */
	public void setUnitName (java.lang.String unitName) {
		this.unitName = unitName;
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
	 * Return the value associated with the column: BUY_PRICE
	 */
	public java.lang.Double getBuyPrice () {
									return buyPrice == null ? Double.valueOf(0) : buyPrice;
					}

	/**
	 * Set the value related to the column: BUY_PRICE
	 * @param buyPrice the BUY_PRICE value
	 */
	public void setBuyPrice (java.lang.Double buyPrice) {
		this.buyPrice = buyPrice;
	}



	/**
	 * Return the value associated with the column: STOCK_AMOUNT
	 */
	public java.lang.Double getStockAmount () {
									return stockAmount == null ? Double.valueOf(0) : stockAmount;
					}

	/**
	 * Set the value related to the column: STOCK_AMOUNT
	 * @param stockAmount the STOCK_AMOUNT value
	 */
	public void setStockAmount (java.lang.Double stockAmount) {
		this.stockAmount = stockAmount;
	}



	/**
	 * Return the value associated with the column: PRICE
	 */
	public java.lang.Double getPrice () {
									return price == null ? Double.valueOf(0) : price;
					}

	/**
	 * Set the value related to the column: PRICE
	 * @param price the PRICE value
	 */
	public void setPrice (java.lang.Double price) {
		this.price = price;
	}



	/**
	 * Return the value associated with the column: DISCOUNT_RATE
	 */
	public java.lang.Double getDiscountRate () {
									return discountRate == null ? Double.valueOf(0) : discountRate;
					}

	/**
	 * Set the value related to the column: DISCOUNT_RATE
	 * @param discountRate the DISCOUNT_RATE value
	 */
	public void setDiscountRate (java.lang.Double discountRate) {
		this.discountRate = discountRate;
	}

	/**
	 * Custom property
	 */
	public static String getVisibleDefaultValue () {
		return "true";
	}


	/**
	 * Return the value associated with the column: DISABLE_WHEN_STOCK_AMOUNT_IS_ZERO
	 */
	public java.lang.Boolean isDisableWhenStockAmountIsZero () {
									return disableWhenStockAmountIsZero == null ? Boolean.valueOf(false) : disableWhenStockAmountIsZero;
						}

	/**
	 * Set the value related to the column: DISABLE_WHEN_STOCK_AMOUNT_IS_ZERO
	 * @param disableWhenStockAmountIsZero the DISABLE_WHEN_STOCK_AMOUNT_IS_ZERO value
	 */
	public void setDisableWhenStockAmountIsZero (java.lang.Boolean disableWhenStockAmountIsZero) {
		this.disableWhenStockAmountIsZero = disableWhenStockAmountIsZero;
	}


	/**
	 * Custom property
	 */
	public static String getDisableWhenStockAmountIsZeroDefaultValue () {
		return "false";
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
									return buttonColorCode == null ? null : buttonColorCode;
						}

	/**
	 * Set the value related to the column: BTN_COLOR
	 * @param buttonColorCode the BTN_COLOR value
	 */
	public void setButtonColorCode (java.lang.Integer buttonColorCode) {
		this.buttonColorCode = buttonColorCode;
	}


	/**
	 * Custom property
	 */
	public static String getButtonColorCodeDefaultValue () {
		return "null";
	}


	/**
	 * Return the value associated with the column: TEXT_COLOR
	 */
	public java.lang.Integer getTextColorCode () {
									return textColorCode == null ? null : textColorCode;
						}

	/**
	 * Set the value related to the column: TEXT_COLOR
	 * @param textColorCode the TEXT_COLOR value
	 */
	public void setTextColorCode (java.lang.Integer textColorCode) {
		this.textColorCode = textColorCode;
	}


	/**
	 * Custom property
	 */
	public static String getTextColorCodeDefaultValue () {
		return "null";
	}


	/**
	 * Return the value associated with the column: IMAGE
	 */
	public byte[] getImageData () {
					return imageData;
			}

	/**
	 * Set the value related to the column: IMAGE
	 * @param imageData the IMAGE value
	 */
	public void setImageData (byte[] imageData) {
		this.imageData = imageData;
	}



	/**
	 * Return the value associated with the column: SHOW_IMAGE_ONLY
	 */
	public java.lang.Boolean isShowImageOnly () {
								return showImageOnly == null ? Boolean.FALSE : showImageOnly;
					}

	/**
	 * Set the value related to the column: SHOW_IMAGE_ONLY
	 * @param showImageOnly the SHOW_IMAGE_ONLY value
	 */
	public void setShowImageOnly (java.lang.Boolean showImageOnly) {
		this.showImageOnly = showImageOnly;
	}



	/**
	 * Return the value associated with the column: FRACTIONAL_UNIT
	 */
	public java.lang.Boolean isFractionalUnit () {
								return fractionalUnit == null ? Boolean.FALSE : fractionalUnit;
					}

	/**
	 * Set the value related to the column: FRACTIONAL_UNIT
	 * @param fractionalUnit the FRACTIONAL_UNIT value
	 */
	public void setFractionalUnit (java.lang.Boolean fractionalUnit) {
		this.fractionalUnit = fractionalUnit;
	}



	/**
	 * Return the value associated with the column: PIZZA_TYPE
	 */
	public java.lang.Boolean isPizzaType () {
								return pizzaType == null ? Boolean.FALSE : pizzaType;
					}

	/**
	 * Set the value related to the column: PIZZA_TYPE
	 * @param pizzaType the PIZZA_TYPE value
	 */
	public void setPizzaType (java.lang.Boolean pizzaType) {
		this.pizzaType = pizzaType;
	}



	/**
	 * Return the value associated with the column: DEFAULT_SELL_PORTION
	 */
	public java.lang.Integer getDefaultSellPortion () {
									return defaultSellPortion == null ? Integer.valueOf(0) : defaultSellPortion;
					}

	/**
	 * Set the value related to the column: DEFAULT_SELL_PORTION
	 * @param defaultSellPortion the DEFAULT_SELL_PORTION value
	 */
	public void setDefaultSellPortion (java.lang.Integer defaultSellPortion) {
		this.defaultSellPortion = defaultSellPortion;
	}



	/**
	 * Return the value associated with the column: GROUP_ID
	 */
	public MenuGroup getParent () {
					return parent;
			}

	/**
	 * Set the value related to the column: GROUP_ID
	 * @param parent the GROUP_ID value
	 */
	public void setParent (MenuGroup parent) {
		this.parent = parent;
	}



	/**
	 * Return the value associated with the column: TAX_GROUP_ID
	 */
	public TaxGroup getTaxGroup () {
					return taxGroup;
			}

	/**
	 * Set the value related to the column: TAX_GROUP_ID
	 * @param taxGroup the TAX_GROUP_ID value
	 */
	public void setTaxGroup (TaxGroup taxGroup) {
		this.taxGroup = taxGroup;
	}



	/**
	 * Return the value associated with the column: RECEPIE
	 */
	public Receipt getRecepie () {
					return recepie;
			}

	/**
	 * Set the value related to the column: RECEPIE
	 * @param recepie the RECEPIE value
	 */
	public void setRecepie (Receipt recepie) {
		this.recepie = recepie;
	}



	/**
	 * Return the value associated with the column: PG_ID
	 */
	public PrinterGroup getPrinterGroup () {
					return printerGroup;
			}

	/**
	 * Set the value related to the column: PG_ID
	 * @param printerGroup the PG_ID value
	 */
	public void setPrinterGroup (PrinterGroup printerGroup) {
		this.printerGroup = printerGroup;
	}



	/**
	 * Return the value associated with the column: pizzaPriceList
	 */
	public java.util.List<PizzaPrice> getPizzaPriceList () {
					return pizzaPriceList;
			}

	/**
	 * Set the value related to the column: pizzaPriceList
	 * @param pizzaPriceList the pizzaPriceList value
	 */
	public void setPizzaPriceList (java.util.List<PizzaPrice> pizzaPriceList) {
		this.pizzaPriceList = pizzaPriceList;
	}

	public void addTopizzaPriceList (PizzaPrice pizzaPrice) {
		if (null == getPizzaPriceList()) setPizzaPriceList(new java.util.ArrayList<PizzaPrice>());
		getPizzaPriceList().add(pizzaPrice);
	}



	/**
	 * Return the value associated with the column: shifts
	 */
	public java.util.List<MenuItemShift> getShifts () {
					return shifts;
			}

	/**
	 * Set the value related to the column: shifts
	 * @param shifts the shifts value
	 */
	public void setShifts (java.util.List<MenuItemShift> shifts) {
		this.shifts = shifts;
	}

	public void addToshifts (MenuItemShift menuItemShift) {
		if (null == getShifts()) setShifts(new java.util.ArrayList<MenuItemShift>());
		getShifts().add(menuItemShift);
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



	/**
	 * Return the value associated with the column: menuItemModiferGroups
	 */
	public java.util.List<MenuItemModifierGroup> getMenuItemModiferGroups () {
					return menuItemModiferGroups;
			}

	/**
	 * Set the value related to the column: menuItemModiferGroups
	 * @param menuItemModiferGroups the menuItemModiferGroups value
	 */
	public void setMenuItemModiferGroups (java.util.List<MenuItemModifierGroup> menuItemModiferGroups) {
		this.menuItemModiferGroups = menuItemModiferGroups;
	}

	public void addTomenuItemModiferGroups (MenuItemModifierGroup menuItemModifierGroup) {
		if (null == getMenuItemModiferGroups()) setMenuItemModiferGroups(new java.util.ArrayList<MenuItemModifierGroup>());
		getMenuItemModiferGroups().add(menuItemModifierGroup);
	}



	/**
	 * Return the value associated with the column: terminals
	 */
	public java.util.List<Terminal> getTerminals () {
					return terminals;
			}

	/**
	 * Set the value related to the column: terminals
	 * @param terminals the terminals value
	 */
	public void setTerminals (java.util.List<Terminal> terminals) {
		this.terminals = terminals;
	}

	public void addToterminals (Terminal terminal) {
		if (null == getTerminals()) setTerminals(new java.util.ArrayList<Terminal>());
		getTerminals().add(terminal);
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



	/**
	 * Return the value associated with the column: orderTypeList
	 */
	public java.util.List<OrderType> getOrderTypeList () {
					return orderTypeList;
			}

	/**
	 * Set the value related to the column: orderTypeList
	 * @param orderTypeList the orderTypeList value
	 */
	public void setOrderTypeList (java.util.List<OrderType> orderTypeList) {
		this.orderTypeList = orderTypeList;
	}

	public void addToorderTypeList (OrderType orderType) {
		if (null == getOrderTypeList()) setOrderTypeList(new java.util.ArrayList<OrderType>());
		getOrderTypeList().add(orderType);
	}

}