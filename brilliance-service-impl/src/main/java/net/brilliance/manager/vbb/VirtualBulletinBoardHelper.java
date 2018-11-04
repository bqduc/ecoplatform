/**
 * 
 */
package net.brilliance.manager.vbb;

import java.util.List;

import javax.inject.Inject;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.CommonUtility;
import net.brilliance.domain.entity.config.Configuration;
import net.brilliance.domain.entity.vbb.VbbForum;
import net.brilliance.domain.entity.vbb.VbbPost;
import net.brilliance.domain.entity.vbb.VbbThread;
import net.brilliance.domain.entity.vbb.VbbTopic;
import net.brilliance.exceptions.EcosysException;
import net.brilliance.manager.configuration.ConfigurationManager;
import net.brilliance.manager.configuration.DefaultConfigurations;
import net.brilliance.model.Bucket;
import net.brilliance.service.helper.GlobalDataServiceHelper;
import net.brilliance.service.helper.GlobalRepositoryHelper;

/**
 * @author ducbq
 *
 */
@Component
public class VirtualBulletinBoardHelper {
	@Inject
	private ConfigurationManager configurationManager;

	@Inject 
	private VbbForumManager forumManager;

	@Inject 
	private VbbTopicManager topicManager;

	@Inject 
	private VbbThreadManager threadManager;

	@Inject 
	private VbbPostManager postManager;

	@Inject
	private GlobalRepositoryHelper globalRepositoryHelper;

	@Inject 
	private GlobalDataServiceHelper dataRepositoryHelper;

	@Transactional
	public void setupDefaultMasterData(){
	}

	/*private String[] forums = new String[]{"Diễn đàn chủ", "Đại sảnh", "Máy tính để bàn", "Khu Trò chơi-Games", "Khu Sản phẩm công nghệ", 
			"Khu Giao lưu Doanh nghiệp & Người dùng", "Khu vui chơi giải trí", "Khu thương mại - Mua và Bán"};

	private String[] hallTopics = new String[]{"Thông báo", "Thắc mắc & Góp ý", "Thread post sai mục", "Tin tức iNet", "Review sản phẩm"};
	private String[] desktopTopics = new String[]{"Thông báo", "Thắc mắc & Góp ý", "Thread post sai mục", "Tin tức iNet", "Review sản phẩm"};
	private String[] gameTopics = new String[]{"Thông báo", "Thắc mắc & Góp ý", "Thread post sai mục", "Tin tức iNet", "Review sản phẩm"};
	private String[] entertimentTopics = new String[]{"Chuyện trò linh tinh", "Superthreads", "From f17 with Love", "Tin tức iNet", "Review sản phẩm"};*/

	@Transactional
	public void setupDefaultData() throws EcosysException {
		Configuration setupForumConfig = configurationManager.getByName(DefaultConfigurations.setupForum.getConfigurationName());
		if (null==setupForumConfig||CommonUtility.BOOLEAN_STRING_FALSE.equalsIgnoreCase(setupForumConfig.getValue())){
			importForums();
			
			//Check and save back the configuration to mark that forum data has been setup
			if (null==setupForumConfig){
				setupForumConfig = Configuration.getInstance(DefaultConfigurations.setupForum.getConfigurationName(), CommonUtility.BOOLEAN_STRING_TRUE);
			}else{
				setupForumConfig.setValue(CommonUtility.BOOLEAN_STRING_TRUE);
			}
			configurationManager.save(setupForumConfig);
		}
	}

	@SuppressWarnings("unchecked")
	public void importForums() throws EcosysException{
		Bucket dataBucket = null;
		//SpreadsheetStringTableDataParser parserInstance = null;
		ClassPathResource resource = null;
		List<List<String>> dataMap = null;
		try {
			resource = new ClassPathResource(globalRepositoryHelper.getDataDirectory() + "forum-structure.xlsx");
			/*parserInstance = SpreadsheetStringTableDataParser.getInstance(resource.getInputStream());
			dataBucket = parserInstance.parseXlsxData();*/
			dataBucket = dataRepositoryHelper.readSpreadsheetData(resource.getInputStream());
			dataMap = (List<List<String>>) dataBucket.getBucketData().get("Sheet1");
			this.parseAndImportForumData(1, dataMap);
		} catch (Exception e) {
			throw new EcosysException(e);
		}
	}

	private void parseAndImportForumData(int startIndex, List<List<String>> stringTable) throws EcosysException {
		int columnIndexForum = 0;
		int columnIndexTopic = 1;
		int columnIndexThread = 2;
		int columnIndexThreadDesc = 3;
		int columnIndexPost = 4;
		int postIdx = 0;

		String cellData = null;
		VbbForum forum = null;
		VbbTopic topic = null;
		VbbThread thread = null;
		VbbPost post = null;
		List<String> rowData = null;
		String postName = null;
		try {
			postManager.deleteAll();
			threadManager.deleteAll();
			topicManager.deleteAll();
			forumManager.deleteAll();
			for (int rowIdx = startIndex; rowIdx < stringTable.size(); ++rowIdx) {
				rowData = stringTable.get(rowIdx);
				cellData = rowData.get(columnIndexForum);
				if (CommonUtility.isNotEmpty(cellData)) {
					forum = VbbForum.getInstance(cellData);
					forumManager.save(forum);
					//System.out.println("Processing forumn: " + forum);
					continue;
				}

				cellData = rowData.get(columnIndexTopic);
				if (CommonUtility.isNotEmpty(cellData)) {
					topic = VbbTopic.getInstance(cellData, forum);
					topicManager.save(topic);
					//System.out.println("Processing topic: " + topic);
					continue;
				}

				cellData = rowData.get(columnIndexThread);
				if (CommonUtility.isNotEmpty(cellData)) {
					thread = VbbThread.getInstance(cellData, rowData.get(columnIndexThreadDesc), topic);
					threadManager.save(thread);
					//System.out.println("Processing thread: " + thread);
					continue;
				}

				while (CommonUtility.isNotEmpty(cellData = rowData.get(columnIndexPost))) {
					postName = new StringBuilder(thread.getName()).append(": ").append(postIdx+1).toString();
					post = VbbPost.getInstance(postName, thread);
					post.setDescription(cellData);
					try {
						rowData = stringTable.get(++rowIdx);
						postManager.save(post);
					} catch (Exception e) {
						break;
					}

					if (CommonUtility.isEmpty(rowData)) {
						break;
					}
					//System.out.println(forum.getName() + "\t|" + topic.getName() + "\t|" + thread.getName() + "\t|" + post);
				}
			}
		} catch (Exception e) {
			 throw new EcosysException(e);
		}
	}
}
