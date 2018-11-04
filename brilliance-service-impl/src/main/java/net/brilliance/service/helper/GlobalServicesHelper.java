/**
 * 
 */
package net.brilliance.service.helper;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import net.brilliance.exceptions.EcosysException;
import net.brilliance.framework.component.BaseComponent;
import net.brilliance.manager.auth.AuthenticationServiceManager;
import net.brilliance.manager.configuration.ConfigurationManager;

/**
 * @author ducbq
 *
 */
@Component
public class GlobalServicesHelper extends BaseComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5021261610500311738L;

	@Inject 
	private AuthenticationServiceManager authenticationServiceManager;

	@Inject 
	private ConfigurationManager configurationManager;

	/*@Inject 
	private EmployeeManager employeeManager;*/

	public void initDefaultComponents() throws EcosysException {
		cLog.info("Enter GlobalServicesHelper::initDefaultComponents(): Initializing the default components....");
		try {
			configurationManager.initializeMasterData();
			cLog.info("Master default master data for authentication is initialized!");

			authenticationServiceManager.initializeMasterData();
			cLog.info("Master default master data for authentication is initialized!");

			//employeeManager.initDefaultData();
			cLog.info("Default master data for authentication is initialized!");
		} catch (Exception e) {
			throw new EcosysException(e);
		}
		cLog.info("Leave GlobalServicesHelper::initDefaultComponents()");
	}

}
