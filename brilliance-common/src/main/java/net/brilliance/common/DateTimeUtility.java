/**
 * 
 */
package net.brilliance.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import net.brilliance.common.validator.DateValidator;
import net.brilliance.model.DateTimePatterns;

/**
 * @author ducbq
 *
 */
public final class DateTimeUtility {
	private static DateTimeUtility instance = new DateTimeUtility();
	private final static String DEFAULT_MINIMUM_TIME_PATTERN = "31/12/1970 13:01:01";
	private final static String DEFAULT_MAXIMUM_TIME_PATTERN = "31/12/4017 13:01:01";

	private static final Logger logger = LogManager.getLogger(DateTimeUtility.class);

	public static DateTimeUtility instance(){
		return instance;
	}

	public Date createDate(long longDateTime){
		return new Date(longDateTime);
	}

	public Date fillSystemTimeParts(long longDateTime){
		return this.fillSystemTimeParts(this.createDate(longDateTime));
	}

	public Date fillSystemTimeParts(Date date){
		Calendar fromCalendar = new GregorianCalendar();
		fromCalendar.setTime(date);

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, fromCalendar.get(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.MONTH, fromCalendar.get(Calendar.MONTH));
		calendar.set(Calendar.YEAR, fromCalendar.get(Calendar.YEAR));

		return calendar.getTime();
	}

	public Date getSystemTime(){
		return new GregorianCalendar().getTime();
	}

	public static long getUtcTime(){
		Calendar utcCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		return utcCalendar.getTimeInMillis();
	}

	public static long getGmtTime(){
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		return calendar.getTimeInMillis();
	}

	private static Date parseDateTime(String pattern, String value){
		Date retDate = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try {
			retDate = simpleDateFormat.parse(value);
		} catch (ParseException e) {
			logger.error(e);
		}
		return retDate;
	}

	public static Date getMinimumTime(){
		return getMinimumTime(null);
	}

	public static Date getUnfinishedTime(){
		return getUnfinishedTime(null);
	}

	public static Date getMinimumTime(String configuredDefaultValue){
		if (CommonUtility.isEmpty(configuredDefaultValue)){
			return parseDateTime(DateTimePatterns.ddMMyyyyHHmmss_SLASH.getDateTimePattern(), DEFAULT_MINIMUM_TIME_PATTERN);
		}
		return parseDateTime(DateTimePatterns.ddMMyyyyHHmmss_SLASH.getDateTimePattern(), configuredDefaultValue);
	}

	public static Date getUnfinishedTime(String configuredDefaultValue){
		if (CommonUtility.isEmpty(configuredDefaultValue)){
			return parseDateTime(DateTimePatterns.ddMMyyyyHHmmss_SLASH.getDateTimePattern(), DEFAULT_MAXIMUM_TIME_PATTERN);
		}
		return parseDateTime(DateTimePatterns.ddMMyyyyHHmmss_SLASH.getDateTimePattern(), configuredDefaultValue);
	}

	public static Date getSystemDate() {
		Calendar sysCal = Calendar.getInstance();
		sysCal.setTime(Calendar.getInstance().getTime());
		sysCal.set(Calendar.HOUR_OF_DAY, 0);
		sysCal.set(Calendar.MINUTE, 0);
		sysCal.set(Calendar.SECOND, 0);
		sysCal.set(Calendar.MILLISECOND, 0);
		return sysCal.getTime();
	}

	public static Date getSystemDateTime() {
		return instance().getSystemTime();
	}

	public static String getSystemDateTimeString() {
		return dateToString(getSystemDateTime(), "dd/MM/yyyy HH:mm:ss");
	}

	public static String getSystemDateTimeString(String pattern) {
		if (CommonUtility.isEmpty(pattern))
			return getSystemDateTimeString();

		return dateToString(getSystemDateTime(), pattern);
	}

	public static String dateToString(Date dateValue, String pattern) {
		String stringRet = null;
		try {
			if (CommonUtility.isEmpty(dateValue))
				return null;

			if (CommonUtility.isEmpty(pattern)) {
				pattern = DateTimePatterns.ddMMyyyy_SLASH.getDateTimePattern();
			}

			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			stringRet = sdf.format(dateValue);
		} catch (Exception e) {
			logger.error(e);
		}
		return stringRet;
	}

	public static Date parseDate(String value, String pattern) {
		if (CommonUtility.isEmpty(value))
			return null;

		SimpleDateFormat sdf = null;
		Date ret = null;
		if (CommonUtility.isEmpty(pattern)) {
			for (int i = 0; i < DateTimePatterns.values().length; i++) {
				try {
					sdf = new SimpleDateFormat(DateTimePatterns.values()[i].getDateTimePattern());
					ret = sdf.parse(value);
				} catch (Exception e) {
					//logger.info("Error while parsing date: " + value);
				}
				if (CommonUtility.isNotEmpty(ret))
					break;
			}
		} else {
			try {
				sdf = new SimpleDateFormat(pattern);
				ret = sdf.parse(value);
			} catch (Exception e) {
				//logger.info("Error while parsing date: " + value);
			}
		}
		return ret;
	}

	public static Date toDate(String chunk) {
		if (CommonUtility.isEmpty(chunk))
			return null;

		return parseDate(chunk, null);
	}

	public static Date createFreeDate(String value){
		Date freeDate = null;
		for (DateTimePatterns dateTimePattern :DateTimePatterns.values()){
			try {
				freeDate = parseDate(value, dateTimePattern.getDateTimePattern());
			} catch (Exception e) {
				freeDate = null;
			}

			if (null != freeDate)
				break;
		}
		return freeDate;
	}

	public static Date getDateInstance(String data, Date defaultValue) throws ParseException{
		DateValidator dateValidatorInstance = DateValidator.getInstance();
		if (!dateValidatorInstance.validate(data))
			return defaultValue;

		return dateValidatorInstance.getSimpleDateFormat().parse(data);
	}

	public static Date getDateInstance(String data) throws ParseException{
		DateValidator dateValidatorInstance = DateValidator.getInstance();
		if (!dateValidatorInstance.validate(data))
			return null;

		return dateValidatorInstance.getSimpleDateFormat().parse(data);
	}

	public static Date getDateInstance(String data, String[] patterns){
		Date parsedDate = null;
		for (String pattern :patterns){
			try {
				parsedDate = new SimpleDateFormat(pattern).parse(data);
			} catch (Exception e) {
				parsedDate = null;
			}

			if (null != parsedDate)
				return parsedDate;
		}
		throw new RuntimeException("The data[" + data + "] did not matches with any patterns " + patterns);
	}

	public static long generateTimeStamp(){
		return Calendar.getInstance().getTimeInMillis();
	}

	public static Date getDummyMaxDate() {
		return toDate("31/12/2200");
	}
}