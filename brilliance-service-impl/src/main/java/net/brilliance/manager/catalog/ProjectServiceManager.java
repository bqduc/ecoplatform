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
package net.brilliance.manager.catalog;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.domain.entity.general.Project;
import net.brilliance.framework.manager.AbstractServiceManager;
import net.brilliance.framework.repository.JBaseRepository;
import net.brilliance.officesuite.GlobalOfficeSuiteRepository;
import net.brilliance.officesuite.model.SpreadsheetContainer;
import net.brilliance.officesuite.model.SpreadsheetDocDataContainer;
import net.brilliance.repository.general.catalog.ProjectRepository;

/**
 * Project service implementation. Provides implementation of the project
 * 
 * @author ducbq
 *
 */
@Service
@Transactional
public class ProjectServiceManager extends AbstractServiceManager<Project, Long> {
	final Logger logger = GlobalLoggerFactory.getLogger(this.getClass());

	private final static int IDX_LICENSE = 0;
	private final static int IDX_ISSUE_DATE = 1;
	private final static int IDX_INVESTOR = 2;
	private final static int IDX_NAME = 3;
	private final static int IDX_INVESTOR_COUNTRY = 4;
	private final static int IDX_BUSINESS_DOMAIN = 5;
	private final static int IDX_INVESTMENT_CAPITAL = 6;
	private final static int IDX_IMPLEMENT_ADDRESS = 7;
	private final static int IDX_DURATION = 8;	
	private final static int IDX_CONTACT_ADDRESS = 10;
	private final static int IDX_INVESTMENT_MODEL = 11;
	
	@Inject
	private ProjectRepository repository;

	@Override
	protected JBaseRepository<Project, Long> getRepository() {
		return this.repository;
	}

	public List<Project> createDummyObjects() {
		List<Project> fetchedObjects = importFromExcel();
		for (Project fetchedObject :fetchedObjects) {
			try {
				this.save(fetchedObject);
			} catch (Exception e) {
				logger.error(CommonUtility.getStackTrace(e));
			}
		}
		return fetchedObjects;
	}

	public Optional<Project> getByName(String name) {
		return repository.findByName(name);
	}

	public Optional<Project> getByCode(String license) {
		return repository.findByCode(license);
	}

	public List<Project> getAll(Integer pageNumber){
    PageRequest pageRequest = new PageRequest(pageNumber - 1, CommonConstants.DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "id");
    Page<Project> pagedProjects = getRepository().findAll(pageRequest);
    return pagedProjects.getContent();
	}

	private List<Project> importFromExcel(){
		List<Project> projects = new ArrayList<>();
		SpreadsheetDocDataContainer dataContainer = null;
		Date issueDate = null;
		String implementComments = null;
		int implementDuration = 0;
		try {
			ClassPathResource resource = new ClassPathResource("/config/data/projects-data.xlsx");
			List<String> sheetNames = new ArrayList<>();
			sheetNames.add("master");
			dataContainer = GlobalOfficeSuiteRepository.instance().fetchExcelData(resource.getInputStream(), sheetNames);
			//dataContainer = GlobalOfficeSuiteRepository.instance().fetchExcelData(resource.getFile());
			/*dataContainer = GlobalOfficeSuiteRepository.instance().fetchExcelData(resource.getFile());
			dataContainer = GlobalOfficeSuiteRepository.instance().fetchExcelData(resource.getURI().getPath());*/
			Map<Object, SpreadsheetContainer> sheetDataMap = dataContainer.getSheetDataContainer();
			List<Object> rowData = null;
			SpreadsheetContainer excelSheetContainer = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String investmentModel = null;
			for (Object worksheet :sheetDataMap.keySet()) {
				excelSheetContainer = (SpreadsheetContainer)sheetDataMap.get(worksheet);
				//Remove the first object, column headers
				excelSheetContainer.getSheetDataContainer().remove(Integer.valueOf(0));
				for (Object key :excelSheetContainer.getSheetDataContainer().keySet()){
					rowData = (List<Object>)excelSheetContainer.getSheetDataContainer().get(key);
					if (CommonUtility.isEmpty(rowData.get(IDX_LICENSE))) {
						this.logger.error("An empty license at [" + key + "]");
						continue;
					}
					try {
						try {
							implementDuration = ((Double)rowData.get(IDX_DURATION)).intValue();
						} catch (Exception e) {
							implementComments = (String)rowData.get(IDX_DURATION);
						}
						if (rowData.get(IDX_ISSUE_DATE) instanceof Date) {
							issueDate = (Date)rowData.get(IDX_ISSUE_DATE);
						}else {
							try {
								if (null != rowData.get(IDX_ISSUE_DATE)) {
									issueDate = sdf.parse(rowData.get(IDX_ISSUE_DATE).toString());
								}
							} catch (Exception e) {
								this.logger.info(CommonUtility.getStackTrace(e));
							}
						}
						try {
							investmentModel = (String)rowData.get(IDX_INVESTMENT_MODEL);
						} catch (Exception e) {
							investmentModel = null;
							//e.printStackTrace();
						}
						projects.add(
		  						Project.instance(
		  								(rowData.get(IDX_LICENSE) instanceof String)?((String)rowData.get(IDX_LICENSE)).trim():rowData.get(IDX_LICENSE).toString().trim(), //License 
		  								((String)rowData.get(IDX_NAME).toString()).trim(), //name
		  								(String)rowData.get(IDX_INVESTOR), //Investor
		  								issueDate, //Issue date
		  								investmentModel, //investmentModel
		  								(null != rowData.get(IDX_INVESTOR_COUNTRY))?rowData.get(IDX_INVESTOR_COUNTRY).toString().trim():"", //investorCountry
		  								(null != rowData.get(IDX_BUSINESS_DOMAIN))?rowData.get(IDX_BUSINESS_DOMAIN).toString().trim():"", //businessDomain
		  								(null != rowData.get(IDX_INVESTMENT_CAPITAL))?new BigDecimal((Double.valueOf(rowData.get(IDX_INVESTMENT_CAPITAL).toString()))):BigDecimal.ZERO, //investmentCapital
		  								BigDecimal.ZERO, //charteredCapital
		  								(null != rowData.get(IDX_IMPLEMENT_ADDRESS))?rowData.get(IDX_IMPLEMENT_ADDRESS).toString():"", //implementAddress
		  								implementDuration, //implement duration
		  								null, //implementDueDate
		  								implementComments, //implementComments
		  								(String)rowData.get(IDX_CONTACT_ADDRESS), //Contact address
		  								issueDate, //Date of license
		  								null)//Description
		  						);

					} catch (Exception e) {
						this.logger.info(CommonUtility.getStackTrace(e));
					}
				}
			}

		} catch (Exception e) {
			this.logger.info(CommonUtility.getStackTrace(e));
		}
		return projects;
	}
}
