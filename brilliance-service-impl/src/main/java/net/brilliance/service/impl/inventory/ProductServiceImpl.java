package net.brilliance.service.impl.inventory;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.common.ListUtility;
import net.brilliance.domain.entity.stock.Product;
import net.brilliance.domain.entity.stock.ProductCatalog;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.repository.contact.ProductRepository;
import net.brilliance.repository.specification.inventory.ProductSpecification;
import net.brilliance.service.api.ObjectNotFoundException;
import net.brilliance.service.api.inventory.ProductService;

@Service
public class ProductServiceImpl extends GenericServiceImpl<Product, Long> implements ProductService{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4213057902203249734L;

	@Inject 
	private ProductRepository repository;
	
	protected BaseRepository<Product, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Product getOne(String code) throws ObjectNotFoundException {
		return repository.findByCode(code);
	}

	@Override
	protected Page<Product> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	@Override
	public String deployProducts(List<List<String>> dataStrings) {
		cLog.info("Enter deploy contacts.");
		StringBuilder deployedObjects = new StringBuilder();
		Product deployedObject = null;
		Product currentObject = null;
		for (List<String> dataObjectParts :dataStrings){
			try {
				currentObject = this.parseEntity(dataObjectParts);
				if (this.repository.countByCode(currentObject.getCode())<1){
					deployedObject = repository.saveAndFlush(currentObject);
					deployedObjects.append(deployedObject.getCode()).append(";");
				}
			} catch (Exception e) {
				cLog.error("Error at contact code: " + currentObject.getCode(), e);
			}
		}
		cLog.info("Leave deploy contacts.");
		return deployedObjects.toString();
	}

	private Product parseEntity(List<String> data){
		return Product.getInstance(
				ListUtility.getEntry(data, 0), //Code
				ListUtility.getEntry(data, 2), //First name
				ListUtility.getEntry(data, 1)) //Last name
				/*.setDateOfBirth(DateTimeUtility.createFreeDate((String)ListUtility.getEntry(data, 4)))
				.setPlaceOfBirth((String)ListUtility.getEntry(data, 5))
				.setNationalId((String)ListUtility.getEntry(data, 6))
				.setNationalIdIssuedDate(DateTimeUtility.createFreeDate((String)ListUtility.getEntry(data, 7)))
				.setNationalIdIssuedPlace((String)ListUtility.getEntry(data, 8))
				.setGender(GenderTypeUtility.getGenderType((String)ListUtility.getEntry(data, 21)))
				.setAddress((String)ListUtility.getEntry(data, 14))
				.setPresentAddress((String)ListUtility.getEntry(data, 14), (String)ListUtility.getEntry(data, 22))
				.setBillingAddress((String)ListUtility.getEntry(data, 15), (String)ListUtility.getEntry(data, 22))
				.setPhones(CommonUtility.safeSubString((String)ListUtility.getEntry(data, 18), 0, 50))
				.setCellPhones(CommonUtility.safeSubString((String)ListUtility.getEntry(data, 19), 0, 50))
				.setOverallExpectation((String)ListUtility.getEntry(data, 28))
				.setOverallExperience((String)ListUtility.getEntry(data, 27))
				.setEmail((String)ListUtility.getEntry(data, 20))*/
				.setDescription((String)ListUtility.getEntry(data, 29))
			;
	}

	@Override
	public Page<Product> getObjects(SearchParameter searchParameter) {
		Page<Product> pagedProducts = this.repository.findAll(ProductSpecification.buildSpecification(searchParameter), searchParameter.getPageable());
		for (Product product :pagedProducts.getContent()) {
			for (ProductCatalog productCatalog :product.getProductCatalogues()) {
				product.getCatalogues().add(productCatalog.getCatalog());
			}
		}
		return pagedProducts;
	}
}
