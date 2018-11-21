package net.brilliance.service.dmx;

import java.io.InputStream;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.domain.entity.dmx.InventoryEntry;
import net.brilliance.domain.model.DataInterfaceModel;
import net.brilliance.domain.model.DataSourceType;
import net.brilliance.exceptions.ExecutionContextException;
import net.brilliance.framework.model.ExecutionContext;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.helper.GlobalDataServicesRepository;
import net.brilliance.model.base.IDataContainer;
import net.brilliance.repository.dmx.InventoryEntryRepository;
import net.brilliance.repository.specification.dmx.InventoryEntryRepositorySpecification;
import net.brilliance.service.api.ObjectNotFoundException;
import net.brilliance.service.api.dmx.InventoryEntryService;

@Service
public class InventoryEntryServiceImpl extends GenericServiceImpl<InventoryEntry, Long> implements InventoryEntryService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6295278179986436285L;

	@Inject 
	private InventoryEntryRepository repository;
	
	protected BaseRepository<InventoryEntry, Long> getRepository() {
		return this.repository;
	}

	@Override
	public InventoryEntry getOne(String code) throws ObjectNotFoundException {
		return (InventoryEntry)super.getOptionalObject(repository.findByIsbn(code));
	}

	@Override
	protected Page<InventoryEntry> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	@Override
	public Page<InventoryEntry> getObjects(SearchParameter searchParameter) {
		Page<InventoryEntry> pagedProducts = this.repository.findAll(InventoryEntryRepositorySpecification.buildSpecification(searchParameter), searchParameter.getPageable());
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
