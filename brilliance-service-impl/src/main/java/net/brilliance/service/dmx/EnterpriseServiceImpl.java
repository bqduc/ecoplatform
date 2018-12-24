package net.brilliance.service.dmx;

import java.io.InputStream;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.common.CommonUtility;
import net.brilliance.deplyment.DeploymentSpecification;
import net.brilliance.domain.entity.dmx.Enterprise;
import net.brilliance.domain.model.DataInterfaceModel;
import net.brilliance.domain.model.DataSourceType;
import net.brilliance.exceptions.ExecutionContextException;
import net.brilliance.framework.model.ExecutionContext;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.helper.GlobalDataServicesRepository;
import net.brilliance.model.base.IDataContainer;
import net.brilliance.repository.dmx.EnterpriseRepository;
import net.brilliance.repository.specification.dmx.EnterpriseSpecification;
import net.brilliance.service.api.ObjectNotFoundException;
import net.brilliance.service.api.dmx.EnterpriseService;

@Service
public class EnterpriseServiceImpl extends GenericServiceImpl<Enterprise, Long> implements EnterpriseService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7761477574156308888L;

	@Inject 
	private EnterpriseRepository repository;
	
	protected BaseRepository<Enterprise, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Enterprise getOne(String code) throws ObjectNotFoundException {
		return (Enterprise)super.getOptionalObject(repository.findByCode(code));
	}

	@Override
	protected Page<Enterprise> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	@Override
	public Page<Enterprise> getObjects(SearchParameter searchParameter) {
		Page<Enterprise> pagedProducts = this.repository.findAll(EnterpriseSpecification.buildSpecification(searchParameter), searchParameter.getPageable());
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
			if (!(executionContext.containKey(DeploymentSpecification.DEPLOYMENT_DATA_KEY) || 
					executionContext.containKey(DeploymentSpecification.DEPLOYMENT_DATA_MODEL_KEY))){
				executionContext.setExecutionStage("There is not enough deployment specification for enterprise. ");
				logger.info(executionContext.getExecutionStage());
				return executionContext;
			}

			globalDataServicesRepository = GlobalDataServicesRepository.builder().build();
			projectContextData = executionContext.getContextData(DeploymentSpecification.DEPLOYMENT_DATA_KEY);
			dataInterfaceModel = (DataInterfaceModel)executionContext.getContextData(DeploymentSpecification.DEPLOYMENT_DATA_MODEL_KEY);
			if (DataSourceType.CSV.equals(dataInterfaceModel.getDataSourceType())){
				dataContainer = globalDataServicesRepository.readCsvFile(
						(InputStream)projectContextData, 
						dataInterfaceModel.isProcessColumnHeaders(), 
						dataInterfaceModel.getComponentSeparator());
			} else if (DataSourceType.EXCEL.equals(dataInterfaceModel.getDataSourceType())) {
			}

			if (CommonUtility.isEmpty(dataContainer)){
				executionContext.setExecutionStage("The data container is empty. Please recheck data source. ");
				logger.info(executionContext.getExecutionStage());
				return executionContext;
			}
			logger.info("Start to deploy enterprise data ......");
			
		} catch (Exception e) {
			throw new ExecutionContextException(e);
		}

		executionContext.setExecutionStage("The data deployment for project is done. ");
		logger.info(executionContext.getExecutionStage());
		return executionContext;
	}
}
