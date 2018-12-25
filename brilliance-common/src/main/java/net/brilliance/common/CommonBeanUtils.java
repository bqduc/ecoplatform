/**
 * 
 */
package net.brilliance.common;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.commons.beanutils.BeanUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ducbq
 *
 */
@Slf4j
public class CommonBeanUtils {
  /**
   * String used to separate multiple properties inside of embedded
   * beans.
   */
  private static final String PROPERTY_SEPARATOR = ".";

  /**
   * an empty object array used for null parameter method reflection
   */
  private static Object[] NO_ARGUMENTS_ARRAY = new Object[] {
  };

	public static Object buildObject(Object targetBean, Map<String, Object> params) {
		for (String key :params.keySet()) {
			try {
				BeanUtils.setProperty(targetBean, key, params.get(key));
			} catch (IllegalAccessException | InvocationTargetException e) {
				log.error(CommonUtility.getStackTrace(e));
			}
		}
		return null;
	}

	public static List<String> getBeanPropertyNames(Class<?> beanClass) throws IntrospectionException {
		List<String> propertyNames = ListUtility.createArrayList();
		String propName = null;
		BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
		PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
		for (int i=0; i < descriptors.length; i++) {
    	propName = descriptors[i].getName();
    	if (descriptors[i].getPropertyType().isPrimitive()){
      	propertyNames.add(propName);
    	}
    	//System.out.println("Property with Name: " + propName + " and Type: " + propType);
    }
		return propertyNames;
	}

	/**
   * <p>Gets the specified attribute from the specified object.  For example,
   * <code>getObjectAttribute(o, "address.line1")</code> will return
   * the result of calling <code>o.getAddress().getLine1()</code>.<p>
   *
   * <p>The attribute specified may contain as many levels as you like. If at
   * any time a null reference is acquired by calling one of the successive
   * getter methods, then the return value from this method is also null.</p>
   *
   * <p>When reading from a boolean property the underlying bean introspector
   * first looks for an is&lt;Property&gt; read method, not finding one it will
   * still look for a get&lt;Property&gt; read method.  Not finding either, the
   * property is considered write-only.</p>
   *
   * @param bean the bean to set the property on
   * @param propertyNames the name of the propertie(s) to retrieve.  If this is
   *        null or the empty string, then <code>bean</code> will be returned.
   * @return the object value of the bean attribute
   *
   * @throws PropertyNotFoundException indicates the the given property
   *         could not be found on the bean
   * @throws NoSuchMethodException Not thrown
   * @throws InvocationTargetException if a specified getter method throws an
   *   exception.
   * @throws IllegalAccessException if a getter method is
   *   not public or property is write-only.
   */
  public static Object getObjectAttribute(Object bean, String propertyNames)
      throws
          NoSuchMethodException,
          InvocationTargetException,
          IllegalAccessException {


      Object result = bean;

      StringTokenizer propertyTokenizer = new StringTokenizer(propertyNames, PROPERTY_SEPARATOR);
      Class<?> resultClass = null;
      String currentPropertyName = null;

      PropertyDescriptor propertyDescriptor = null;

      Method readMethod = null;

      // Run through the tokens, calling get methods and
      // replacing result with the new object each time.
      // If the result equals null, then simply return null.
      while (propertyTokenizer.hasMoreElements() && result != null) {
          resultClass = result.getClass();
          currentPropertyName = propertyTokenizer.nextToken();

          propertyDescriptor = getPropertyDescriptor(currentPropertyName, resultClass);

          readMethod = propertyDescriptor.getReadMethod();
          if (readMethod == null) {
              throw new IllegalAccessException(
                  "User is attempting to "
                      + "read from a property that has no read method.  "
                      + " This is likely a write-only bean property.  Caused "
                      + "by property ["
                      + currentPropertyName
                      + "] on class ["
                      + resultClass
                      + "]");
          }

          result = readMethod.invoke(result, NO_ARGUMENTS_ARRAY);
      }

      return result;
  }

	/**
   * <p>Gets the specified attribute from the specified object.  For example,
   * <code>getObjectAttribute(o, "address.line1")</code> will return
   * the result of calling <code>o.getAddress().getLine1()</code>.<p>
   *
   * <p>The attribute specified may contain as many levels as you like. If at
   * any time a null reference is acquired by calling one of the successive
   * getter methods, then the return value from this method is also null.</p>
   *
   * <p>When reading from a boolean property the underlying bean introspector
   * first looks for an is&lt;Property&gt; read method, not finding one it will
   * still look for a get&lt;Property&gt; read method.  Not finding either, the
   * property is considered write-only.</p>
   *
   * @param bean the bean to set the property on
   * @param propertyNames the name of the propertie(s) to retrieve.  If this is
   *        null or the empty string, then <code>bean</code> will be returned.
   * @return the object value of the bean attribute
   *
   * @throws PropertyNotFoundException indicates the the given property
   *         could not be found on the bean
   * @throws NoSuchMethodException Not thrown
   * @throws InvocationTargetException if a specified getter method throws an
   *   exception.
   * @throws IllegalAccessException if a getter method is
   *   not public or property is write-only.
   */
  public static Map<String, Object> getObjectAttributes(Object bean, Set<String> propertyNames)
      throws
          NoSuchMethodException,
          InvocationTargetException,
          IllegalAccessException {
  	
  	Map<String, Object> objectAttributeMap = ListUtility.createMap();
      Object result = null;

      Class<?> beanClass = bean.getClass();

      PropertyDescriptor propertyDescriptor = null;

      Method readMethod = null;

      // Run through the tokens, calling get methods and
      // replacing result with the new object each time.
      // If the result equals null, then simply return null.
      for (String currentPropertyName :propertyNames){
        propertyDescriptor = getPropertyDescriptor(currentPropertyName, beanClass);

        readMethod = propertyDescriptor.getReadMethod();
        if (readMethod == null) {
            throw new IllegalAccessException(
                "User is attempting to "
                    + "read from a property that has no read method.  "
                    + " This is likely a write-only bean property.  Caused "
                    + "by property ["
                    + currentPropertyName
                    + "] on class ["
                    + beanClass
                    + "]");
        }

        result = readMethod.invoke(bean, NO_ARGUMENTS_ARRAY);
        objectAttributeMap.put(currentPropertyName, result);
      }

      return objectAttributeMap;
  }

