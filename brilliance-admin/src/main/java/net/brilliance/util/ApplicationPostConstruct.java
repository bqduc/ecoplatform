/**
 * 
 */
package net.brilliance.util;

import java.util.Calendar;

import javax.inject.Inject;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import net.brilliance.async.DataConfigurationDispatcher;
import net.brilliance.async.ResourcesDispatcher;
import net.brilliance.deployment.InventoryDataDeployer;
import net.brilliance.framework.logging.CommonLoggingService;

/**
 * @author ducbq
 *
 */
@Component
public class ApplicationPostConstruct implements ApplicationListener<ApplicationReadyEvent> {
	@Inject 
	protected CommonLoggingService cLogger;

	@Inject 
	private DataConfigurationDispatcher dataConfigurationDispatcher;
	
	@Inject 
	private ResourcesDispatcher resourcesDispatcher;

	@Inject 
	private InventoryDataDeployer inventoryDataDeployer;
	
	/*@Inject
	private BpmProcessEngine bpmProcessEngine;*/

	/*@Inject 
	private GlobalDataRepositoryManager globalDataRepositoryManager;*/

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println("Fired on application ready event at: " + Calendar.getInstance().getTime());
		try {
			dataConfigurationDispatcher.asyncDeployConstructionData(null);
			resourcesDispatcher.asyncDeployConstructionData(null);
			inventoryDataDeployer.asyncDeployConstructionData(null);
			//globalDataRepositoryManager.initializeGlobalData();
		} catch (Exception e) {
			cLogger.error(e);
		}
	}

}
