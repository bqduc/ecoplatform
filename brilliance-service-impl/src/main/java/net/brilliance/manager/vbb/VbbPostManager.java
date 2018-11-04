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
package net.brilliance.manager.vbb;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.domain.entity.vbb.VbbPost;
import net.brilliance.framework.manager.BaseManager;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.repository.vbb.VbbPostRepository;

/**
 * Post service implementation. Provides implementation of the forum
 * 
 * @author ducbq
 *
 */
@Service
@Transactional
public class VbbPostManager extends BaseManager<VbbPost, Long> {
	private static final long serialVersionUID = -3621765663559056110L;

	final Logger logger = GlobalLoggerFactory.getLogger(this.getClass());

	@Inject
	private VbbPostRepository repository;

	@Override
	protected BaseRepository<VbbPost, Long> getRepository() {
		return this.repository;
	}

	@Override
	protected Page<VbbPost> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	public Page<VbbPost> getThreadPosts(Long threadId, Pageable pageable) {
		return repository.findByThreadId(threadId, pageable);
	}

	public VbbPost getByName(String name) {
		return repository.findByName(name);
	}

	public void setupMasterData(){
		repository.save(VbbPost.getInstance("Đại sảnh", null, null));
		repository.save(VbbPost.getInstance("Máy tính để bàn", null, null));
		repository.save(VbbPost.getInstance("Khu Trò chơi-Games", null, null));
		repository.save(VbbPost.getInstance("Khu Sản phẩm công nghệ", null, null));
		repository.save(VbbPost.getInstance("Khu Giao lưu Doanh nghiệp & Người dùng", null, null));
		repository.save(VbbPost.getInstance("Khu vui chơi giải trí", null, null));
		repository.save(VbbPost.getInstance("Khu thương mại - Mua và Bán", null, null));
	}
}
