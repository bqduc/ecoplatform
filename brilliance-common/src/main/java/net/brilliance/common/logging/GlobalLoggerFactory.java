/**
 * 
 */
package net.brilliance.common.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ducbq
 *
 */
public class GlobalLoggerFactory {
	public static Logger getLogger(Class<?> userClass){
		return LoggerFactory.getLogger(userClass);
	}

	public static Logger getLogger(String userClass){
		return LoggerFactory.getLogger(userClass);
	}
}
