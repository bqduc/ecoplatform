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
@Table(name = "vbb_thread")
@EqualsAndHashCode(callSuper = true)
public class VbbThread extends VirtualBulletinBoard{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3180230535325649269L;

	@Size(max = 150)
	@Column(name="name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private VbbThread parent;

	@ManyToOne
	@JoinColumn(name = "topic_id")
	private VbbTopic topic;

	@Column(name="priority")
	private Byte priority = 0;//0: Low, 1: Normal, 2: Hot

	@Column(name="rating")
	private Byte rating = 0;

	@Column(name="replies")
	private Integer replies = 0;

	@Column(name="views")
	private Integer views = 0;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "last_post")
	private Date lastPost = new Date();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public VbbThread getParent() {
		return parent;
	}

	public void setParent(VbbThread parent) {
		this.parent = parent;
	}

	public VbbTopic getTopic() {
		return topic;
	}

	public void setTopic(VbbTopic topic) {
		this.topic = topic;
	}

	public Byte getRating() {
		return rating;
	}

	public void setRating(Byte rating) {
		this.rating = rating;
	}

	public Integer getReplies() {
		return replies;
	}

	public void setReplies(Integer replies) {
		this.replies = replies;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Date getLastPost() {
		return lastPost;
	}

	public void setLastPost(Date lastPost) {
		this.lastPost = lastPost;
	}

	public Byte getPriority() {
		return priority;
	}

	public void setPriority(Byte priority) {
		this.priority = priority;
	}

	public static VbbThread getInstance(String name, String description, VbbTopic topic){
		return getInstance(name, description, null, topic);
	}

	public static VbbThread getInstance(String name, String description, VbbThread parent, VbbTopic topic){
		VbbThread fetchedObject = new VbbThread();
		fetchedObject.setName(name);
		fetchedObject.setDescription(description);
		if (parent != null){
			fetchedObject.setParent(parent);
		}
		fetchedObject.setTopic(topic);
		return fetchedObject;
	}
}
