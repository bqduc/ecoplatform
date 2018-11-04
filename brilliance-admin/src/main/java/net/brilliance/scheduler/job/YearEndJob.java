/**
 * 
 */
package net.brilliance.scheduler.job;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import net.brilliance.scheduler.QuartzJobCoordinatorService;
import net.brilliance.scheduler.job.base.AbstractJob;
import net.brilliance.scheduler.job.base.BaseJob;

/**
 * @author ducbq
 *
 */
public class YearEndJob extends AbstractJob implements BaseJob {
	@Autowired
	private QuartzJobCoordinatorService service;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) {
		service.processYearEnd();
	}
}