/**
 * 
 */
package net.brilliance.scheduler.config;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import net.brilliance.domain.entity.schedule.JobDefinition;
import net.brilliance.framework.logging.CommonLoggingService;
import net.brilliance.scheduler.QuartzSchedulerServiceHelper;
import net.brilliance.scheduler.job.FoodSynchronizeJob;
import net.brilliance.scheduler.job.SimpleJob;

/**
 * @author ducbq
 *
 */
@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class QuartzSchedulerConfiguration {
	@Inject
	private CommonLoggingService cLog;

	@Inject
	private ApplicationContext context;

	// Automatically autowires data source if configured in application.yml file
	@Inject
	private DataSource dataSource;

	@Inject
	private QuartzSchedulerServiceHelper quartzSchedulerServiceHelper;

	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	protected List<Trigger> generateMasterTriggers() {
		List<Trigger> triggers = generateTriggers();
		//triggers.add(sampleJobTrigger());

		return triggers;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, Trigger simpleJobTrigger) throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setJobFactory(jobFactory);
		factory.setQuartzProperties(quartzProperties());
		factory.setTriggers(simpleJobTrigger, sampleJobTrigger());

		List<Trigger> triggers = generateMasterTriggers();
		triggers.add(simpleJobTrigger);

		factory.setTriggers(triggers.toArray(new Trigger[0]));
		cLog.info("Starting jobs....");
		return factory;
	}

	@Bean
	public SimpleTriggerFactoryBean simpleJobTrigger(@Qualifier("simpleJobDetail") JobDetail jobDetail, @Value("${simplejob.frequency}") long frequency) {
		cLog.info("simpleJobTrigger");

		SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setStartDelay(0L);

		factoryBean.setRepeatInterval(frequency);
		factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		return factoryBean;
	}

	/**
	 * create trigger which executes using cron expression at every 10 second.
	 */
	protected Trigger sampleJobTrigger() {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setJobDetail(sampleJobDetail());
		factoryBean.setName("test-trigger");
		factoryBean.setStartTime(Calendar.getInstance().getTime());
		factoryBean.setCronExpression("0/55 * * * * ?");
		factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);

		try {
			factoryBean.afterPropertiesSet();
		} catch (ParseException e) {
			cLog.error(e);
		}
		CronTrigger object = factoryBean.getObject();
		cLog.info("Cron trigger object: " + object.getDescription());
		return object;
	}

	/**
	 * Configure job
	 */
	public JobDetail sampleJobDetail() {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();

		factoryBean.setJobClass(FoodSynchronizeJob.class);
		factoryBean.setDurability(true);
		factoryBean.setApplicationContext(context);
		factoryBean.setName("test-job");

		factoryBean.afterPropertiesSet();

		return factoryBean.getObject();
	}

	private JobDetail createJobDetail(Class<?> jobClassType, String jobName) {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();

		factoryBean.setJobClass(jobClassType);
		factoryBean.setDurability(true);
		factoryBean.setApplicationContext(context);
		factoryBean.setName(jobName);

		factoryBean.afterPropertiesSet();

		return factoryBean.getObject();
	}

	/**
	 * create trigger which executes using cron expression.
	 */
	private Trigger createJobTrigger(String triggerName, String cronExpression, JobDetail jobDetail) {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setName(triggerName);
		factoryBean.setStartTime(Calendar.getInstance().getTime());
		factoryBean.setCronExpression(cronExpression);
		factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);

		try {
			factoryBean.afterPropertiesSet();
		} catch (ParseException e) {
			cLog.error(e);
		}
		return factoryBean.getObject();
	}

	private List<Trigger> generateTriggers() {
		List<JobDefinition> jobDefinitions = quartzSchedulerServiceHelper.getJobDefinitions();
		cLog.info("Current number of jobs: " + jobDefinitions.size());
		List<Trigger> triggers = new ArrayList<>();
		JobDetail jobDetail = null;
		Trigger triggerDetail = null;
		for (JobDefinition jobDefinition : jobDefinitions) {
			try {
				jobDetail = createJobDetail(Class.forName(jobDefinition.getJobExecutionClass()), jobDefinition.getCode());
				triggerDetail = createJobTrigger(jobDefinition.getTriggerName(), jobDefinition.getCronTimeExpression(), jobDetail);
				triggers.add(triggerDetail);
			} catch (Exception e) {
				cLog.error("Error while creating trgigger for job: " + jobDefinition.getCode(), e);
			}
		}
		return triggers;
	}

	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/config/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	@Bean
	public JobDetailFactoryBean simpleJobDetail() {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(SimpleJob.class);
		factoryBean.setDurability(true);
		return factoryBean;
	}
}
