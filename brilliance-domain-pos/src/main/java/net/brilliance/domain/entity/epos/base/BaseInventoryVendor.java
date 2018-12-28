package net.brilliance.domain.entity.epos.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

import net.brilliance.framework.entity.BizObjectBase;


/**
 * This is an object that contains data related to the INVENTORY_VENDOR table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="INVENTORY_VENDOR"
 */

@MappedSuperclass
public abstract class BaseInventoryVendor extends BizObjectBase/*extends RepositoryEntity */{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5591200698397009355L;
	public static String REF = "InventoryVendor"; //$NON-NLS-1$
	public static String PROP_ZIP = "zip"; //$NON-NLS-1$
	public static String PROP_EMAIL = "email"; //$NON-NLS-1$
	public static String PROP_ADDRESS = "address"; //$NON-NLS-1$
	public static String PROP_STATE = "state"; //$NON-NLS-1$
	public static String PROP_PHONE = "phone"; //$NON-NLS-1$
	public static String PROP_COUNTRY = "country"; //$NON-NLS-1$
	public static String PROP_CITY = "city"; //$NON-NLS-1$
	public static String PROP_ID = "id"; //$NON-NLS-1$
	public static String PROP_FAX = "fax"; //$NON-NLS-1$
	public static String PROP_NAME = "name"; //$NON-NLS-1$


	// constructors
	public BaseInventoryVendor () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseInventoryVendor (Long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseInventoryVendor (
		Long id,
 java.lang.String name, java.lang.String address, java.lang.String city, java.lang.String state,
			java.lang.String zip, java.lang.String country, java.lang.String email, java.lang.String phone) {

		this.setId(id);
		this.setName(name);
		this.setAddress(address);
		this.setCity(city);
		this.setState(state);
		this.setZip(zip);
		this.setCountry(country);
		this.setEmail(email);
		this.setPhone(phone);
		initialize();
	}

	protected void initialize () {}



	// fields
	@Size(max = 150)
	@Column(name="name")
	protected java.lang.String name;

	@Size(max = 250)
	@Column(name="address")
	protected java.lang.String address;

	@Size(max = 50)
	@Column(name="city")
	protected java.lang.String city;

	@Size(max = 50)
	@Column(name="state")
	protected java.lang.String state;

	@Size(max = 10)
	@Column(name="zip")
	protected java.lang.String zip;

	@Size(max = 20)
	@Column(name="country")
	protected java.lang.String country;

	@Size(max = 150)
	@Column(name="email")
	protected java.lang.String email;

	@Size(max = 30)
	@Column(name="phone")
	protected java.lang.String phone;

	@Size(max = 50)
	@Column(name="fax")
	protected java.lang.String fax;


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
	 * Return the value associated with the column: ADDRESS
	 */
	public java.lang.String getAddress() {
		return address;
	}

	/**
	 * Set the value related to the column: ADDRESS
	 * @param address the ADDRESS value
	 */
	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	/**
	 * Return the value associated with the column: CITY
	 */
	public java.lang.String getCity() {
		return city;
	}

	/**
	 * Set the value related to the column: CITY
	 * @param city the CITY value
	 */
	public void setCity(java.lang.String city) {
		this.city = city;
	}

	/**
	 * Return the value associated with the column: STATE
	 */
	public java.lang.String getState() {
		return state;
	}

	/**
	 * Set the value related to the column: STATE
	 * @param state the STATE value
	 */
	public void setState(java.lang.String state) {
		this.state = state;
	}

	/**
	 * Return the value associated with the column: ZIP
	 */
	public java.lang.String getZip() {
		return zip;
	}

	/**
	 * Set the value related to the column: ZIP
	 * @param zip the ZIP value
	 */
	public void setZip(java.lang.String zip) {
		this.zip = zip;
	}

	/**
	 * Return the value associated with the column: COUNTRY
	 */
	public java.lang.String getCountry() {
		return country;
	}

	/**
	 * Set the value related to the column: COUNTRY
	 * @param country the COUNTRY value
	 */
	public void setCountry(java.lang.String country) {
		this.country = country;
	}

	/**
	 * Return the value associated with the column: EMAIL
	 */
	public java.lang.String getEmail() {
		return email;
	}

	/**
	 * Set the value related to the column: EMAIL
	 * @param email the EMAIL value
	 */
	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	/**
	 * Return the value associated with the column: PHONE
	 */
	public java.lang.String getPhone() {
		return phone;
	}

	/**
	 * Set the value related to the column: PHONE
	 * @param phone the PHONE value
	 */
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	/**
	 * Return the value associated with the column: FAX
	 */
	public java.lang.String getFax() {
		return fax;
	}

	/**
	 * Set the value related to the column: FAX
	 * @param fax the FAX value
	 */
	public void setFax(java.lang.String fax) {
		this.fax = fax;
	}

}