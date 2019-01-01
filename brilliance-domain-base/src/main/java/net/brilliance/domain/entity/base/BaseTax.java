package net.brilliance.domain.entity.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import net.brilliance.framework.entity.BizObjectBase;

/**
 * This is an object that contains data related to the TAX table. Do not modify this class because it will be overwritten if the configuration file related to this class is
 * modified.
 *
 * @hibernate.class table="TAX"
 */
@MappedSuperclass
public abstract class BaseTax extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6216186291755321219L;
	public static String REF = "Tax"; 
	public static String PROP_RATE = "rate"; 
	public static String PROP_ID = "id"; 
	public static String PROP_NAME = "name"; 

	public BaseTax() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTax(java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTax(java.lang.Long id, java.lang.String name) {

		this.setId(id);
		this.setName(name);
		initialize();
	}

	protected void initialize() {
	}

	// fields
	@Column(name = "name")
	protected java.lang.String name;

	@Column(name = "rate")
	protected java.lang.Double rate;

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
	 * Return the value associated with the column: RATE
	 */
	public java.lang.Double getRate() {
		return rate == null ? Double.valueOf(0) : rate;
	}

	/**
	 * Set the value related to the column: RATE
	 * 
	 * @param rate
	 *          the RATE value
	 */
	public void setRate(java.lang.Double rate) {
		this.rate = rate;
	}

}