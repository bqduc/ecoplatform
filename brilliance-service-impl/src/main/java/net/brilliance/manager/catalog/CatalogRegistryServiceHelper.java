package net.brilliance.manager.catalog;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import net.brilliance.common.CommonUtility;
import net.brilliance.common.ListUtility;
import net.brilliance.domain.entity.general.Catalogue;
import net.brilliance.domain.entity.general.MeasureUnit;
import net.brilliance.domain.entity.stock.Product;
import net.brilliance.domain.entity.stock.ProductCatalog;
import net.brilliance.exceptions.EcosysException;
import net.brilliance.framework.component.ComponentBase;
import net.brilliance.model.Bucket;
import net.brilliance.model.BuildCatalogBucket;
import net.brilliance.repository.general.ProductRepositoryOrigin;
import net.brilliance.repository.general.catalog.CatalogRepository;
import net.brilliance.repository.general.catalog.MeasureUnitRepository;
import net.brilliance.service.helper.GlobalDataServiceHelper;

@Component
public class CatalogRegistryServiceHelper extends ComponentBase {
	private static final long serialVersionUID = -4842644480886496784L;

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

	@Inject
	private MeasureUnitRepository measureUnitRepository;

	@Inject
	private ProductRepositoryOrigin productRepository;

	@Inject
	private CatalogRepository catalogRepository;

	@Inject
	private GlobalDataServiceHelper globalDataServiceHelper;

	public void registerCataloguesFromExcel(Map<Object, Object> params) throws EcosysException {
		InputStream inputStream = null;
		Bucket bucket = null;
		try {
			inputStream = (InputStream)params.get(Bucket.PARAM_INPUT_STREAM);
			bucket = globalDataServiceHelper.readSpreadsheetData(inputStream, params);
			parseCatalogues((List<List<String>>)bucket.get(params.get(Bucket.PARAM_WORK_DATA_SHEET)));
		} catch (Exception e) {
			throw new EcosysException(e);
		}
	}

