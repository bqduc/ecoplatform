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

import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.domain.entity.config.Configuration;
import net.brilliance.domain.entity.config.ConfigurationDetail;
import net.brilliance.framework.manager.BaseManager;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.repository.config.ConfigurationDetailRepository;

/**
 * Configuration detail service implementation. Provides implementation of the configuration detail
 * 
 * @author ducbq
 *
 */
@Service
@Transactional
public class ConfigurationDetailManager extends BaseManager<ConfigurationDetail, Long> {
	private static final long serialVersionUID = -3621765663559056110L;

	final Logger logger = GlobalLoggerFactory.getLogger(this.getClass());

	@Inject
	private ConfigurationDetailRepository repository;

	@Override
	protected BaseRepository<ConfigurationDetail, Long> getRepository() {
		return this.repository;
	}

	@Override
	protected Page<ConfigurationDetail> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	public ConfigurationDetail getByName(String name) {
		return repository.findByName(name);
	}

	public ConfigurationDetail getByConfigurationAndName(Configuration configuration, String name) {
		return repository.findByConfigurationAndName(configuration, name);
	}

	public boolean isExists(String name){
		return repository.isExists(name);
	}
}
