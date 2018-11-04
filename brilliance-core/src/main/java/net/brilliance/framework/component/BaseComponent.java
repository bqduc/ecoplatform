/**
 * 
 */
package net.brilliance.framework.component;

import java.io.Serializable;

import javax.inject.Inject;

import net.brilliance.framework.logging.CommonLoggingService;

/**
 * @author ducbq
 *
 */
public abstract class BaseComponent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2761943656319335197L;

	@Inject
	protected CommonLoggingService cLog;
}
