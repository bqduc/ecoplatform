/**
 * 
 */
package net.brilliance.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author ducbq
 *
 */
@Component
public class DataConfigurationDispatcher {
	@Async
	public void onPostConstruct(Object arg){
		System.out.println("------------Fired on DataConfigurationDispatcher::onPostConstruct------------");
		for (int idx = 0; idx < 100000; idx++){
			//System.out.println(idx);
		}
	}
}
