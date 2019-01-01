package net.brilliance.domain.entity.base;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.MappedSuperclass;

import net.brilliance.domain.entity.general.Tax;
import net.brilliance.framework.entity.BizObjectBase;


/**
 * This is an object that contains data related to the TAX_GROUP table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TAX_GROUP"
 */
@MappedSuperclass
public abstract class BaseTaxGroup extends BizObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2607451700515581301L;
	public static String REF = "TaxGroup"; 
	public static String PROP_ID = "id"; 
	public static String PROP_NAME = "name"; 


	// constructors
	public BaseTaxGroup () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTaxGroup (java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTaxGroup (
		java.lang.Long id,
		java.lang.String name) {

		this.setId(id);
		this.setName(name);
		initialize();
	}

	protected void initialize () {}

	// fields
	@Column(name = "name")
	protected java.lang.String name;

	// collections
	@ElementCollection
	@CollectionTable(name = "taxes")
	private java.util.List<Tax> taxes;

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
	 * Return the value associated with the column: taxes
	 */
	public java.util.List<Tax> getTaxes () {
					return taxes;
			}

	/**
	 * Set the value related to the column: taxes
	 * @param taxes the taxes value
	 */
	public void setTaxes (java.util.List<Tax> taxes) {
		this.taxes = taxes;
	}

	public void addTotaxes (Tax tax) {
		if (null == getTaxes()) 
			setTaxes(new java.util.ArrayList<Tax>());

		getTaxes().add(tax);
	}

}