	private void parseCatalogues(List<List<String>> bucketDataElements){
		Catalogue currentPartCatalog = null;
		Catalogue currentSectionCatalog = null;

		Optional<Catalogue> catalogOptional = null;
		Catalogue catalog = null;
		Catalogue parentCatalog = null;
		Product product = null;
		MeasureUnit unit = null;
		Optional<MeasureUnit> unitOptional;
		Map<String, MeasureUnit> unitMap = ListUtility.createMap();
		//Object[] buckets = null;
		BuildCatalogBucket buckets = null;
		List<Product> products = ListUtility.createArrayList();		
		List<String> elements = null;
		int startedIndex = 0;//6345;
		try {
			int bucketSize = bucketDataElements.size();
			for (int elIdx = startedIndex; elIdx < bucketSize; elIdx++){
				elements = bucketDataElements.get(elIdx);
				if (CommonUtility.isNotEmpty(elements.get(0)) && CommonUtility.isEmpty(elements.get(1)) && CommonUtility.isEmpty(elements.get(2))){
					if (Pattern.compile(CatalogCodeRegExp.PART.getRegExp()).matcher(elements.get(0)).find()){//Super catalog (Part)
						buckets = buildFullCatalog(elIdx, bucketDataElements, CatalogCodeRegExp.SECTION.getRegExp());
						if (!buckets.hasNext()){
							elIdx = Integer.valueOf(buckets.nextIdx().intValue()-1);
							currentPartCatalog = this.catalogRepository.findOne(((Catalogue)buckets.next()).getId());
							currentSectionCatalog = null;
							parentCatalog = null;
							catalog = null;
							continue;
						}

						try {
							catalog = (Catalogue)buckets.next();
							if (null==catalog.getId())
								this.catalogRepository.saveAndFlush(catalog);

							currentPartCatalog = this.catalogRepository.findOne(catalog.getId());
							elIdx = Integer.valueOf(buckets.nextIdx().intValue()-1);
						} catch (Exception e) {
							e.printStackTrace();
						}
						continue;
					} else if (Pattern.compile(CatalogCodeRegExp.SECTION.getRegExp()).matcher(elements.get(0)).find()){//Super catalog (Section)
						buckets = buildFullCatalog(elIdx, bucketDataElements, CatalogCodeRegExp.CATALOG.getRegExp());
						if (!buckets.hasNext()){
							elIdx = Integer.valueOf(buckets.nextIdx().intValue()-1);
							currentSectionCatalog = this.catalogRepository.findOne(((Catalogue)buckets.next()).getId());
							parentCatalog = null;
							catalog = null;
							continue;
						}

						try {
							catalog = (Catalogue)buckets.next();
							catalog.setParent(currentPartCatalog);
							if (null==catalog.getId())
								this.catalogRepository.saveAndFlush(catalog);

							currentSectionCatalog = this.catalogRepository.findOne(catalog.getId());
							elIdx = Integer.valueOf(buckets.nextIdx().intValue()-1);
						} catch (Exception e) {
							e.printStackTrace();
						}
						continue;
					} 
				}else if (Pattern.compile(CatalogCodeRegExp.CATALOG.getRegExp()).matcher(elements.get(0)).find() && (elements.get(0).length() >= 5 && elements.get(0).length() <= 7)){//Catalog 
					catalogOptional = this.catalogRepository.findByCode(elements.get(0));
					if (!catalogOptional.isPresent()){
						catalog = Catalogue.builder()
						.code(elements.get(0))
						.name(CommonUtility.safeSubString(elements.get(1), 0, 500))
						.description(elements.get(3))
						.build();
						if (catalog.getCode().length() == 5){
							catalog.setParent(currentSectionCatalog!=null?currentSectionCatalog:currentPartCatalog);
						} else {
							catalog.setParent(parentCatalog);
						}
						try {
							this.catalogRepository.saveAndFlush(catalog);
						} catch (Exception e) {
							e.printStackTrace();
						}

						if (elements.get(0).length() < 7){
							parentCatalog = this.catalogRepository.findOne(catalog.getId());
						}
					}else{
						catalog = catalogOptional.get();
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
							unitOptional = this.measureUnitRepository.findByCode(elements.get(2).toUpperCase(CommonUtility.LOCALE_VIETNAMESE));
							if (!unitOptional.isPresent()){
								unit = new MeasureUnit();
								//unit.setCode(CommonUtility.toHexString(elements.get(2))); 
								unit.setCode(elements.get(2).toUpperCase(CommonUtility.LOCALE_VIETNAMESE));
								unit.setName(elements.get(2));
								try {
									this.measureUnitRepository.saveAndFlush(unit);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}else{
								unit = unitOptional.get();
							}
							unitMap.put(elements.get(2), unit);
						}
						product.setMeasureUnit(unit);
					}
					try {
						productRepository.save(product);
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (null==catalog){
						product.getProductCatalogues().add(ProductCatalog.getInstance(product, currentSectionCatalog!=null?currentSectionCatalog:currentPartCatalog));
					}else{
						product.getProductCatalogues().add(ProductCatalog.getInstance(product, catalog));
					}
					try {
						productRepository.saveAndFlush(product);
					} catch (Exception e) {
						e.printStackTrace();
					}
					//products.add(product);
					continue;
				} else {
					//System.out.println("Invalid data: " + elements.get(0));
				}
			}
			System.out.println(products.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private BuildCatalogBucket buildFullCatalog(int idx, List<List<String>> bucketDataElements, String terminator) throws EcosysException{
		String code, name;
		int elIdx = idx;
		List<String> elements = bucketDataElements.get(elIdx);
		Catalogue catalog = null;

		//First line is code
		code = elements.get(0);

		//Second line is the name
		elements = bucketDataElements.get(++elIdx);
		name = CommonUtility.safeSubString(elements.get(0), 0, 500);

		StringBuilder desc = new StringBuilder();
		//Making the description
		while (true) {
			elements = bucketDataElements.get(++elIdx);
			if (Pattern.compile(terminator).matcher(elements.get(0)).find()){
				break;
			}

			desc.append(elements.get(0)).append(CommonUtility.characterNewLine);
		}

		Optional<Catalogue> catalogOptional = this.catalogRepository.findByCode(code);
		if (catalogOptional.isPresent()){
			return BuildCatalogBucket
					.getInstance()
					.setNextObject(catalogOptional.get())
					.setNextIdx(Integer.valueOf(elIdx));
		}

		try {
			desc.deleteCharAt(desc.length()-1);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		catalog = Catalogue.builder()
		.code(code)
		.name(name)
		.description(desc.toString())
		.build();
		return BuildCatalogBucket.getInstance(elIdx, catalog);
	}
}
