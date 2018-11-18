/**
 * 
 */
package net.brilliance.framework.deployment;

import net.brilliance.exceptions.ExecutionContextException;
import net.brilliance.framework.model.ExecutionContext;

/**
 * @author ducbq
 *
 */
public interface Deployment {
	void deploy(ExecutionContext executionContext) throws ExecutionContextException;
}
