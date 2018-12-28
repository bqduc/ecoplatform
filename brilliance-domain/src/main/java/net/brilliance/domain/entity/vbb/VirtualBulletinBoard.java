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
package net.brilliance.domain.entity.vbb;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.framework.entity.BizObjectBase;

/**
 * A forum.
 * 
 * @author ducbq
 */
@MappedSuperclass
public abstract class VirtualBulletinBoard extends BizObjectBase{
	private static final long serialVersionUID = 1996007596596684474L;

	@Column(name = "description", columnDefinition="TEXT")
	private String description;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "photo", columnDefinition="TEXT")
	private String photo;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "published_date")
	private Date publishedDate;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "created_date")
	private Date createdDate = new Date();

	@ManyToOne
	@JoinColumn(name = "author_id")
	private UserAccount author;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public UserAccount getAuthor() {
		return author;
	}

	public void setAuthor(UserAccount author) {
		this.author = author;
	}
}
