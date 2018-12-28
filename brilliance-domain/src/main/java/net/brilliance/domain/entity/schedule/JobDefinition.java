/**
 * 
 */
package net.brilliance.domain.entity.schedule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.brilliance.framework.entity.BizObjectBase;

/**
 * @author ducbq
 *
 */
@Entity
@Table(name = "sys_job_definition")
public class JobDefinition extends BizObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4040612429757111881L;

	//Name of job processor
	@Column(name = "job_name", length=50, nullable=false, unique=true)
	private String code;

	//Name of job trigger
	@Column(name = "trigger_name", length=50)
	private String triggerName;

	@Column(name = "cron_time_expression")
	private String cronTimeExpression;

	@Column(name = "job_execution_class", length=256)
	private String jobExecutionClass;

	@Column(name = "is_active")
	private Boolean isActive = Boolean.TRUE;

	@Column(name = "started")
	private Long started; //Started time without time-zone

	@Column(name = "finished")
	private Long finished;//Finished time without time-zone

	@Column(name = "status", length=20)
	private String status;

	@Column(name = "info")
	private String info;

	@ManyToOne
	@JoinColumn(name = "job_category_id")
	private JobCategory jobCategory;

	public JobDefinition(
			String jobExecutionClass, 
			String jobName, 
			String triggerName, 
			String cronExpression, 
			String info, 
			JobCategory jobCategory){
		this.jobExecutionClass = jobExecutionClass;
		this.code = jobName;
		this.triggerName = triggerName;
		this.cronTimeExpression = cronExpression;
		this.info = info;
		this.jobCategory = jobCategory;
	}

	public JobDefinition(){
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getCronTimeExpression() {
		return cronTimeExpression;
	}

	public void setCronTimeExpression(String cronTimeExpression) {
		this.cronTimeExpression = cronTimeExpression;
	}

	public String getJobExecutionClass() {
		return jobExecutionClass;
	}

	public void setJobExecutionClass(String jobExecutionClass) {
		this.jobExecutionClass = jobExecutionClass;
	}

	public JobCategory getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(JobCategory jobCategory) {
		this.jobCategory = jobCategory;
	}

	public Long getStarted() {
		return started;
	}

	public void setStarted(Long started) {
		this.started = started;
	}

	public Long getFinished() {
		return finished;
	}

	public void setFinished(Long finished) {
		this.finished = finished;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public static JobDefinition getInstance(
			JobCategory jobCategory,
			String jobExecutionClass, 
			String jobName, 
			String cronExpression, 
			String info){
		return new JobDefinition(jobExecutionClass, jobName, "TriggerOf"+jobName, cronExpression, info, jobCategory);
	}
}
