/**
 * 
 */
package net.brilliance.framework.component;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ducbq
 *
 */
public abstract class ComponentBase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8228266753216183339L;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
}
