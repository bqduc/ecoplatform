package net.brilliance.service.impl.catalog;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.domain.entity.general.CatalogueSubtype;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.repository.general.catalog.CatalogSubtypeRepository;
import net.brilliance.repository.specification.general.CatalogSubtypeSpecifications;
import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.service.api.inventory.CatalogueSubtypeService;

@Service
public class CatalogueSubtypeServiceImpl extends GenericServiceImpl<CatalogueSubtype, Long> implements CatalogueSubtypeService{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4213057902203249734L;

	@Inject 
	private CatalogSubtypeRepository repository;
	
	protected BaseRepository<CatalogueSubtype, Long> getRepository() {
		return this.repository;
	}

	@Override
	public CatalogueSubtype getOne(String code) throws ObjectNotFoundException {
		return (CatalogueSubtype)super.getOptionalObject(repository.findByCode(code));
	}

	@Override
	protected Page<CatalogueSubtype> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	@Override
	public Page<CatalogueSubtype> getObjects(SearchParameter searchParameter) {
		Page<CatalogueSubtype> pagedProducts = this.repository.findAll(CatalogSubtypeSpecifications.buildSpecification(searchParameter), searchParameter.getPageable());
		//Perform additional operations here
		return pagedProducts;
	}
}
