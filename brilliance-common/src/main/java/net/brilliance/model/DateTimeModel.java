/**
 * 
 */
package net.brilliance.model;

/**
 * @author ducbq
 *
 */
public enum DateTimeModel {
	mmddyyyy("mm/dd/yyyy", "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$"),
	ddmmyyyy("dd/mm/yyyy", "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$")
	
	;
	private String pattern;
	private String regularExpression;

	private DateTimeModel(String pattern, String regularExpression){
		this.pattern = pattern;
		this.regularExpression = regularExpression;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getRegularExpression() {
		return regularExpression;
	}

	public void setRegularExpression(String regularExpression) {
		this.regularExpression = regularExpression;
	}
}