package net.brilliance.service.dmx;

import java.io.InputStream;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.domain.entity.general.Project;
import net.brilliance.domain.model.DataInterfaceModel;
import net.brilliance.domain.model.DataSourceType;
import net.brilliance.exceptions.ExecutionContextException;
import net.brilliance.framework.model.ExecutionContext;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.helper.GlobalDataServicesRepository;
import net.brilliance.model.base.IDataContainer;
import net.brilliance.repository.general.catalog.ProjectRepository;
import net.brilliance.repository.specification.dmx.ProjectRepositorySpec;
import net.brilliance.service.api.ObjectNotFoundException;
import net.brilliance.service.api.dmx.ProjectService;

@Service
public class ProjectServiceImpl extends GenericServiceImpl<Project, Long> implements ProjectService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6295278179986436285L;

	@Inject 
	private ProjectRepository repository;
	
	protected BaseRepository<Project, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Project getOne(String code) throws ObjectNotFoundException {
		return (Project)super.getOptionalObject(repository.findByCode(code));
	}

	@Override
	protected Page<Project> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	@Override
	public Page<Project> getObjects(SearchParameter searchParameter) {
		Page<Project> pagedProducts = this.repository.findAll(ProjectRepositorySpec.buildSpecification(searchParameter), searchParameter.getPageable());
		//Perform additional operations here
		return pagedProducts;
	}

	@Override
	public ExecutionContext deploy(ExecutionContext executionContext) throws ExecutionContextException {
		GlobalDataServicesRepository globalDataServicesRepository = null;
		Object projectContextData = null;
		DataInterfaceModel dataInterfaceModel = null;
		IDataContainer<String> dataContainer = null;
		try {
			if (!(executionContext.containKey(CommonConstants.DEPLOYMENT_DATA_KEY) || 
					executionContext.containKey(CommonConstants.DEPLOYMENT_DATA_MODEL_KEY))){
				executionContext.setExecutionStage("There is not enough deployment specification for project. ");
				cLog.info(executionContext.getExecutionStage());
				return executionContext;
			}

			globalDataServicesRepository = GlobalDataServicesRepository.builder().build();
			projectContextData = executionContext.getContextData(CommonConstants.DEPLOYMENT_DATA_KEY);
			dataInterfaceModel = (DataInterfaceModel)executionContext.getContextData(CommonConstants.DEPLOYMENT_DATA_MODEL_KEY);
			if (DataSourceType.CSV.equals(dataInterfaceModel.getDataSourceType())){
				dataContainer = globalDataServicesRepository.readCsvFile(
						(InputStream)projectContextData, 
						dataInterfaceModel.isProcessColumnHeaders(), 
						dataInterfaceModel.getComponentSeparator());
			} else if (DataSourceType.EXCEL.equals(dataInterfaceModel.getDataSourceType())) {
				
			}

			if (CommonUtility.isEmpty(dataContainer)){
				executionContext.setExecutionStage("The data container is empty. Please recheck data source. ");
				cLog.info(executionContext.getExecutionStage());
				return executionContext;
			}
			cLog.info("Start to deploy project data ......");
			
		} catch (Exception e) {
			throw new ExecutionContextException(e);
		}

		executionContext.setExecutionStage("The data deployment for project is done. ");
		cLog.info(executionContext.getExecutionStage());
		return executionContext;
	}
}
