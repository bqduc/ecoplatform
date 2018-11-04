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

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.domain.entity.vbb.VbbForum;
import net.brilliance.exceptions.EcosysException;
import net.brilliance.framework.manager.BaseManager;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.manager.configuration.ConfigurationManager;
import net.brilliance.model.Bucket;
import net.brilliance.repository.vbb.VbbForumRepository;
import net.brilliance.repository.vbb.VbbPostRepository;
import net.brilliance.repository.vbb.VbbThreadRepository;
import net.brilliance.repository.vbb.VbbTopicRepository;
import net.brilliance.service.helper.GlobalDataServiceHelper;

/**
 * Forum service implementation. Provides implementation of the forum
 * 
 * @author ducbq
 *
 */
@Service
@Transactional
public class VbbForumManager extends BaseManager<VbbForum, Long> {
	private static final long serialVersionUID = -3621765663559056110L;

	final Logger logger = GlobalLoggerFactory.getLogger(this.getClass());

	@Inject
	private ConfigurationManager configurationManager;

	@Inject
	private VbbForumRepository repository;

	@Inject
	private VbbTopicRepository topicRepository;

	@Inject
	private VbbThreadRepository threadRepository;

	@Inject
	private VbbPostRepository postRepository;

	@Inject 
	private GlobalDataServiceHelper globalDataServiceHelper;

	@Override
	protected BaseRepository<VbbForum, Long> getRepository() {
		return this.repository;
	}

	@Override
	protected Page<VbbForum> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	public VbbForum getByName(String name) {
		return repository.findByName(name);
	}

	public void importBusinessObjects(Bucket dataBucket, String sheetName, int dataStartedIndex) throws EcosysException {
		List<List<String>> dataStrings = null;
		List<String> dataParts = null;
		try {
			dataStrings = (List<List<String>>)dataBucket.getBucketData().get(sheetName);
			for (int i = dataStartedIndex; i < dataStrings.size(); ++i){
				dataParts = dataStrings.get(i);
				/*
				emp = this.parseEmployee(dataParts);
				this.employeeRepository.save(emp);*/
			}
		} catch (Exception e) {
			throw new EcosysException(e);
		}
	}

	
	/*public void setupMasterData() {
		Configuration setupForumConfig = configurationManager.getByName(DefaultConfigurations.setupForum.getConfigurationName());
		if (null==setupForumConfig||CommonUtility.BOOLEAN_STRING_FALSE.equalsIgnoreCase(setupForumConfig.getValue())){
			importForumns();
			
			//Check and save back the configuration to mark that forum data has been setup
			if (null==setupForumConfig){
				setupForumConfig = Configuration.getInstance(DefaultConfigurations.setupForum.getConfigurationName(), CommonUtility.BOOLEAN_STRING_TRUE);
			}else{
				setupForumConfig.setValue(CommonUtility.BOOLEAN_STRING_TRUE);
			}
			configurationManager.save(setupForumConfig);
		}
	}*/
/*
	public void importForumns() {
		Bucket dataBucket = null;
		Xlsx2StringTableParser parserInstance = null;
		try {
			ClassPathResource resource = new ClassPathResource("/config/liquibase/data/forum-structure.xlsx");
			parserInstance = Xlsx2StringTableParser.getInstance(resource.getInputStream());
			dataBucket = parserInstance.parseXlsxData();
			@SuppressWarnings("unchecked")
			List<List<String>> dataMap = (List<List<String>>) dataBucket.getBucketData().get("Sheet1");
			this.parseFoumnData(1, dataMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseFoumnData(int startIndex, List<List<String>> stringTable) {
		int columnIndexForum = 0;
		int columnIndexTopic = 1;
		int columnIndexThread = 2;
		int columnIndexThreadDesc = 3;
		int columnIndexPost = 4;

		try {
			String cellData = null;
			Forum forum = null;
			Topic topic = null;
			net.vpx.domain.entity.vbb.Thread thread = null;
			Post post = null;
			repository.deleteAll();
			topicRepository.deleteAll();
			threadRepository.deleteAll();
			postRepository.deleteAll();
			for (int rowIdx = startIndex; rowIdx < stringTable.size(); ++rowIdx) {
				List<String> rowData = stringTable.get(rowIdx);
				cellData = rowData.get(columnIndexForum);
				if (CommonUtility.isNotEmpty(cellData)) {
					forum = Forum.getInstance(cellData);
					repository.save(forum);
					System.out.println("Processing forumn: " + forum);
					continue;
				}

				cellData = rowData.get(columnIndexTopic);
				if (CommonUtility.isNotEmpty(cellData)) {
					topic = Topic.getInstance(cellData, forum);
					topicRepository.save(topic);
					System.out.println("Processing topic: " + topic);
					continue;
				}

				cellData = rowData.get(columnIndexThread);
				if (CommonUtility.isNotEmpty(cellData)) {
					thread = net.vpx.domain.entity.vbb.Thread.getInstance(cellData, rowData.get(columnIndexThreadDesc), topic);
					threadRepository.save(thread);
					System.out.println("Processing thread: " + thread);
					continue;
				}

				int postIdx = 0;
				while (CommonUtility.isNotEmpty(cellData = rowData.get(columnIndexPost))) {
					String postName = new StringBuilder(thread.getName()).append(": ").append(postIdx+1).toString();
					post = Post.getInstance(postName, thread);
					post.setDescription(cellData);
					try {
						rowData = stringTable.get(++rowIdx);
						postRepository.save(post);
					} catch (Exception e) {
						break;
					}

					if (CommonUtility.isEmpty(rowData)) {
						break;
					}
					System.out.println(forum.getName() + "\t|" + topic.getName() + "\t|" + thread.getName() + "\t|" + post);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
