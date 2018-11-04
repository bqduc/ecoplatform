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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.CommonConstants;
import net.brilliance.domain.entity.general.Book;
import net.brilliance.framework.manager.AbstractServiceManager;
import net.brilliance.framework.repository.JBaseRepository;
import net.brilliance.officesuite.GlobalOfficeSuiteRepository;
import net.brilliance.officesuite.model.DataContainer;
import net.brilliance.repository.general.catalog.BookRepository;

/**
 * Book service implementation. Provides implementation of the department
 * 
 * @author ducbq
 *
 */
@Service
@Transactional
public class BookServiceManager extends AbstractServiceManager<Book, Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6005224094134853078L;
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
	
	@Inject
	private BookRepository repository;

	@Override
	protected JBaseRepository<Book, Long> getRepository() {
		return this.repository;
	}

	public List<Book> createDummyObjects() {
		List<Book> fetchedObjects = importFromExcel();
		for (Book fetchedObject :fetchedObjects) {
			try {
				this.save(fetchedObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fetchedObjects;
		/*Book fetchObject = null;
		List<Book> fetchedObjects = new ArrayList<>();
		for (int i = 1; i <= CommonUtility.MAX_DUMMY_OBJECTS; ++i){
			fetchObject = new Book();
			this.save(fetchObject);
			fetchedObjects.add(fetchObject);
		}
		return fetchedObjects;*/
	}

	public Optional<Book> getByName(String name) {
		return repository.findByName(name);
	}

	public Optional<Book> getByIsbn(String isbn) {
		return repository.findByIsbn(isbn);
	}

	public List<Book> getAll(Integer pageNumber){
    PageRequest pageRequest = new PageRequest(pageNumber - 1, CommonConstants.DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "id");
    Page<Book> pagedBooks = getRepository().findAll(pageRequest);
    return pagedBooks.getContent();
	}

	private List<Book> importFromExcel(){
		List<Book> projects = new ArrayList<>();
		DataContainer dataContainer = null;
		Date issueDate = null;
		String implementComments = null;
		int implementDuration = 0;
		try {
			ClassPathResource resource = new ClassPathResource("/config/data/projects-data.xlsx");
			dataContainer = GlobalOfficeSuiteRepository.instance().fetchExcelData(resource.getFile());
			/*dataContainer = GlobalOfficeSuiteRepository.instance().fetchExcelData(resource.getFile());
			dataContainer = GlobalOfficeSuiteRepository.instance().fetchExcelData(resource.getURI().getPath());*/
			Map<Object, Object> dataMap = dataContainer.getBucketData();
			
			for (Object worksheet :dataMap.keySet()) {
				System.out.println("Data of worksheet: " + worksheet);
				if (dataMap.get(worksheet) instanceof Map) {
					/*Map itemMap = (Map)dataMap.get(worksheet);
					for (Object innerKey :itemMap.keySet()) {
						displayData((List<DataContainer>)itemMap.get(innerKey));
					}*/
				}else if (dataMap.get(worksheet) instanceof List) {
					List<DataContainer> objectList = (List<DataContainer>)dataMap.get(worksheet);
					System.out.println("Number of rows: " + objectList.size());
					String license = null;
					int pos = -1;
					String parsedData = null;
					for (DataContainer object :objectList) {
						implementComments = null;
						implementDuration = 0;
						issueDate = null;
						parsedData = object.getContainer().get(IDX_LICENSE).toString().trim();
						pos = parsedData.indexOf("471");
						try {
							if (pos != -1) {
								if (parsedData.length() > 12+pos) {
									license = parsedData.substring(pos, pos+12);
								}else {
									license = parsedData.substring(pos);
								}
							}else {
								license = parsedData;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							try {
								implementDuration = ((Double)object.getContainer().get(IDX_DURATION)).intValue();
							} catch (Exception e) {
								implementComments = (String)object.getContainer().get(IDX_DURATION);
							}
							if (object.getContainer().get(IDX_ISSUE_DATE) instanceof Date) {
								issueDate = (Date)object.getContainer().get(IDX_ISSUE_DATE);
							}else {
								try {
									SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
									if (null != object.getContainer().get(IDX_ISSUE_DATE)) {
										issueDate = sdf.parse(object.getContainer().get(IDX_ISSUE_DATE).toString());
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							/*projects.add(
		  						Book.instance(
		  								license, 
		  								(String)object.getContainer().get(IDX_NAME).toString().trim(), //name
		  								(String)object.getContainer().get(IDX_INVESTOR), //Investor
		  								issueDate, //Issue date
		  								null, //investmentModel
		  								(null != object.getContainer().get(IDX_INVESTOR_COUNTRY))?object.getContainer().get(IDX_INVESTOR_COUNTRY).toString().trim():"", //investorCountry
		  								(null != object.getContainer().get(IDX_BUSINESS_DOMAIN))?object.getContainer().get(IDX_BUSINESS_DOMAIN).toString().trim():"", //businessDomain
		  								(null != object.getContainer().get(IDX_INVESTMENT_CAPITAL))?new BigDecimal((Double.valueOf(object.getContainer().get(IDX_INVESTMENT_CAPITAL).toString()))):BigDecimal.ZERO, //investmentCapital
		  								BigDecimal.ZERO, //charteredCapital
		  								(null != object.getContainer().get(IDX_IMPLEMENT_ADDRESS))?object.getContainer().get(IDX_IMPLEMENT_ADDRESS).toString():"", //implementAddress
		  								implementDuration, //implement duration
		  								null, //implementDueDate
		  								implementComments, //implementComments
		  								(String)object.getContainer().get(IDX_CONTACT_ADDRESS), //Contact address
		  								issueDate, //Date of license
		  								null)//Description
		  						);*/
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return projects;
	}
}
