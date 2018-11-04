/**
 * 
 */
package net.brilliance.controller.base;

import javax.inject.Inject;

import org.slf4j.Logger;

import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.framework.logging.CommonLoggingService;

/**
 * @author bdq1hc
 *
 */
public abstract class BaseRestController {
	@Inject 
	protected CommonLoggingService cLog;

	protected Logger logger = GlobalLoggerFactory.getLogger(this.getClass());
}
