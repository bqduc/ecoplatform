/*
* Copyright 2017, Bui Quy Duc
* by the @authors tag. See the LICENCE in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package net.brilliance.manager.configuration;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.domain.entity.config.Configuration;
import net.brilliance.exceptions.EcosysException;
import net.brilliance.framework.manager.BaseManager;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.global.WebAdminConstants;
import net.brilliance.model.DateTimePatterns;
import net.brilliance.repository.config.ConfigurationRepository;

/**
 * Configuration service implementation. Provides implementation of the configuration
 * 
 * @author ducbq
 *
 */
@Service
@Transactional
public class ConfigurationManager extends BaseManager<Configuration, Long> {
	private static final long serialVersionUID = -3621765663559056110L;

	final Logger logger = GlobalLoggerFactory.getLogger(this.getClass());

	@Inject
	private ConfigurationRepository repository;

	@Override
	protected BaseRepository<Configuration, Long> getRepository() {
		return this.repository;
	}

	@Override
	protected Page<Configuration> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	public Configuration getByName(String name) {
		return repository.findByName(name);
	}

	public boolean isExists(String name){
		return repository.isExists(name);
	}

	public void initializeMasterData() throws EcosysException{
		logger.info("Setup the default configurations for forums");
		this.setupForumConfigurations();

		logger.info("Setup the default configurations for date/time ");
		this.setupDateTimeConfigurations();

		logger.info("Leave the setup master data of configuration");
	}

	protected void setupForumConfigurations(){
		if (null==repository.findByName(DefaultConfigurations.setupForum.getConfigurationName())){
			repository.save(Configuration.getInstance(DefaultConfigurations.setupForum.getConfigurationName(), "true"));
		}
	}

	protected void setupDateTimeConfigurations() {
		//Setup default date formats
		Configuration config = this.repository.findByName(WebAdminConstants.CONFIG_DATE_FORMAT);
		if (null==config) {
			StringBuilder dateFormats = new StringBuilder();
			for (DateTimePatterns dateTimePattern :DateTimePatterns.values()) {
				dateFormats.append(dateTimePattern.getDateTimePattern()).append(CommonConstants.SEMICOLON);				
			}

			config = Configuration.getInstance(WebAdminConstants.CONFIG_GROUP_DATE_TIME, WebAdminConstants.CONFIG_DATE_FORMAT, dateFormats.toString(), null);
			this.repository.save(config);
		}
	}
}
