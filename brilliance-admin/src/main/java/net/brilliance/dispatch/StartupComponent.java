/**
 * 
 *//*
package net.brilliance.dispatch;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.manager.system.SystemSequenceManager;
import net.brilliance.service.helper.GlobalServicesHelper;

*//**
 * @author ducbq
 *
 *//*
@Component
public class StartupComponent {
	private Logger log = GlobalLoggerFactory.getLogger(this.getClass());

	@Inject 
	private GlobalServicesHelper globalServicesHelper;

	@Inject
	private SystemSequenceManager systemSequenceManager;

	@PostConstruct
	public void init() {
		log.info("Enter StartupComponent::init(): Initializing default master data for authentication....");
		try {
			globalServicesHelper.initDefaultComponents();
			log.info("Master default master data for authentication is initialized!");

			systemSequenceManager.initializeSystemSequence();
			log.info("System sequence initialize is done!");
		} catch (Exception e) {
			log.error("Initialized start up component. ", e);
		}
		log.info("Leave StartupComponent::init()");
	}
}
*/