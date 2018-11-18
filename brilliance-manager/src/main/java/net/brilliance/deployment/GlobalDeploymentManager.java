/**
 * 
 */
package net.brilliance.deployment;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonManagerConstants;
import net.brilliance.exceptions.ExecutionContextException;
import net.brilliance.framework.component.BaseComponent;
import net.brilliance.framework.model.ExecutionContext;
import net.brilliance.service.api.dmx.ProjectService;

/**
 * @author ducbq
 * Query the configured data and deploy all activated deployment entries
 *
 */
@Component
public class GlobalDeploymentManager extends BaseComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 288413656949421360L;

	@Inject 
	private ProjectService projectService;

	public ExecutionContext deploy(ExecutionContext executionContext) throws ExecutionContextException {
		if (!executionContext.containKey(CommonManagerConstants.CONFIG_GROUP_DEPLOYMENT)){
			cLog.info("There is no configuration for deployment group in execution context. ");
			return executionContext;
		}

		List<String> deploymentParts = (List<String>)executionContext.getContextData(CommonManagerConstants.CONFIG_GROUP_DEPLOYMENT);
		return executionContext;
	}

	protected void deployProjects(ExecutionContext executionContext) throws ExecutionContextException {
		final String DEPLOY_ENTRY = "project";
		ExecutionContext projectExecutionContext = null;
		String contextKey = null;
		Object projectContextData = null;
		try {
			contextKey = new StringBuilder(CommonManagerConstants.CONTEXT_DEPLOYMENT_PREFIX).append(DEPLOY_ENTRY).toString();
			if (!executionContext.containKey(contextKey)){
				cLog.info("There is no configuration for deployment project in execution context. ");
				return;
			}

			projectExecutionContext = ExecutionContext.builder().build();
			projectContextData = executionContext.getContextData(
					new StringBuilder(CommonManagerConstants.CONTEXT_DEPLOYMENT_PREFIX)
					.append(DEPLOY_ENTRY)
					.append(CommonManagerConstants.CONTEXT_DEPLOYMENT_DATA)
					.toString());

			projectExecutionContext.putContextData(CommonConstants.DEPLOYMENT_DATA_KEY, projectContextData);
			projectService.deploy(projectExecutionContext);
		} catch (Exception e) {
			throw new ExecutionContextException(e);
		}
	}

	protected void deployBooks(ExecutionContext executionContext) throws ExecutionContextException {
		final String DEPLOY_ENTRY = "book";
		ExecutionContext projectExecutionContext = null;
		String contextKey = null;
		Object projectContextData = null;
		try {
			contextKey = new StringBuilder(CommonManagerConstants.CONTEXT_DEPLOYMENT_PREFIX).append(DEPLOY_ENTRY).toString();
			if (!executionContext.containKey(contextKey)){
				cLog.info("There is no configuration for deployment project in execution context. ");
				return;
			}

			projectExecutionContext = ExecutionContext.builder().build();
			projectContextData = executionContext.getContextData(
					new StringBuilder(CommonManagerConstants.CONTEXT_DEPLOYMENT_PREFIX)
					.append(DEPLOY_ENTRY)
					.append(CommonManagerConstants.CONTEXT_DEPLOYMENT_DATA)
					.toString());

			projectExecutionContext.putContextData(CommonConstants.DEPLOYMENT_DATA_KEY, projectContextData);
			projectService.deploy(projectExecutionContext);
		} catch (Exception e) {
			throw new ExecutionContextException(e);
		}
	}
}
