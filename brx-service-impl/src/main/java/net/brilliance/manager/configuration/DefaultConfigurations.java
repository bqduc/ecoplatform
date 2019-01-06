/**
 * 
 */
package net.brilliance.manager.configuration;

/**
 * @author ducbq
 *
 */
public enum DefaultConfigurations {
	setupClientProfile("Setup client profile"),
	setupForum("Setup forum"),
	setupEmployees("Setup employees"),
	setupPerms("Setup default permissions")
	;

	private String configurationName;

	private DefaultConfigurations(String config){
		this.setConfigurationName(config);
	}

	public String getConfigurationName() {
		return configurationName;
	}

	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}
}
