/**
 * 
 */
package net.brilliance.scheduler.job.base;

import java.util.Date;

import org.quartz.Job;

/**
 * @author ducbq
 *
 */
public abstract interface BaseJob extends Job {
	String getName();
	void setName(String name);

	Date getStartTime();
	void setStartTime(Date startTime);

	String getCronExpression();
	void setCronExpression(String cronExpression);

	int getMisfireInstruction();
	void setMisfireInstruction(int misfireInstruction);
}
