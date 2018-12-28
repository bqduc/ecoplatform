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
package net.brilliance.framework.entity.auth;

import net.brilliance.framework.entity.BizEntity;

/**
 * An abstract authentication account entity.
 * 
 * @author ducbq
 */
public interface AuthAccount extends BizEntity {
	String getSsoId();
	void setSsoId(String ssoId);

	String getPassword();
	void setPassword(String password);

	String getEncryptAlgorithm();
	void setEncryptAlgorithm(String encryptAlgorithm);

	String getEmail();
	void setEmail(String email);

	Boolean getLocked();
	void setLocked(Boolean locked);

	Boolean getSystemAdmin();
	void setSystemAdmin(Boolean systemAdmin);
}