/**
 * 
 */
package net.brilliance.framework.model;

/**
 * @author ducbq
 *
 */
public enum SequenceType {
	DASHBOARD("DSB"),
	DASHLET("DSL"),

	CATALOG_SUBTYPE("CST"),
	TICKET("TCK"),
	INCIDENT("INC"),
	CHANGE_REQUEST("CRQ"),
	PROBLEM("PRB")
	;

	private String type;

	public String getType() {
		return type;
	}

	private SequenceType(String type) {
		this.type = type;
	}
}
