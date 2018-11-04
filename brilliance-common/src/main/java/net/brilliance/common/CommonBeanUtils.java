/**
 * 
 */
package net.brilliance.common;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ducbq
 *
 */
@Slf4j
public class CommonBeanUtils {
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
}
