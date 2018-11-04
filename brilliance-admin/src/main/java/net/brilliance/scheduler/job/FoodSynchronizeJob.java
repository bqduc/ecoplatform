/**
 * 
 */
package net.brilliance.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import net.brilliance.scheduler.QuartzJobCoordinatorService;

/**
 * @author ducbq
 *
 */
public class FoodSynchronizeJob implements Job{
  @Autowired
  private QuartzJobCoordinatorService service;

  @Override
  public void execute(JobExecutionContext jobExecutionContext) {
      service.processFoodSynchronized();
  }
}