  /**
   * <p>Sets the specified attribute on the specified object.  For example,
   * <code>getObjectAttribute(o, "address.line1", value)</code> will call
   * <code>o.getAddress().setLine1(value)</code>.<p>
   *
   * <p>The attribute specified may contain as many levels as you like. If at
   * any time a null reference is acquired by calling one of the successive
   * getter methods, then a <code>NullPointerException</code> is thrown.</p>
   *
   * @param bean the bean to call the getters on
   * @param propertyNames the name of the attribute(s) to set.  If this is
   *        null or the empty string, then an exception is thrown.
   * @param value the value of the object to set on the bean property
   *
   * @throws PropertyNotFoundException indicates the the given property
   *         could not be found on the bean
   * @throws IllegalArgumentException if the supplied parameter is not of
   *   a valid type
   * @throws NoSuchMethodException never
   * @throws IllegalAccessException if a getter or setter method is
   *   not public or property is read-only.
   * @throws InvocationTargetException if a specified getter method throws an
   *   exception.
   */
  public static void setObjectAttribute(
      Object bean,
      String propertyNames,
      Object value)
      throws
          IllegalAccessException,
          IllegalArgumentException,
          InvocationTargetException,
          NoSuchMethodException {


      Object result = bean;
      String propertyName = propertyNames;

      // Check if this has some embedded properties.  If it does, use the
      // getObjectAttribute to get the proper object to call this on.
      int indexOfLastPropertySeparator =
          propertyName.lastIndexOf(PROPERTY_SEPARATOR);

      if (indexOfLastPropertySeparator >= 0) {
          String embeddedProperties =
              propertyName.substring(0, indexOfLastPropertySeparator);

          // Grab the final property name after the last property separator
          propertyName =
              propertyName.substring(
                  indexOfLastPropertySeparator + PROPERTY_SEPARATOR.length());

          result = getObjectAttribute(result, embeddedProperties);
      }

      Class<?> resultClass = result.getClass();

      PropertyDescriptor propertyDescriptor =
          getPropertyDescriptor(propertyName, resultClass);

      Method writeMethod = propertyDescriptor.getWriteMethod();
      if (writeMethod == null) {
          throw new IllegalAccessException(
              "User is attempting to write "
                  + "to a property that has no write method.  This is likely "
                  + "a read-only bean property.  Caused by property ["
                  + propertyName
                  + "] on class ["
                  + resultClass
                  + "]");
      }

      writeMethod.invoke(result, new Object[] { value });
  }

  /**
   * Retreives a property descriptor object for a given property.
   * <p>
   * Uses the classes in <code>java.beans</code> to get back
   * a descriptor for a property.  Read-only and write-only
   * properties will have a slower return time.
   * </p>
   *
   * @param propertyName The programmatic name of the property
   * @param beanClass The Class object for the target bean.
   *                  For example sun.beans.OurButton.class.
   * @return a PropertyDescriptor for a property that follows the
   *         standard Java naming conventions.
   * @throws PropertyNotFoundException indicates that the property
   *         could not be found on the bean class.
   */
  private static final PropertyDescriptor getPropertyDescriptor(String propertyName, Class<?> beanClass) {
    BeanInfo beanInfo = null;

    PropertyDescriptor[] propertyDescriptors = null;

      PropertyDescriptor resultPropertyDescriptor = null;

      char[] pNameArray = propertyName.toCharArray();
      pNameArray[0] = Character.toLowerCase(pNameArray[0]);
      propertyName = new String(pNameArray);

      try {
          resultPropertyDescriptor = new PropertyDescriptor(propertyName, beanClass);
      } catch (IntrospectionException e1) {
          // Read-only and write-only properties will throw this
          // exception.  The properties must be looked up using
          // brute force.

          // This will get the list of all properties and iterate
          // through looking for one that matches the property
          // name passed into the method.
          try {
              beanInfo = Introspector.getBeanInfo(beanClass);

              propertyDescriptors = beanInfo.getPropertyDescriptors();

              for (int i = 0; i < propertyDescriptors.length; i++) {
                  if (propertyDescriptors[i]
                      .getName()
                      .equals(propertyName)) {

                      // If the names match, this this describes the
                      // property being searched for.  Break out of
                      // the iteration.
                      resultPropertyDescriptor = propertyDescriptors[i];
                      break;
                  }
              }
          } catch (IntrospectionException e2) {
              log.error(e2.getMessage(), e2);
          }
      }

      // If no property descriptor was found, then this bean does not
      // have a property matching the name passed in.
      if (resultPropertyDescriptor == null) {
      	log.info("resultPropertyDescriptor == null");
      }

      return resultPropertyDescriptor;
  }
}
