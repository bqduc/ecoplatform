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

/**
 * @author ducbq
 *
 */
@Component
public class ApplicationPostConstruct implements ApplicationListener<ApplicationReadyEvent> {
	@Inject 
	private DataConfigurationDispatcher dataConfigurationDispatcher;
	
	@Inject 
	private ResourcesDispatcher resourcesDispatcher;

	@Inject 
	private InventoryDataDeployer inventoryDataDeployer;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println("Fired onApplicationEvent at: " + Calendar.getInstance().getTime());
		dataConfigurationDispatcher.onPostConstruct(null);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		resourcesDispatcher.onPostConstruct(null);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		//inventoryDataDeployer.deployData(null);
	}

}
