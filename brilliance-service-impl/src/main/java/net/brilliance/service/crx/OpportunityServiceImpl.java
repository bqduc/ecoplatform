package net.brilliance.service.crx;

import java.io.InputStream;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.brilliance.common.CommonUtility;
import net.brilliance.deplyment.DeploymentSpecification;
import net.brilliance.domain.entity.crx.Opportunity;
import net.brilliance.domain.model.DataInterfaceModel;
import net.brilliance.domain.model.DataSourceType;
import net.brilliance.exceptions.ExecutionContextException;
import net.brilliance.framework.model.ExecutionContext;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.helper.GlobalDataServicesRepository;
import net.brilliance.model.base.IDataContainer;
import net.brilliance.repository.crx.OpportunityRepository;
import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.service.api.crx.OpportunityService;

@Service
public class OpportunityServiceImpl extends GenericServiceImpl<Opportunity, Long> implements OpportunityService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7412053874038570241L;

	@Inject 
	private OpportunityRepository repository;
	
	protected BaseRepository<Opportunity, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Opportunity getOne(String name) throws ObjectNotFoundException {
		return (Opportunity)super.getOptionalObject(repository.findByName(name));
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
