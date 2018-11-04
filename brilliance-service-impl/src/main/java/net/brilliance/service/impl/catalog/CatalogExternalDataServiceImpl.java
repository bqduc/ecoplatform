package net.brilliance.service.impl.catalog;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.common.ListUtility;
import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.domain.entity.general.Catalogue;
import net.brilliance.domain.entity.general.MeasureUnit;
import net.brilliance.domain.entity.stock.Product;
import net.brilliance.domain.entity.stock.ProductCatalog;
import net.brilliance.exceptions.EcosysException;
import net.brilliance.manager.catalog.CatalogRegistryServiceHelper;
import net.brilliance.model.Bucket;
import net.brilliance.officesuite.SpreadsheetXSSFEventBasedExtractor;
import net.brilliance.repository.general.ProductRepositoryOrigin;
import net.brilliance.repository.general.catalog.CatalogRepository;
import net.brilliance.repository.general.catalog.MeasureUnitRepository;
import net.brilliance.service.api.catalog.CatalogExternalDataException;
import net.brilliance.service.api.catalog.CatalogExternalDataService;

@Service
public class CatalogExternalDataServiceImpl implements CatalogExternalDataService {
	private Logger logger = GlobalLoggerFactory.getLogger(this.getClass());

	@Inject
	private CatalogRegistryServiceHelper catalogRegistryServiceHelper;

	@Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private ProductRepositoryOrigin productRepository;

    @Autowired
    private MeasureUnitRepository measureUnitRepository;

  	private enum CatalogCodeRegExp{
  		PART("^(PHẦN) .*$"),
  		SECTION("^(Chương) .*$"),
  		CATALOG("^\\d+(\\.\\d+)*$");
  		
  		private String regExp;

  		private CatalogCodeRegExp(String regExp){
  			this.regExp = regExp;
  		}

  		public String getRegExp() {
  			return this.regExp;
  		}
  	};

 
    /**
     * Imports the catalog and it's dependencies from external source data. At present only support for Excel.
     * 
     * @param inputStream The source input stream of external data
     * @param params The additional parameters
     * @return The execution status message
     * @throws CatalogExternalDataException If any error causes during the execution.
     */
		@Override
		public String importExternalCatalogues(Map<Object, Object> configParams) throws CatalogExternalDataException {
			try {
				catalogRegistryServiceHelper.registerCataloguesFromExcel(configParams);
				//Bucket bucket = SpreadsheetXSSFEventBasedExtractor.extractSpreadsheetData(new FileInputStream(resourceFileName), configParams);
				//parseCatalogues((List<List<String>>)bucket.get(sheet), 0);
			} catch (Exception e) {
				throw new CatalogExternalDataException(e);
			}
			return "true";
		}

