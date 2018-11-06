/**
 * 
 */
package net.brilliance.async;

import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import net.brilliance.common.CommonConstants;
import net.brilliance.exceptions.EcosysException;
import net.brilliance.framework.component.ComponentBase;

/**
 * @author ducbq
 *
 */
@Component
public class DataConfigurationDispatcher extends ComponentBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3296828556733459062L;

	@Async
	public void asyncDeployConstructionData(Map<?, ?> contextParams) throws EcosysException {
		logger.info("Enter DataConfigurationDispatcher::asyncDeployConstructionData------------");
		for (int idx = 0; idx < CommonConstants.DUMMY_LARGE_COUNT; idx++){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.info("Leave DataConfigurationDispatcher::asyncDeployConstructionData------------");
	}
}
