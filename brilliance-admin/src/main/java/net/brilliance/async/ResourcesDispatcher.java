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
public class ResourcesDispatcher {
	@Async
	public void onPostConstruct(Object arg){
		System.out.println("------------Fired on ResourcesDispatcher::onPostConstruct------------");
		for (int idx = 0; idx < 100; idx++){
			//System.out.println("->" + idx);
		}
	}
}
