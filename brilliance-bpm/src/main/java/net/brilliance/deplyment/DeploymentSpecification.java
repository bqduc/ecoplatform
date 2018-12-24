/**
 * 
 */
package net.brilliance.deplyment;

/**
 * @author ducbq
 *
 */
public interface DeploymentSpecification {
	final static String DEPLOYMENT_DATA_KEY = "deployedData";
	final static String DEPLOYMENT_DATA_MODEL_KEY = "deployedDataModel";

	final static String CONFIG_GROUP_DEPLOYMENT = "config.deploymentGroup";

	final static String CONTEXT_DEPLOYMENT_PREFIX = "deploy.";
	final static String CONTEXT_DEPLOYMENT_DATA = ".data";

	
	final static String CONTEXT_DATA = "data";
	final static String CONTEXT_PROJECT = "project";
	final static String CONTEXT_ENTERPRISE = "enterprise";
}
