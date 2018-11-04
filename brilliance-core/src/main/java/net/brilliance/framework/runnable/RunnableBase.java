/**
 * 
 */
package net.brilliance.framework.runnable;

import javax.inject.Inject;

import net.brilliance.framework.logging.CommonLoggingService;

/**
 * @author ducbq
 *
 */
public abstract class RunnableBase implements Runnable {
	@Inject 
	protected CommonLoggingService cLog;
}
