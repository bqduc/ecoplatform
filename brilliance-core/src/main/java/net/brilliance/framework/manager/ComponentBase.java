/**
 * 
 */
package net.brilliance.framework.manager;

import java.io.Serializable;

import org.slf4j.Logger;

import net.brilliance.common.logging.GlobalLoggerFactory;

/**
 * @author ducbq
 *
 */
public abstract class ComponentBase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1902875290972565306L;
	protected Logger logger = GlobalLoggerFactory.getLogger(this.getClass());
}
