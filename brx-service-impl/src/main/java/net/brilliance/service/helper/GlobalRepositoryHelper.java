/**
 * 
 */
package net.brilliance.service.helper;

import org.springframework.stereotype.Component;

import net.brilliance.framework.component.BaseComponent;

/**
 * @author ducbq
 *
 */
@Component
public class GlobalRepositoryHelper extends BaseComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6567346477426198878L;

	private final String defaultDataDirectory = "/config/liquibase/data/";

	public String getDataDirectory(){
		return defaultDataDirectory;
	}
}
