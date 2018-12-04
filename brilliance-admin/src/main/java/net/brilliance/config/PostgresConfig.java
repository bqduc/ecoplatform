/*
* Copyright 2018, Bui Quy Duc
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

import org.springframework.context.annotation.Profile;

import net.brilliance.config.base.BaseConfiguration;
import net.brilliance.util.Profiles;

/**
 * Configuration specific to the {@code postgres} and {@code mysql} profiles. The implementation of
 * JPA here is standard specification JPA. The underlying datastore is
 * PostgreSQL and/or MySQL
 * 
 * @author ducbq
 *
 */
@Profile({ Profiles.POSTGRES, Profiles.HEROKU })
public class PostgresConfig extends BaseConfiguration{

}
