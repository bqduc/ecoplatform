/**
 * 
 */
package net.brilliance.scheduler.job;

import org.quartz.JobExecutionContext;

import net.brilliance.scheduler.job.base.AbstractJob;
import net.brilliance.scheduler.job.base.BaseJob;

/**
 * @author ducbq
 *
 */
public class DataDeploymentTriggerJob extends AbstractJob implements BaseJob {
  /*@Autowired
  private QuartzJobCoordinatorService service;*/

  @Override
  public void execute(JobExecutionContext jobExecutionContext) {
  	doDeployData();
  }

  private void doDeployData(){
  	
  }
}