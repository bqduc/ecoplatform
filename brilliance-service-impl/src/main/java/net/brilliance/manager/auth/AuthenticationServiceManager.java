/**
 * 
 */
package net.brilliance.manager.auth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import net.brilliance.common.CommonUtility;
import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.domain.entity.admin.Authority;
import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.domain.entity.config.Configuration;
import net.brilliance.domain.entity.security.AccessRight;
import net.brilliance.domain.entity.security.Module;
import net.brilliance.domain.entity.security.Permission;
import net.brilliance.domain.model.AuthorityGroup;
import net.brilliance.domain.model.DefaultAccessRightAction;
import net.brilliance.exceptions.AuthenticationException;
import net.brilliance.manager.configuration.ConfigurationManager;
import net.brilliance.manager.configuration.DefaultConfigurations;
import net.brilliance.service.api.admin.UserAuthenticationService;

/**
 * @author ducbq
 *
 */
@Service
public class AuthenticationServiceManager implements Serializable{
	private static final long serialVersionUID = 6272702669192923840L;
	final Logger logger = GlobalLoggerFactory.getLogger(this.getClass());

	@Inject
	private AuthorityManager authorityManager;

	@Inject
	private AccessRightManager accessRightService;

	@Inject
	private UserAuthenticationService userAuthenticationService;

	@Inject
	private ModuleManager moduleManager;

	@Inject
	private AccessRightManager accessRightManager;

	@Inject
	private PermissionManager permissionManager;

	@Inject
	private ConfigurationManager configurationManager;

	public UserAccount authenticate(String principal, String credential) throws AuthenticationException{
		return userAuthenticationService.authenticate(principal, credential);
	}

	public UserAccount getUser(String userToken) throws AuthenticationException{
		return userAuthenticationService.getUser(userToken);
	}

	public UserAccount save(UserAccount user)  throws AuthenticationException{
		return userAuthenticationService.save(user);
	}

	public Map<String, Authority> getAuthorityMap(){
		Map<String, Authority> authoritiesMap = new HashMap<>();
		List<Authority> authorities = authorityManager.getAll();
		for (Authority authority :authorities){
			authoritiesMap.put(authority.getName(), authority);
		}
		return authoritiesMap;
	}

	public void initializeMasterData() throws AuthenticationException {
		logger.info("Setup default master data for user sub-module. ");
		userAuthenticationService.initializeMasterData();

		logger.info("Setup default master data for access right sub-module. ");
		accessRightService.initializeMasterData();

		logger.info("Setup default master data for function/module sub-module. ");
		moduleManager.initializeMasterData();

		setupPermissionsByExample();
		logger.info("Setup default permission data. ");
	}


	protected void setupPermissionsByExample(){
		Configuration setupPermission = configurationManager.getByName(DefaultConfigurations.setupPerms.getConfigurationName());
		if (null != setupPermission && 
				!CommonUtility.BOOLEAN_STRING_FALSE.equalsIgnoreCase(setupPermission.getValue())){
			logger.info("Default permissions is setup already. ");
			return;
		}

		AccessRight viewAccessRight = accessRightManager.getByName(DefaultAccessRightAction.View.getAction());
		AccessRight createAccessRight = accessRightManager.getByName(DefaultAccessRightAction.Create.getAction());
		AccessRight modifyAccessRight = accessRightManager.getByName(DefaultAccessRightAction.Modify.getAction());
		AccessRight deleteAccessRight = accessRightManager.getByName(DefaultAccessRightAction.Delete.getAction());

		List<Module> modules = moduleManager.getAll();

		Authority adminRole = authorityManager.getByName(AuthorityGroup.RoleAdmin.getCode());
		Authority clientRole = authorityManager.getByName(AuthorityGroup.RoleClient.getCode());
		for (Module module :modules){
			this.permissionManager.save(Permission.getInstance(adminRole, module, viewAccessRight));
			this.permissionManager.save(Permission.getInstance(adminRole, module, createAccessRight));
			this.permissionManager.save(Permission.getInstance(adminRole, module, modifyAccessRight));
			this.permissionManager.save(Permission.getInstance(adminRole, module, deleteAccessRight));
		}

		for (Module module :modules){
			this.permissionManager.save(Permission.getInstance(clientRole, module, viewAccessRight));
			this.permissionManager.save(Permission.getInstance(clientRole, module, createAccessRight));
			this.permissionManager.save(Permission.getInstance(clientRole, module, modifyAccessRight));
			this.permissionManager.save(Permission.getInstance(clientRole, module, deleteAccessRight));
		}

		setupPermission = Configuration.getInstance(DefaultConfigurations.setupPerms.getConfigurationName(), CommonUtility.BOOLEAN_STRING_TRUE);
		configurationManager.save(setupPermission);
		/**
		 * 1. Load all modules
		 * 2. For administrator, allow all objects from all modules
		 * 3. For corporate client, allow full permission on defined from UI
		 */
	}
}
