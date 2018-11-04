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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A forum.
 * 
 * @author ducbq
 */
@Data
@Entity
@Table(name = "vbb_post")
@EqualsAndHashCode(callSuper = true)
public class VbbPost extends VirtualBulletinBoard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8522297422378676228L;

	@Size(max = 150)
	@Column(name="name")
	private String name;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "banned_date")
	private Date bannedDate;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private VbbPost parent;

	@ManyToOne
	@JoinColumn(name = "thread_id")
	private VbbThread thread;

	@Column(name="rating")
	private Byte rating = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBannedDate() {
		return bannedDate;
	}

	public void setBannedDate(Date bannedDate) {
		this.bannedDate = bannedDate;
	}

	public VbbPost getParent() {
		return parent;
	}

	public void setParent(VbbPost parent) {
		this.parent = parent;
	}

	public VbbThread getThread() {
		return thread;
	}

	public void setThread(VbbThread thread) {
		this.thread = thread;
	}

	public Byte getRating() {
		return rating;
	}

	public void setRating(Byte rating) {
		this.rating = rating;
	}

	public static VbbPost getInstance(String name, VbbThread thread){
		return getInstance(name, null, thread);
	}
	
	public static VbbPost getInstance(String name, VbbPost parent, VbbThread thread){
		VbbPost fetchedObject = new VbbPost();
		fetchedObject.setName(name);
		if (parent != null){
			fetchedObject.setParent(parent);
		}
		fetchedObject.setThread(thread);
		return fetchedObject;
	}
}
