/**
 * 
 */
package net.brilliance.model;

/**
 * @author ducbq
 *
 */
public enum DateTimePatterns {
	ddMMyyyy_SLASH("dd/MM/yyyy"),
	ddMMyyyy_BAR("dd-MM-yyyy"),
	MMddyyyy_SLASH("MM/dd/yyyy"),
	MMddyyyy_BAR("MM-dd-yyyy"),
	yyyyMMdd_SLASH("yyyy/MM/dd"),
	yyyyMMdd_BAR("yyyy-MM-dd"),
	yyyyMMdd("yyyyMMdd"),
	ddMMyyyyHHmmss_UNDERSCORE("dd/MM/yyyy_HH:mm:ss"),
	ddMMyyyyHHmmss_SLASH("dd/MM/yyyy HH:mm:ss")
	;

	private String dateTimePattern;

	private DateTimePatterns(String pattern){
		this.dateTimePattern = pattern;
	}

	public String getDateTimePattern() {
		return dateTimePattern;
	}
}