		private void parseCatalogues(List<List<String>> bucketDataElements, int startedIndex) throws CatalogExternalDataException{
			Catalogue currentPartCatalog = null;
			Catalogue currentSectionCatalog = null;

			Catalogue catalog = null;
			Catalogue parentCatalog = null;
			Product product = null;
			MeasureUnit unit;
			Map<String, MeasureUnit> unitMap = ListUtility.createMap();
			Object[] buckets = null;
			List<Product> products = ListUtility.createArrayList();		
			List<String> elements = null;
			Optional<Catalogue> catalogOptional = null;
			Optional<MeasureUnit> unitOptional = null;
			try {
				int bucketSize = bucketDataElements.size();
				for (int elIdx = startedIndex; elIdx < bucketSize; elIdx++){
					elements = bucketDataElements.get(elIdx);
					if (CommonUtility.isNotEmpty(elements.get(0)) && CommonUtility.isEmpty(elements.get(1)) && CommonUtility.isEmpty(elements.get(2))){
						if (Pattern.compile(CatalogCodeRegExp.PART.getRegExp()).matcher(elements.get(0)).find()){//Super catalog (Part)
							buckets = buildFullCatalog(elIdx, bucketDataElements, CatalogCodeRegExp.SECTION.getRegExp());
							try {
								catalog = (Catalogue)buckets[0];
								if (null==catalog.getId())
									this.catalogRepository.saveAndFlush(catalog);

								currentPartCatalog = this.catalogRepository.findOne(catalog.getId());
								elIdx = (Integer)buckets[1]-1;
							} catch (Exception e) {
								logger.error("Error at part: " + elements, e); 
							}
							continue;
						} else if (Pattern.compile(CatalogCodeRegExp.SECTION.getRegExp()).matcher(elements.get(0)).find()){//Super catalog (Section)
							buckets = buildFullCatalog(elIdx, bucketDataElements, CatalogCodeRegExp.CATALOG.getRegExp());
							try {
								catalog = (Catalogue)buckets[0];
								catalog.setParent(currentPartCatalog);
								if (null==catalog.getId())
									this.catalogRepository.saveAndFlush(catalog);

								currentSectionCatalog = this.catalogRepository.findOne(catalog.getId());
								elIdx = (Integer)buckets[1]-1;
							} catch (Exception e) {
								logger.error("Error at section: " + elements, e); 
							}
							continue;
						} 
					}else if (Pattern.compile(CatalogCodeRegExp.CATALOG.getRegExp()).matcher(elements.get(0)).find() && (elements.get(0).length() >= 5 && elements.get(0).length() <= 7)){//Catalog 
						catalogOptional = this.catalogRepository.findByCode(elements.get(0));
						if (!catalogOptional.isPresent()){
							catalog = Catalogue.builder().build();
							catalog.setCode(elements.get(0));
							catalog.setName(CommonUtility.safeSubString(elements.get(1), 0, 500));
							catalog.setDescription(elements.get(3));
							if (catalog.getCode().length() == 5){
								catalog.setParent(currentSectionCatalog!=null?currentSectionCatalog:currentPartCatalog);
							} else {
								catalog.setParent(parentCatalog);
							}
							try {
								this.catalogRepository.saveAndFlush(catalog);
							} catch (Exception e) {
								logger.error("Error at catalogue: " + elements, e); 
							}

							if (elements.get(0).length() < 7){
								parentCatalog = this.catalogRepository.findOne(catalog.getId());
							}
						}
						continue;
					} else if (Pattern.compile(CatalogCodeRegExp.CATALOG.getRegExp()).matcher(elements.get(0)).find() && 10==elements.get(0).length()){//Product|Inventory Item
						product = this.productRepository.findByCode(elements.get(0));
						if (null != product)
							continue;

						product = new Product();
						product.setCode(elements.get(0));
						product.setName(CommonUtility.safeSubString(elements.get(1), 0, 500));
						product.setDescription(elements.get(3));
						//Handle measure unit
						if (CommonUtility.isNotEmpty(elements.get(2))){
							if (unitMap.containsKey(elements.get(2))){
								unit = unitMap.get(elements.get(2));
							}else{
								unitOptional = this.measureUnitRepository.findByCode(elements.get(2));
								if (!unitOptional.isPresent()){
									unit = new MeasureUnit();
									////unit.setCode(CommonUtility.toHexString(elements.get(2))); 
									unit.setCode(elements.get(2).toUpperCase(CommonUtility.LOCALE_VIETNAMESE));
									unit.setName(elements.get(2));
									try {
										this.measureUnitRepository.saveAndFlush(unit);
									} catch (Exception e) {
										logger.error("Error at measure unit: " + elements, e);
									}
								}else{
									unit = unitOptional.get();
								}
								unitMap.put(elements.get(2), unit);
							}
							product.setMeasureUnit(unit);
						}
						try {
							this.productRepository.save(product);
						} catch (Exception e) {
							logger.error("Error at product/inventory: " + elements, e); 
						}
						
						product.getProductCatalogues().add(ProductCatalog.getInstance(product, catalog));
						try {
							this.productRepository.saveAndFlush(product);
						} catch (Exception e) {
							logger.error("Error at product-catalogue: " + elements, e); 
						}
						//products.add(product);
						continue;
					} else {
						//System.out.println("Invalid data: " + elements.get(0));
					}
				}
			} catch (Exception e) {
				throw new CatalogExternalDataException(e);
			}
		}

		private Object[] buildFullCatalog(int idx, List<List<String>> bucketDataElements, String terminator) throws EcosysException{
			int elIdx = idx;
			Object[] ret = new Object[2];
			List<String> elements = bucketDataElements.get(elIdx);
			Optional<Catalogue> findCatalog = this.catalogRepository.findByCode(elements.get(0));
			Catalogue catalog = null;
			if (!findCatalog.isPresent()){
				catalog = Catalogue.builder().build();
			}
			//First line is code
			catalog.setCode(elements.get(0));

			//Second line is the name
			elements = bucketDataElements.get(++elIdx);
			catalog.setName(CommonUtility.safeSubString(elements.get(0), 0, 500));

			StringBuilder desc = new StringBuilder();
			//Making the description
			while (true) {
				elements = bucketDataElements.get(++elIdx);
				if (Pattern.compile(terminator).matcher(elements.get(0)).find()){
					break;
				}

				desc.append(elements.get(0)).append(CommonUtility.characterNewLine);
			}
			try {
				desc.deleteCharAt(desc.length()-1);
			} catch (Exception e) {
				//e.printStackTrace();
			}
			catalog.setDescription(desc.toString());
			ret[0] = catalog;
			ret[1] = Integer.valueOf(elIdx);
			return ret;
		}
}
