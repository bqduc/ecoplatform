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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.DateTimeUtility;
import net.brilliance.domain.entity.general.Catalogue;
import net.brilliance.framework.manager.AbstractServiceManager;
import net.brilliance.framework.repository.JBaseRepository;
import net.brilliance.repository.general.catalog.CatalogRepository;

/**
 * Catalog service implementation. Provides implementation of the catalog
 * 
 * @author ducbq
 *
 */
@Service
@Transactional
public class CatalogManager extends AbstractServiceManager<Catalogue, Long> {
	private static final long serialVersionUID = 5462391494726676551L;

	@Inject
	private CatalogRepository repository;

	@Override
	protected JBaseRepository<Catalogue, Long> getRepository() {
		return this.repository;
	}

	public List<Catalogue> createDummyObjects() {
		String[] names = new String[]{"AIS", "GPS", "INMASAT", "LA BÀN CON QUAY", "MF/HF", "MÁY LÁI", "MÁY ĐO SÂU", "MÁY ĐO TỐC ĐỘ", "NAVITEX", "RADAR", "VHF", "bón lá", 
				"Chất cải tạo đất", 
				"hữu cơ", 
				"Hữu cơ khoáng", 
				"Hữu cơ sinh học", 
				"hữu cơ vi sinh", 
				"Khoáng hữu cơ", 
				"Phân bón  hữu cơ khoáng", 
				"Phân bón  hữu cơ vi sinh", 
				"Phân bón cải tạo đất", 
				"Phân bón có bổ sung chất tăng hiệu suất sử dụng", 
				"Phân bón hữu cơ", 
				"Phân bón hữu cơ (HCTT)", 
				"Phân bón hữu cơ khoáng", 
				"Phân bón hữu cơ sinh học", 
				"Phân bón hữu cơ sinh học (phân bón lá)", 
				"Phân bón hữu cơ vi sinh", 
				"Phân bón khoáng hữu cơ", 
				"Phân bón lá ", 
				"Phân bón lá (phân sinh học )", 
				"Phân bón lá HCSH", 
				"Phân bón lá sinh học ", 
				"Phân bón sinh học", 
				"Phân bón sinh học (phân bón lá)", 
				"Phân bón vi sinh", 
				"Phân bón vi sinh vật", 
				"Phân bón hữu cơ khoáng", 
				"Phân HCSH", 
				"Phân hữu cơ ", 
				"Phân hữu cơ khoáng", 
				"Phân hữu cơ sinh học ", 
				"Phân hữu cơ vi sinh ", 
				"Phân Hữu cơ vi sinh vật ", 
				"Phân Hữu khoáng ", 
				"Phân Hữu khoáng HUMIX", 
				"Phân khoáng hữu cơ", 
				"Phân sinh học ", 
				"Phân sinh học (phân bón lá)", 
				"Phân vi sinh", 
				"Phân vi sinh vật"};
		Catalogue fetchObject = null;
		List<Catalogue> fetchedObjects = new ArrayList<>();
		for (int i = 0; i < names.length; ++i){
			fetchObject = Catalogue.builder()
					.code(String.valueOf(DateTimeUtility.generateTimeStamp()))
					.name(names[i])
					.build();
			this.save(fetchObject);
			fetchedObjects.add(fetchObject);
		}
		return fetchedObjects;
	}

	public Optional<Catalogue> getByName(String name) {
		return repository.findByName(name);
	}

	public Optional<Catalogue> getByCode(String code) {
		return repository.findByCode(code);
	}

}
