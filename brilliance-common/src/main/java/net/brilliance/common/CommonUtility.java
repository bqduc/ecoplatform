/**
 * 
 */
package net.brilliance.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Base64Utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import net.brilliance.exceptions.VpxRuntimeException;

/**
 * @author ducbq
 *
 */
public class CommonUtility implements CommonConstants {
	public static final String characterNewLine = "\n";
	public static final String BOOLEAN_STRING_TRUE = "true";
	public static final String BOOLEAN_STRING_FALSE = "false";
	
	private static PhoneNumberUtil globalPhoneNumberUtil = PhoneNumberUtil.getInstance();

	private static final Logger logger = LogManager.getLogger(CommonUtility.class);
	private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<Class<?>, Class<?>>();
	protected final static CommonUtility instance = new CommonUtility();
	private static List<String> TURN_ON_VALUES = new ArrayList<>();
	
	public static final Integer ZERO_INTEGER = new Integer(0);
	public static final BigDecimal ZERO_BIG_DECIMAL = new BigDecimal(0);
	public static final int CALENDAR_FIELD_DAY_OF_MONTH = Calendar.DAY_OF_MONTH;
	public static final int CALENDAR_FIELD_MONTH = Calendar.MONTH;
	public static final int CALENDAR_FIELD_YEAR = Calendar.YEAR;

	public static final String[] formattedDatePatterns = { "dd/MM/yyyy", "dd-MM-yyyy", "MM/dd/yyyy", "MM-dd-yyyy", "yyyy/MM/dd", "yyyy-MM-dd", "yyyyMMdd" };

	public static final Locale LOCALE_US = Locale.US;
	public static final Locale LOCALE_VIETNAMESE = new Locale("vi", "VN");

	public static String[] SEPARATORS = new String[]{",", ";", "|"};

	static {
		primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
		primitiveWrapperMap.put(Byte.TYPE, Byte.class);
		primitiveWrapperMap.put(Character.TYPE, Character.class);
		primitiveWrapperMap.put(Short.TYPE, Short.class);
		primitiveWrapperMap.put(Integer.TYPE, Integer.class);
		primitiveWrapperMap.put(Long.TYPE, Long.class);
		primitiveWrapperMap.put(Double.TYPE, Double.class);
		primitiveWrapperMap.put(Float.TYPE, Float.class);
		primitiveWrapperMap.put(Void.TYPE, Void.TYPE);

		TURN_ON_VALUES.add("true");
		TURN_ON_VALUES.add("on");
		TURN_ON_VALUES.add("enable");
		TURN_ON_VALUES.add("enabled");
	}

  /**
   * Maps wrapper {@code Class}es to their corresponding primitive types.
   */
	private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new HashMap<Class<?>, Class<?>>();
	static {
		for (final Class<?> primitiveClass : primitiveWrapperMap.keySet()) {
			final Class<?> wrapperClass = primitiveWrapperMap.get(primitiveClass);
			if (!primitiveClass.equals(wrapperClass)) {
				wrapperPrimitiveMap.put(wrapperClass, primitiveClass);
			}
		}
	}

  /**
   * Returns whether the given {@code type} is a primitive or primitive wrapper ({@link Boolean}, {@link Byte}, {@link Character},
   * {@link Short}, {@link Integer}, {@link Long}, {@link Double}, {@link Float}).
   *
   * @param type
   *            The class to query or null.
   * @return true if the given {@code type} is a primitive or primitive wrapper ({@link Boolean}, {@link Byte}, {@link Character},
   *         {@link Short}, {@link Integer}, {@link Long}, {@link Double}, {@link Float}).
   * @since 3.1
   */
	public static boolean isPrimitiveOrWrapper(final Class<?> type) {
		if (type == null) {
			return false;
		}
		return type.isPrimitive() || isPrimitiveWrapper(type);
	}

  /**
   * Returns whether the given {@code type} is a primitive wrapper ({@link Boolean}, {@link Byte}, {@link Character}, {@link Short},
   * {@link Integer}, {@link Long}, {@link Double}, {@link Float}).
   *
   * @param type
   *            The class to query or null.
   * @return true if the given {@code type} is a primitive wrapper ({@link Boolean}, {@link Byte}, {@link Character}, {@link Short},
   *         {@link Integer}, {@link Long}, {@link Double}, {@link Float}).
   * @since 3.1
   */
  public static boolean isPrimitiveWrapper(final Class<?> type) {
  	if ("java.lang.String".equals(type.getName()))
  		return true;

  	return wrapperPrimitiveMap.containsKey(type);
  }

