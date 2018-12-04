/**
 * 
 */
package net.brilliance.config.executor;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author ducbq
 *
 */
@Configuration
@EnableAsync
public class AsyncCustomizeExcutor extends AsyncConfigurerSupport {
		@Override
	    public Executor getAsyncExecutor() {
			ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		    executor.setCorePoolSize(10);
		    executor.setMaxPoolSize(10);
		    executor.setQueueCapacity(20);
		    executor.setThreadNamePrefix("AsyncConfigurerSupportDemo");
		    executor.initialize();
		    return executor;		
		}
}
