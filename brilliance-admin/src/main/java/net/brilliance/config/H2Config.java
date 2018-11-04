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
package net.brilliance.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import net.brilliance.config.base.BaseConfiguration;
import net.brilliance.util.Profiles;

/**
 * Configuration specific for {@code h2} profile. This configuration uses Spring
 * Data JPA. The underlying datastore is H2 embedded database for it's JPA
 * access
 * 
 * @author Julius Krah
 *
 */
@Configuration
@Profile({ Profiles.H2, Profiles.HEROKU })
//@EnableJpaRepositories(basePackageClasses = BaseRepositoryImpl.class)
@ComponentScan({
	"net.vpx.repository", 
	"net.vpx.repository.h2", 
	"net.vpx.services", 
	"net.vpx.services.config",
	"net.vpx.manager",
	"net.vpx.quartzscheduler"
})
@EnableJpaRepositories("net.vpx.repository")
public class H2Config extends BaseConfiguration{

}