  private static DecimalFormatSymbols getDecimalFormatSymbols() {
		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(LOCALE_US);
		return decimalFormatSymbols;
	}

	private static NumberFormat getNumberFormat() {
		return NumberFormat.getInstance(LOCALE_US);
	}

	public static String getPathSeparator() {
		return File.separator;
	}

	public static int toNumber(String value) {
		return Integer.parseInt(value);
	}

	public static BigDecimal roundBigDecimal(BigDecimal bigDecimal, int decimalDigits) {
		String roundedString = convertBigDecimalToString(bigDecimal, decimalDigits);
		BigDecimal bigDecimalRet = new BigDecimal(roundedString);
		return bigDecimalRet;
	}

	public static String convertBigDecimalToString(BigDecimal bigDecimal, int decDigits) {
		NumberFormat numberFormat = getNumberFormat();
		String totalValueString = numberFormat.format(bigDecimal.doubleValue());
		String formattedString = totalValueString.replaceAll(String.valueOf(getDecimalFormatSymbols().getGroupingSeparator()), STRING_BLANK);
		int decimalPointPos = formattedString.lastIndexOf(getDecimalFormatSymbols().getDecimalSeparator());
		if (decimalPointPos > 0) {
			if (decimalPointPos + decDigits < formattedString.length()) {
				formattedString = formattedString.substring(0, decimalPointPos + decDigits);
			} else {
				formattedString = formattedString.substring(0, formattedString.length() - 1);
			}
		}
		return formattedString;
	}

	public static BigDecimal addBigDecimals(BigDecimal bigDecimal, BigDecimal valueAdded) {
		BigDecimal bigDecimalTotal = new BigDecimal(0);
		MathContext mathContext = new MathContext(0, RoundingMode.UNNECESSARY);

		bigDecimalTotal = bigDecimal.add(valueAdded, mathContext);
		return bigDecimalTotal;
	}

	public static BigDecimal addBigDecimal(BigDecimal bigDecimal, String value) {
		BigDecimal bigDecimalTotal = new BigDecimal(0);
		MathContext mathContext = new MathContext(0, RoundingMode.UNNECESSARY);

		BigDecimal addedValue = new BigDecimal(value);
		bigDecimalTotal = bigDecimal.add(addedValue, mathContext);

		NumberFormat numberFormat = getNumberFormat();
		String totalValueString = numberFormat.format(bigDecimalTotal.doubleValue());
		String formattedString = totalValueString.replaceAll(String.valueOf(getDecimalFormatSymbols().getGroupingSeparator()), STRING_BLANK);
		bigDecimalTotal = new BigDecimal(formattedString);
		return bigDecimalTotal;
	}

	public static BigDecimal addToBigDecimal(BigDecimal bigDecimal, double value) {
		BigDecimal bigDecimalTotal = new BigDecimal(0);
		MathContext mathContext = new MathContext(0, RoundingMode.UNNECESSARY);

		BigDecimal addedValue = new BigDecimal(value);
		bigDecimalTotal = bigDecimal.add(addedValue, mathContext);
		return bigDecimalTotal;
	}

	public static BigDecimal parseDecimal(String chunk) {
		BigDecimal decimal = null;
		try {
			decimal = new BigDecimal(chunk);
		} catch (Exception e) {
		}
		return decimal;
	}

	public static long extractEmbeddedNumber(String buffer){
		Matcher matcher = Pattern.compile("\\d+").matcher(buffer);
		matcher.find();
		return Long.valueOf(matcher.group());
	}

	public static boolean isEquals(Object data1, Object data2){
		boolean data1NullOrEmpty = isNullOrEmpty(data1);
		boolean data2NullOrEmpty = isNullOrEmpty(data2);

		return (data1NullOrEmpty==data2NullOrEmpty);
	}

	public static boolean isEmpty(Object data){
		return isNullOrEmpty(data);
	}

	public static boolean isNotEmpty(Object data){
		return (false==isNullOrEmpty(data));
	}

	public static void clear(Object data){
		if (isEmpty(data))
			return;
		
		if (data instanceof Collection||data instanceof List)
			((Collection<?>)data).clear();
	}

