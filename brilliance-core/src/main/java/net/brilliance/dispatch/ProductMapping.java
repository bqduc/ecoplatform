/**
 * 
 */
package net.brilliance.dispatch;

/**
 * @author ducbq
 *
 */
public enum ProductMapping {
	NAME(1, "Name"),
	CONCENTRATION(2, "Concentration"),
	ACTIVE_PRINCIPLE(3, "Active Principle"),
	PROCESSING(4, "Processing"),
	PACKAGING(5, "Packaging"),
	STANDARD(6, "Standard"),
	EXPECTATION_OF_LIFE(7, "Expectation of life"),
	REGISTRATION_NO(8, "Registration No"),
	PRODUCTION_COMPANY(9, "Production company"),
	PRODUCTION_COUNTRY(10, "Production country"),
	PRODUCTION_ADDRESS(11, "Production address"),
	REGISTRATION_COMPANY(12, "Registration company"),
	REGISTRATION_COUNTRY(13, "Registration country"),
	REGISTRATION_ADDRESS(14, "Registration address"),
	ROOT(15, "root"),
	NATIONAL_CIRCULAR_NO(16, "National Circular No"),
	CATEGORY(17, "category")
	;

	private String name;
	private int index;

	private ProductMapping(int idx, String name){
		this.name = name;
		this.index = idx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
