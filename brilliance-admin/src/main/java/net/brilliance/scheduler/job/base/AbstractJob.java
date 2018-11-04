/**
 * 
 */
package net.brilliance.scheduler.job.base;

import java.util.Date;

/**
 * @author ducbq
 *
 */
public abstract class AbstractJob {
  private String name;
  private Date startTime;
  private String cronExpression;
  private int misfireInstruction;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public int getMisfireInstruction() {
		return misfireInstruction;
	}
	public void setMisfireInstruction(int misfireInstruction) {
		this.misfireInstruction = misfireInstruction;
	}
}