	private static boolean isNullOrEmpty(Object data) {
		if (data == null)
			return true;

		if (data instanceof String) {
			String stringData = (String) data;
			if (stringData.length() < 1)
				return true;

			return false;
		}

		if (data instanceof Collection){
			return ((Collection<?>)data).isEmpty();
		}

		if (data instanceof List) {
			if (((List<?>) data).size() < 1)
				return true;

			return false;
		}

		if (data instanceof byte[]) {
			return ((byte[]) data).length < 1;
		}

		if (data instanceof int[]) {
			return ((int[]) data).length < 1;
		}

		if (data instanceof long[]) {
			return ((long[]) data).length < 1;
		}
		return false;
	}

	public static boolean propertyExists(Class<?> beanClass, String property){
		Method getterMethod = null;
		Class<?>[] nullParameters = null;
		try {
			/*String uniqueField = IntrospectorUtility.instance().getUniqueField(beanClass);
			System.out.println(uniqueField);
			boolean hasField = IntrospectorUtility.instance().hasField(beanClass, property);
			System.out.println(hasField);*/
			getterMethod = beanClass.getMethod(new StringBuilder("get").append(StringUtils.capitalize(property)).toString(), nullParameters);
		} catch (Exception e) {
			//logger.info(e);
			getterMethod = null;
		}
		return (null != getterMethod);
	}

