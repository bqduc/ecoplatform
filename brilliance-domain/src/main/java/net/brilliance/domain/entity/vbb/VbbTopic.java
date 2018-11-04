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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A forum.
 * 
 * @author ducbq
 */
@Entity
@Table(name = "vbb_topic")
public class VbbTopic extends VirtualBulletinBoard {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2321216753924319608L;

	@Size(max = 150)
	@Column(name="name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private VbbTopic parent;

	@ManyToOne
	@JoinColumn(name = "forum_id")
	private VbbForum forum;

	@ManyToOne
	@JoinColumn(name = "last_post_id")
	private VbbPost lastPost;

	@Column(name="threads")
	private Integer threads = 0;

	@Column(name="posts")
	private Integer posts = 0;

	@Column(name="links")
	private Boolean links = Boolean.FALSE;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public VbbTopic getParent() {
		return parent;
	}

	public void setParent(VbbTopic parent) {
		this.parent = parent;
	}

	public VbbForum getForum() {
		return forum;
	}

	public void setForum(VbbForum forum) {
		this.forum = forum;
	}

	public VbbPost getLastPost() {
		return lastPost;
	}

	public void setLastPost(VbbPost lastPost) {
		this.lastPost = lastPost;
	}

	public Integer getThreads() {
		return threads;
	}

	public void setThreads(Integer threads) {
		this.threads = threads;
	}

	public Integer getPosts() {
		return posts;
	}

	public void setPosts(Integer posts) {
		this.posts = posts;
	}

	public Boolean getLinks() {
		return links;
	}

	public void setLinks(Boolean links) {
		this.links = links;
	}

	public static VbbTopic getInstance(String name, VbbForum forum){
		return getInstance(name, null, forum);
	}

	public static VbbTopic getInstance(String name, VbbTopic parent, VbbForum forum){
		VbbTopic fetchedObject = new VbbTopic();
		fetchedObject.setName(name);
		if (parent != null){
			fetchedObject.setParent(parent);
		}
		fetchedObject.setForum(forum);
		return fetchedObject;
	}
}
