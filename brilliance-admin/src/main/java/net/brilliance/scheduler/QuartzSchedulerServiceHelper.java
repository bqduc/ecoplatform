/**
 * 
 */
package net.brilliance.scheduler;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import net.brilliance.domain.entity.schedule.JobCategory;
import net.brilliance.domain.entity.schedule.JobDefinition;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.scheduler.job.ClientExpirationJob;
import net.brilliance.scheduler.job.DayEndJob;
import net.brilliance.scheduler.job.FeedAlertJob;
import net.brilliance.scheduler.job.FoodSynchronizeJob;
import net.brilliance.scheduler.job.GeneralLedgeReconcileJob;
import net.brilliance.scheduler.job.MonthEndJob;
import net.brilliance.scheduler.job.YearEndJob;
import net.brilliance.service.api.admin.quartz.JobCategoryService;
import net.brilliance.service.api.admin.quartz.JobDefinitionService;

/**
 * @author ducbq
 *
 */
@Component
public class QuartzSchedulerServiceHelper {
	@Inject 
	private JobDefinitionService jobDefinitionManager;

	@Inject 
	private JobCategoryService jobCategoryManager;

	@Inject 
	private JobDefinitionService jobDefinitionService;

	public List<JobDefinition> getJobDefinitions(){
		if (jobDefinitionManager.count() < 1){
			buildSystemJobDefinitions();
		}
		Page<JobDefinition> jobDefinitions = jobDefinitionManager.getObjects(SearchParameter.getInstance());
		return jobDefinitions.getContent();
	}

	private void buildSystemJobDefinitions(){
		List<JobDefinition> jobs = buildJobDefinitions();
		for (JobDefinition job :jobs){
			jobDefinitionService.saveOrUpdate(job);
		}
		/*String defaultJobCategoryName = "Basic job category";
		JobCategory defaultJobCategory = null;
		Optional<JobCategory> jobCategory = jobCategoryManager.getByName(defaultJobCategoryName);
		if (jobCategory.isPresent()){
			defaultJobCategory = jobCategory.get();
		}else{
			defaultJobCategory = new JobCategory().setName("Basic job category").setInfo("Basic job scheduler category");
			jobCategoryManager.save(defaultJobCategory);
		}

		jobDefinitionManager.saveOrUpdate(JobDefinition.getInstance(defaultJobCategory, DayEndJob.class.getName(), DayEndJob.class.getSimpleName(), "0/5 * * * * ?", "Job run at the end of every day"));
		jobDefinitionManager.saveOrUpdate(JobDefinition.getInstance(defaultJobCategory, MonthEndJob.class.getName(), MonthEndJob.class.getSimpleName(), "0/6 * * * * ?", "Job run at the end of every month"));
		jobDefinitionManager.saveOrUpdate(JobDefinition.getInstance(defaultJobCategory, YearEndJob.class.getName(), YearEndJob.class.getSimpleName(), "0/7 * * * * ?", "Job run at the end of every year"));
		jobDefinitionManager.saveOrUpdate(JobDefinition.getInstance(defaultJobCategory, FeedAlertJob.class.getName(), FeedAlertJob.class.getSimpleName(), "0/8 * * * * ?", "Job run to send alert for feeding."));
		jobDefinitionManager.saveOrUpdate(JobDefinition.getInstance(defaultJobCategory, ClientExpirationJob.class.getName(), ClientExpirationJob.class.getSimpleName(), "0/9 * * * * ?", "Job run to scan and make expiration to expired clients"));
		jobDefinitionManager.saveOrUpdate(JobDefinition.getInstance(defaultJobCategory, FoodSynchronizeJob.class.getName(), FoodSynchronizeJob.class.getSimpleName(), "0/11 * * * * ?", "Job run to synchronize food data"));
		jobDefinitionManager.saveOrUpdate(JobDefinition.getInstance(defaultJobCategory, GeneralLedgeReconcileJob.class.getName(), GeneralLedgeReconcileJob.class.getSimpleName(), "0/12 * * * * ?", "Job run to reconcile the general ledger entities"));*/
	}

	private List<JobDefinition> buildJobDefinitions(){
		String defaultJobCategoryName = "Basic job category";
		JobCategory defaultJobCategory = null;
		Optional<JobCategory> jobCategory = jobCategoryManager.getOne(defaultJobCategoryName);
		if (jobCategory.isPresent()){
			defaultJobCategory = jobCategory.get();
		}else{
			defaultJobCategory = JobCategory.builder().name("Basic job category").info("Basic job scheduler category").build();
			jobCategoryManager.saveOrUpdate(defaultJobCategory);
		}

		JobDefinition[] jobs = new JobDefinition[]{
				JobDefinition.getInstance(defaultJobCategory, DayEndJob.class.getName(), DayEndJob.class.getSimpleName(), "0 0/15 * * * ?", "Job run at the end of every day"),
				JobDefinition.getInstance(defaultJobCategory, MonthEndJob.class.getName(), MonthEndJob.class.getSimpleName(), "0 0/16 * * * ?", "Job run at the end of every month"),
				JobDefinition.getInstance(defaultJobCategory, YearEndJob.class.getName(), YearEndJob.class.getSimpleName(), "0 0/17 * * * ?", "Job run at the end of every year"),
				JobDefinition.getInstance(defaultJobCategory, FeedAlertJob.class.getName(), FeedAlertJob.class.getSimpleName(), "0 0/18 * * * ?", "Job run to send alert for feeding."),
				JobDefinition.getInstance(defaultJobCategory, ClientExpirationJob.class.getName(), ClientExpirationJob.class.getSimpleName(), "0 0/19 * * * ?", "Job run to scan and make expiration to expired clients"),
				JobDefinition.getInstance(defaultJobCategory, FoodSynchronizeJob.class.getName(), FoodSynchronizeJob.class.getSimpleName(), "0 0/20 * * * ?", "Job run to synchronize food data"),
				JobDefinition.getInstance(defaultJobCategory, GeneralLedgeReconcileJob.class.getName(), GeneralLedgeReconcileJob.class.getSimpleName(), "0 0/21 * * * ?", "Job run to reconcile the general ledger entities")
		};
		return Arrays.asList(jobs);
	}
}