	public static List<String> getFileNamesFromPath(String path) {
		List<String> fileNames = new ArrayList<String>();
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					fileNames.add(files[i].getName());
				}
			}
		}
		return fileNames;
	}

	public static List<String> getFileNamesFromPath(String path, String includePattern) {
		List<String> fileNames = new ArrayList<String>();
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile() && files[i].getName().contains(includePattern)) {
					fileNames.add(files[i].getName());
				}
			}
		}
		return fileNames;
	}

	public static List<String> getFileNamesFromPath(String path, String includePattern, String includePrefix) {
		List<String> fileNames = new ArrayList<String>();
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile() && files[i].getName().contains(includePattern) && files[i].getName().toLowerCase().startsWith(includePrefix.toLowerCase())) {
					fileNames.add(files[i].getName());
				}
			}
		}
		return fileNames;
	}

	public static List<String> getFileNamesFromPath(String path, String includePattern, String includePrefix, String extension) {
		List<String> fileNames = new ArrayList<String>();
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				int dotPos = fileName.lastIndexOf(".");
				String fileExtension = fileName.substring(dotPos + 1);
				if (files[i].isFile() && fileName.contains(includePattern) && fileName.toLowerCase().startsWith(includePrefix.toLowerCase()) && fileExtension.equalsIgnoreCase(extension)) {
					fileNames.add(files[i].getName());
				}
			}
		}
		return fileNames;
	}

	public static List<String> getFileNamesFromPath(String path, String includePattern, List<String> includePrefixes) {
		List<String> fileNames = new ArrayList<String>();
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile() && files[i].getName().contains(includePattern)) {
					for (String prefix : includePrefixes) {
						if (files[i].getName().toLowerCase().startsWith(prefix.toLowerCase())) {
							fileNames.add(files[i].getName());
						}
					}
				}
			}
		}
		return fileNames;
	}

	public static List<String> getFileNamesFromPath(String path, String includePattern, List<String> includePrefixes, String extension) {
		List<String> fileNames = new ArrayList<String>();
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				int dotPos = fileName.lastIndexOf(".");
				String fileExtension = fileName.substring(dotPos + 1);
				if (files[i].isFile() && fileName.contains(includePattern) && fileExtension.equalsIgnoreCase(extension)) {
					for (String prefix : includePrefixes) {
						if (files[i].getName().toLowerCase().startsWith(prefix.toLowerCase())) {
							fileNames.add(files[i].getName());
						}
					}
				}
			}
		}
		return fileNames;
	}

	public static void moveFile(String fromPath, String toPath) {
		File file = new File(fromPath);
		File processedFile = new File(toPath);
		if (processedFile.exists()) {
			processedFile.delete();
		}
		file.renameTo(processedFile);
	}

	public static void deleteFile(String fileName){
		File delFile = new File(fileName);
		delFile.delete();
	}

	public static void deleteFiles(List<File> files){
		for (File file : files) {
			file.delete();
		}
	}

	public static boolean equals(Object obj1, Object obj2) {
		if (isNullOrEmpty(obj1) || isNullOrEmpty(obj2))
			return false;

		/*
		 * if ((obj1 instanceof VirtualObject) && (obj2 instanceof VirtualObject)) return equalsVirtualEntity((VirtualObject)obj1, (VirtualObject)obj2);
		 */if (obj1 instanceof String)
			return equalsStrings((String) obj1, (String) obj2);

		if (obj1 instanceof Long)
			return equalsLong((Long) obj1, (Long) obj2);

		String class1 = obj1.getClass().getName();
		String class2 = obj2.getClass().getName();
		if (!equalsStrings(class1, class2))
			return false;

		boolean results = false;
		return results;
	}

	public static boolean equalsStrings(String s1, String s2) {
		return s1.equals(s2);
	}

	public static boolean equalsLong(Long l1, Long l2) {
		if (l1 == null && l2 == null)
			return true;

		return (l1.longValue() == l2.longValue());
	}

	public static List<? extends Object> createList(Object[] list) {
		List<Object> valueList = new ArrayList<>();
		for (Object obj : list) {
			valueList.add(obj);
		}
		return valueList;
	}

	public static String getDatePartString(Date date, int field, int style) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// return cal.getDisplayName(field, style, Locale.ENGLISH);
		return STRING_BLANK;
	}

	public static int getDatePart(Date date, int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(field);
	}

	public static String getTimeSerialNumber() {
		StringBuilder sbSerial = new StringBuilder();
		sbSerial.append(Calendar.getInstance().getTimeInMillis());
		return sbSerial.toString();
	}

	public static String capitalFirstCharacter(String string) {
		int firstCharOrder = string.charAt(0);
		firstCharOrder += 32;
		char capitalChar = (char) firstCharOrder;
		StringBuilder sbBuffer = new StringBuilder(string);
		sbBuffer.deleteCharAt(0).insert(0, capitalChar);
		return sbBuffer.toString();
	}

	public static String getEntityName(Class<?> clazz) {
		String entityName = clazz.getSimpleName();
		// entityName = capitalFirstCharacter(entityName);
		return entityName;
	}

	public static String getConvertedEntityName(Class<?> clazz) {
		String entityName = clazz.getSimpleName();
		entityName = capitalFirstCharacter(entityName);
		return entityName;
	}

	public static String numberInWords(long number) {
		NumericConverter converter = new NumericConverter();
		String words = converter.numberInWords(number);
		words = words.trim();
		words += " " + NumericConverter.SPECIAL_END_STATEMENT;
		return capitalFirstCharacter(words);
	}

	public static List<? extends Object> createParameterizedList(Object... parameters) {
		List<Object> list = new ArrayList<>();
		for (int i = 0; i < parameters.length; i++) {
			list.add(parameters[i]);
		}
		return list;
	}

	public static Map<String, Object> createParameterMap(Object... parameters) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();

		int i = 0;
		while (i < parameters.length) {
			String parameterName = (String) parameters[i];
			Object parameterValue = parameters[++i];
			parameterMap.put(parameterName, parameterValue);
			i++;
		}

		return parameterMap;
	}

	public static String toString(Object value) {
		if (null == value)
			return null;

		if (value instanceof String)
			return (String) value;

		if (value instanceof Date)
			return DateTimeUtility.dateToString((Date) value, formattedDatePatterns[0]);

		return value.toString();
	}

	public static Long toLong(Object fromValue) {
		if (CommonUtility.isEmpty(fromValue))
			return null;

		if (fromValue instanceof String)
			return Long.valueOf((String) fromValue);

		throw new RuntimeException("Could not support to create a Long object from: " + fromValue);
	}

	public static Date toDate(Object value, String datePattern) {
		return DateTimeUtility.parseDate((String) value, datePattern);
	}

	public static Date dateAdd(Date date, int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		clearCalendar(calendar);
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	public static Calendar clearCalendar(Calendar calendar) {
		if (isNullOrEmpty(calendar)) {
			calendar = Calendar.getInstance();
		}

		calendar.set(0, 0, 0, 0, 0, 0);
		return calendar;
	}

	public static Boolean toBoolean(Object value) {
		if (isNullOrEmpty(value))
			return null;

		Boolean retBoolean = new Boolean(value.toString());
		return retBoolean;
	}

	public static Date cloneDate(Date fromDate) {
		Date clonedDate = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		clonedDate = cal.getTime();
		return clonedDate;
	}

	public static int getLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static boolean isFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return (1 == calendar.get(Calendar.DAY_OF_MONTH));
	}

	public static boolean isLastDayOfMonth(Date date) {
		int lastDay = getLastDay(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return (lastDay == calendar.get(Calendar.DAY_OF_MONTH));
	}

	public static String encodeWebString(String value) {
		if (null == value)
			return STRING_BLANK;

		return value;
	}

	public static double getDoubleValue(Object object, double defaultValue){
		if (null==object)
			return defaultValue;

		Double doubleObj = null;
		doubleObj = (Double)object;
		return doubleObj.doubleValue();
	}

	static class Match {
		int start;
		String text;
	}

	public static synchronized CommonUtility instance() {
		return instance;
	}

	public void walk(String path) {
		File root = new File(path);
		File[] list = root.listFiles();
		if (list == null)
			return;

		for (File f : list) {
			if (f.isDirectory()) {
				walk(f.getAbsolutePath());
				System.out.println("Dir:" + f.getAbsoluteFile());
			} else {
				System.out.println("File:" + f.getAbsoluteFile());
			}
		}
	}

	public static List<Object> listFromArray(Object[] dataBuffs) {
		List<Object> results = new ArrayList<Object>();
		for (Object dataBuff :dataBuffs){
			results.add(dataBuff);
		}
		return results;
	}

	public static Map<Object, Object> listToMap(List<?> objects, String properties) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Map<Object, Object> result = new HashMap<Object, Object>();
		for (Object object :objects){
			Object key = PropertyUtils.getProperty(object, properties);
			result.put(key,  object);
		}
		return result;
	}
	
	public static Object instantiateInstance(Class<?> aClass){
		Object classInstance = null;
		try {
			classInstance = aClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException (e);
		}
		return classInstance;
	}

	public static long getObjectSize(Object object) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(object);
		oos.close();
		return VARIANCE_SIZE + baos.size();
	}

	private static String hashString(String buffer){
		int middlePos = buffer.length()/2;
		StringBuilder leftSubBuffer = new StringBuilder(buffer.substring(0, middlePos));
		String rightSubBuffer = buffer.substring(middlePos);
		int procPos = 0;
		int rightPos = 0;

		while (rightPos < rightSubBuffer.length()){
			procPos += 2;
			if (procPos >= leftSubBuffer.length()){
				procPos = 0;
			}
			leftSubBuffer.insert(procPos, rightSubBuffer.charAt(rightPos++));
		}
		return leftSubBuffer.toString();
	}

	public static String hashBuffer(String buffer){
		StringBuilder leftSubBuffer = new StringBuilder(hashString(buffer));
		return leftSubBuffer.reverse().toString();
	}

	public static String shuffleString(String string) {
		StringBuilder sb = new StringBuilder(string.length());
		double rnd;
		for (char c : string.toCharArray()) {
			rnd = Math.random();
			if (rnd < 0.34)
				sb.append(c);
			else if (rnd < 0.67)
				sb.insert(sb.length() / 2, c);
			else
				sb.insert(0, c);
		}
		return sb.toString();
	}

	public static UUID getRandomUUID(){
		UUID uniqueKey = UUID.randomUUID();
		return uniqueKey;
	}

	public static String formatPadsWithZeros(short length, long value){
		String expression = new StringBuilder("%1$0").append(length).append("d").toString();
		return String.format(expression, value);
	}

	public static long fetchObjectSize(Object object) throws IOException {
	  final ByteCountingOutputStream out = new ByteCountingOutputStream();
	  new ObjectOutputStream(out).writeObject(object);
	  out.close();
		return VARIANCE_SIZE + out.size();
	}

	public static boolean isOnEnabled(final String value){
		return TURN_ON_VALUES.contains(value.toLowerCase());
	}

	public static boolean getBooleanValue(final String value){
		return isOnEnabled(value);
	}

	/*public static void main(String[] args){
		String userName = "buiquyduc", password = GlobalSecurityHelper.getInstance().encrypt("khongCoPassword");
		UUID uniqueKey = UUID.randomUUID();
		StringBuilder token = new StringBuilder()
			.append(userName)
			.append(GlobalSecurityHelper.DELIMITER_PATTERN)
			.append(uniqueKey.toString())
			.append(GlobalSecurityHelper.DELIMITER_PATTERN)
			.append(password);
		System.out.println(token.toString());
		String shuffledString = shuffleString(token.toString());
		System.out.println(shuffledString);
		String plainText = "BuiQuyDuc:operator";
		String reversedText = hashBuffer(plainText);
		System.out.println(reversedText);

		reversedText = shuffleString(plainText);
		System.out.println(reversedText);
	}*/

	public static String bigDecimalToString(BigDecimal bd){
		return NumberFormat.getInstance(Locale.US).format(bd.longValue());
	}

	public static String bigDecimalIntegerToString(BigDecimal bigDecimalValue, Locale locale){
		if (null==locale){
			locale = CommonUtility.LOCALE_VIETNAMESE;
		}
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(locale);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

		//symbols.setGroupingSeparator('.');
		formatter.setDecimalFormatSymbols(symbols);
		return formatter.format(bigDecimalValue.longValue());
	}

	public static String getApplicableString(String string){
		if (CommonUtility.isNotEmpty(string))
			return string;

		return STRING_BLANK;
	}

	public static String getApplicableString(Object object){
		if (isEmpty(object))
			return STRING_BLANK;

		if (!(object instanceof String))
			return object.toString();

		return (String)object;
	}

	public static List<String> extractJpqlParameters(String jpql){
		//Find the words between : and [space or )]
		Pattern p = Pattern.compile("(?<=\\:\\b).*?(?=\\b[ )]\\b)");
		Matcher m = p.matcher(jpql);
		List<String> parameters = new ArrayList<String>();
		while (m.find()) {
		  parameters.add(m.group());
		}
		//Process the last parameter in case not in matchers
		int lastIndexOf = jpql.lastIndexOf(":");
		if (-1 != lastIndexOf){
			String lastParam = jpql.substring(lastIndexOf+1);
			if (!parameters.contains(lastParam)){
				parameters.add(lastParam);
			}
		}
		
		return parameters;
	}

	public static String decapitalize(String string){
    if (isEmpty(string)) {
        return string;
    }

    char c[] = string.toCharArray();
    c[0] = Character.toLowerCase(c[0]);
    return new String(c);
	}

	public static String trim(String src){
		if (isEmpty(src))
			return STRING_BLANK;

		return src.trim();
	}

	public static String createPreference(Object event){
		String pref = STRING_BLANK;
		if (CommonUtility.isNotEmpty(event))
			pref = event.toString().trim();

		return new StringBuilder("%").append(pref).append("%").toString();
	}

	public static Object cloneObject(Object fromObject){
		if (isEmpty(fromObject))
			return null;

		Object clonedObject = null;
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(fromObject);
			bais = new ByteArrayInputStream(baos.toByteArray());
			ois = new ObjectInputStream(bais);
			clonedObject = ois.readObject();		
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		return clonedObject;
	}

	public static void cloneObject(Object destination, Object original){
		try {
			BeanUtils.copyProperties(destination, original);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public static short countNotEmptyFields(final Object object, boolean publicOnly){
		short counter = 0;
		Class<? extends Object> objectClass = object.getClass();
		Field[] fields = objectClass.getDeclaredFields();
		try {
  		for (short i = 0; i < fields.length; i++) {
  			if (publicOnly) {
  				if (Modifier.isPublic(fields[i].getModifiers())) {
  					if (null != fields[i].get(object)){
  						++counter;
  					}
  				}
  			} else {
  				if (Modifier.isStatic(fields[i].getModifiers()))
  					continue;
  
  				fields[i].setAccessible(true);
  					if (null != fields[i].get(object)){
  						++counter;
  					}
  			}
  		}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return counter;
	}

	protected static boolean isGetter(Method method) {
	   if (Modifier.isPublic(method.getModifiers()) &&
	      method.getParameterTypes().length == 0) {
	         if (method.getName().matches("^get[A-Z].*") &&
	            !method.getReturnType().equals(void.class))
	               return true;
	         if (method.getName().matches("^is[A-Z].*") &&
	            method.getReturnType().equals(boolean.class))
	               return true;
	   }
	   return false;
	}

	protected static boolean isSetter(Method method) {
	   return Modifier.isPublic(method.getModifiers()) &&
	      method.getReturnType().equals(void.class) &&
	         method.getParameterTypes().length == 1 &&
	            method.getName().matches("^set[A-Z].*");
	}

	public static Locale[] getSupportedLocales(){
		Locale[] suppportedLocales = new Locale[]{LOCALE_US, LOCALE_VIETNAMESE};
		return suppportedLocales;
	}

	public static String[] separateStrings(Object object){
		if (isEmpty(object))
			return null;

		String sourceString = (String)object;
		for (String separtor :SEPARATORS){
			if (sourceString.contains(separtor))
				return sourceString.split(separtor);
		}
		//did not contains any separator
		return new String[]{(String)object};
	}

	public static boolean validatePhoneNumber(String phoneNumber, String expression){
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(phoneNumber);
		if(matcher.matches())
			return true;

		return false;
	}

	private static boolean isValidPhoneNumber(String phoneNo){
		if (true==isPhoneNumberValid(phoneNo))
			return true;

		//validate phone numbers of format "38128123". Ho Chi Minh City phone
		if (phoneNo.matches("\\d{7}") || phoneNo.matches("\\d{8}") || phoneNo.matches("\\d{9}") || phoneNo.matches("\\d{10}")) 
			return true;

		//validate phone numbers of format "1234567890"
		if (phoneNo.matches("\\d{10}")) return true;
		//validating phone number with -, . or spaces
		else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
		//validating phone number with extension length from 3 to 5
		else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
		//validating phone number where area code is in braces ()
		else if(phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
		//return false if nothing matches the input
		else return false;
	}

	/** 
	 * isPhoneNumberValid: Validate phone number using Java reg ex.
	 * This method checks if the input string is a valid phone number.
	 * @param email String. Phone number to validate
	 * @return boolean: true if phone number is valid, false otherwise.
	 */
	private static boolean isPhoneNumberValid(String phoneNumber){
		boolean isValid = false;
		/* Phone Number formats: (nnn)nnn-nnnn; nnnnnnnnnn; nnn-nnn-nnnn
			^\\(? : May start with an option "(" .
			(\\d{3}): Followed by 3 digits.
			\\)? : May have an optional ")" 
			[- ]? : May have an optional "-" after the first 3 digits or after optional ) character. 
			(\\d{3}) : Followed by 3 digits. 
			 [- ]? : May have another optional "-" after numeric digits.
			 (\\d{4})$ : ends with four digits.

		         Examples: Matches following phone numbers:
		         (123)456-7890, 123-456-7890, 1234567890, (123)-456-7890

		*/
		//Initialize reg ex for phone number. 
		String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
		CharSequence inputStr = phoneNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if(matcher.matches()){
			isValid = true;
		}
		return isValid;
	}

	private static boolean isInternationalPhone(String phoneNumber){
		PhoneNumber numberProto = null;
		Set<String> supportedRegionCodes = globalPhoneNumberUtil.getSupportedRegions();
		for (String supportedRegionCode :supportedRegionCodes){
			try {
				numberProto = globalPhoneNumberUtil.parse(phoneNumber, supportedRegionCode);
			} catch (NumberParseException e) {
				logger.info("Error to parse phone number: [" + phoneNumber + "] with region code: [" + supportedRegionCode + "]. Error message: " + e.getMessage());
			}

			if (globalPhoneNumberUtil.isValidNumber(numberProto)){
				return true;
			}
		}
		return false;
	}

	public static boolean isPhoneNumber(String number){
		if (isEmpty(number))
			return false;

		if (!preCheckPhoneNumber(number))
			return false;

		if (isInternationalPhone(number))
			return true;

		if (isValidPhoneNumber(number))
			return true;

		return false;
	}

	private static boolean preCheckPhoneNumber(String number){
		String[] regularExpressionPatterns = new String[] {"[0-9]+", "^\\D*(?:\\d[+()-]*){10,}$", "(\\d{3}-)?\\d{2}-\\d{8}", "\\d{3}-\\d{2}-\\d{8}", "\\d{2}-\\d{7}"};
		for (String regExPattern :regularExpressionPatterns){
			try {
				if (number.matches(regExPattern)){
					return true;
				}
			} catch (Exception e) {
				logger.info(e);
			}
		}
		return false;
	}

	public static String stringTruncate(String src, int capacity){
		if (isEmpty(src))
			return STRING_BLANK;

		if (src.length() < capacity)
			return src;

		return src.substring(0, capacity);
	}

	public static String createHumanReadableString(String src){
		if (isEmpty(src))
			return STRING_BLANK;

		StringBuilder humanReadableString = new StringBuilder();
		String[] upperWords = src.split("(?=\\p{Upper})");
		for (String word :upperWords){
			humanReadableString.append(word).append(STRING_SPACE);
		}
		return WordUtils.capitalize(humanReadableString.toString().trim());
	}

	public static boolean isEmailAddreess(String email){
		System.out.println("Checking isEmailAddreess is valid email address or not. ");
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static Map<String, Object> upperCaseStringKeys(Map<Object, Object> map){
		Map<String, Object>  convertedMap = new HashMap<>();
		for (Object key :map.keySet()){
			convertedMap.put(key.toString().toUpperCase(), map.get(key));
		}
		map.clear();
		map.putAll(convertedMap);
		return convertedMap;
	}

	public static String getStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		return sw.toString();
	}
	
	public static boolean isBlankOrNull(String str) {
		if (str == null) {
			return true;
		}
		if (str.trim().equals("")) { //$NON-NLS-1$
			return true;
		}
		return false;
	}

	public static String escapePropertyKey(String propertyKey) {
		return propertyKey.replaceAll("\\s+", "_"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static boolean getBoolean(String b) {
		if (b == null) {
			return false;
		}

		return Boolean.valueOf(b);
	}

	public static boolean getBoolean(Boolean b) {
		if (b == null) {
			return false;
		}

		return b;
	}

	public static boolean getBoolean(Boolean b, boolean defaultValue) {
		if (b == null) {
			return defaultValue;
		}

		return b;
	}

	public static double getDouble(Double d) {
		if (d == null) {
			return 0;
		}

		return d;
	}

	public static int getInteger(Integer d) {
		if (d == null) {
			return 0;
		}

		return d;
	}

	public static int parseInteger(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Exception x) {
			return 0;
		}
	}

	public static int parseInteger(String s, String parseErrorMessage) {
		try {
			return Integer.parseInt(s);
		} catch (Exception x) {
			throw new VpxRuntimeException(parseErrorMessage);
		}
	}

	public static double parseDouble(String s) {
		try {
			return Double.parseDouble(s);
		} catch (Exception x) {
			return 0;
		}
	}

	public static double parseDouble(String s, String parseErrorMessage, boolean mandatory) {
		try {
			return Double.parseDouble(s);
		} catch (Exception x) {
			if (mandatory) {
				throw new VpxRuntimeException(parseErrorMessage);
			}
			else {
				return 0;
			}
		}
	}

	public static String encodeURLString(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8"); //$NON-NLS-1$
		} catch (Exception x) {
			return s;
		}
	}

	public static boolean isNull(Object data){
		return (data==null);
	}

	public static String encodeHex(String plainText){
		byte[] encodedBytes = Base64Utils.encode(plainText.getBytes());
		return Hex.encodeHexString(encodedBytes);
	}

	public static String safeSubString(String source, int begin, int end){
		if (source.length() <= end)
			return source;

		return source.substring(begin, end);
	}

	public static String decodeHex(String encodedHex) throws Exception{
		byte[] hexDecodedBytes = Hex.decodeHex(encodedHex.toCharArray());
		byte[] decodedBytes = Base64Utils.decode(hexDecodedBytes);
		return new String(decodedBytes);
	}

	public static boolean regularExpressionCompiled(final String patternExpression, final String value){
		Pattern pattern = Pattern.compile(patternExpression);
		Matcher matcher = pattern.matcher(value);
		return matcher.find();
	}

	public static InputStream getClassPathResourceInputStream(String relativePath) throws FileNotFoundException, IOException{
		ClassPathResource resource = new ClassPathResource(relativePath);
		InputStream is = new FileInputStream(resource.getFile());
		return is;
	}

	public static void closeInputStream(InputStream inputStream) throws IOException{
		if (null != inputStream){
			inputStream.close();
		}
	}

	public static void main(String[] args){
		String patternExp = "^\\d{2}[.]\\d{2}$";
		String string = "03.02";//"98.12";
		
		Pattern pattern = Pattern.compile(patternExp);
		Matcher matcher = pattern.matcher(string);
		System.out.println(matcher.find());
	}

}
