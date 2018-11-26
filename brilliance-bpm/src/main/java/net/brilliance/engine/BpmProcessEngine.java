/**
 * 
 */
package net.brilliance.engine;

import org.springframework.stereotype.Component;

import lombok.Builder;
import net.brilliance.exceptions.ExecutionContextException;
import net.brilliance.framework.component.BaseComponent;

/**
 * @author ducbq
 *
 */
@Builder
@Component
public class BpmProcessEngine extends BaseComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5528854202981977057L;

	public void startBusinessProcessEngine() throws ExecutionContextException {
		try {
		/*	ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
			System.out.println(defaultProcessEngine);*/

			/*ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
				  .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
				  .setJdbcUrl("jdbc:h2:mem:my-own-db;DB_CLOSE_DELAY=1000")
				  .setAsyncExecutorActivate(false)
				  .buildProcessEngine();
			System.out.println(processEngine);*/
		} catch (Exception e) {
			throw new ExecutionContextException(e);
		}
	}
}
