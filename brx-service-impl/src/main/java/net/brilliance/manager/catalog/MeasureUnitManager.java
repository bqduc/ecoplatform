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

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.domain.entity.general.MeasureUnit;
import net.brilliance.framework.manager.AbstractServiceManager;
import net.brilliance.framework.repository.JBaseRepository;
import net.brilliance.repository.general.catalog.MeasureUnitRepository;

/**
 * MeasureUnit service implementation. Provides implementation of the measure unit
 * 
 * @author ducbq
 *
 */
@Service
@Transactional
public class MeasureUnitManager extends AbstractServiceManager<MeasureUnit, Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4716250635938610907L;

	final Logger logger = GlobalLoggerFactory.getLogger(this.getClass());

	@Inject
	private MeasureUnitRepository repository;

	@Override
	protected JBaseRepository<MeasureUnit, Long> getRepository() {
		return this.repository;
	}

	/*@Override
	protected IBaseRepository<MeasureUnit, Long> getSearchableRepository() {
		return this.repository;
	}*/
	
	public Optional<MeasureUnit> getByName(String name) {
		return repository.findByName(name);
	}

	public Optional<MeasureUnit> getByCode(String license) {
		return repository.findByCode(license);
	}

	@Override
	protected Page<MeasureUnit> performSearchObjects(String keyword, Pageable pageable) {
		List<MeasureUnit>measureUnits = repository.search(keyword);
		return new PageImpl<>(measureUnits);
	}

	public void setupMasterData(){
		repository.save(MeasureUnit.createInstance("Qty", "Quantity","Số lượng"));
		repository.save(MeasureUnit.createInstance("gr", "Gram",""));
		repository.save(MeasureUnit.createInstance("mt", "Metre",""));
		repository.save(MeasureUnit.createInstance("lt", "Litre",""));
		repository.save(MeasureUnit.createInstance("hour", "Hour","Giờ"));
		repository.save(MeasureUnit.createInstance("day", "Day","Ngày"));
		repository.save(MeasureUnit.createInstance("Session", "Phien","Phiên giao dịch"));
		repository.save(MeasureUnit.createInstance("Package", "TronGoi","Trọn gói"));
		repository.save(MeasureUnit.createInstance("Ticket", "Phieu","Một vé/phiếu để thực hiện"));
		repository.save(MeasureUnit.createInstance("Unknown", "Chưa xác định","Chưa xác định"));
		repository.save(MeasureUnit.createInstance("Adet", "Adet","Adet"));
		repository.save(MeasureUnit.createInstance("Ton", "Tấn","Tấn"));
		repository.save(MeasureUnit.createInstance("M3", "M3","M3"));
		repository.save(MeasureUnit.createInstance("Kg", "Kg","Kg"));
		repository.save(MeasureUnit.createInstance("Cara", "Cara","Cara"));
		repository.save(MeasureUnit.createInstance("Triệu Đồng", "Triệu Đồng","Triệu Đồng"));
		repository.save(MeasureUnit.createInstance("1000 Lít", "1000 Lít","1000 Lít"));
		repository.save(MeasureUnit.createInstance("1000 Bao", "1000 Bao","1000 Bao"));
		repository.save(MeasureUnit.createInstance("1000 Điếu", "1000 Điếu","1000 Điếu"));
		repository.save(MeasureUnit.createInstance("1000 M2", "1000 M2","1000 M2"));
		repository.save(MeasureUnit.createInstance("1000 Cái", "1000 Cái","1000 Cái"));
		repository.save(MeasureUnit.createInstance("M2", "M2","M2"));
		repository.save(MeasureUnit.createInstance("1000 Đôi", "1000 Đôi","1000 Đôi"));
		repository.save(MeasureUnit.createInstance("M", "M","M"));
		repository.save(MeasureUnit.createInstance("1000 Gói", "1000 Gói","1000 Gói"));
		repository.save(MeasureUnit.createInstance("1000 Chiếc", "1000 Chiếc","1000 Chiếc"));
		repository.save(MeasureUnit.createInstance("Triệu Trang", "Triệu Trang","Triệu Trang"));
		repository.save(MeasureUnit.createInstance("1000 Tấn", "1000 Tấn","1000 Tấn"));
		repository.save(MeasureUnit.createInstance("Lít", "Lít","Lít"));
		repository.save(MeasureUnit.createInstance("Triệu Viên", "Triệu Viên","Triệu Viên"));
		repository.save(MeasureUnit.createInstance("1000 Liều", "1000 Liều","1000 Liều"));
		repository.save(MeasureUnit.createInstance("Bộ", "Bộ","Bộ"));
		repository.save(MeasureUnit.createInstance("1000 M", "1000 M","1000 M"));
		repository.save(MeasureUnit.createInstance("Cái", "Cái","Cái"));
		repository.save(MeasureUnit.createInstance("1000 Viên", "1000 Viên","1000 Viên"));
		repository.save(MeasureUnit.createInstance("Chiếc", "Chiếc","Chiếc"));
		repository.save(MeasureUnit.createInstance("1000 Quả", "1000 Quả","1000 Quả"));
		repository.save(MeasureUnit.createInstance("1000 Viên Qc", "1000 Viên Qc","1000 Viên Qc"));
		repository.save(MeasureUnit.createInstance("1000 Kwh", "1000 Kwh","1000 Kwh"));
		repository.save(MeasureUnit.createInstance("1000 ổ", "1000 ổ","1000 ổ"));
		repository.save(MeasureUnit.createInstance("Đôi", "Đôi","Đôi"));
		repository.save(MeasureUnit.createInstance("Quả", "Quả","Quả"));
		repository.save(MeasureUnit.createInstance("1000 Con", "1000 Con","1000 Con"));
		repository.save(MeasureUnit.createInstance("1000 Cây", "1000 Cây","1000 Cây"));
		repository.save(MeasureUnit.createInstance("1000 Thẻ", "1000 Thẻ","1000 Thẻ"));
		repository.save(MeasureUnit.createInstance("1000 Vòng", "1000 Vòng","1000 Vòng"));
		repository.save(MeasureUnit.createInstance("Triệu Kwh", "Triệu Kwh","Triệu Kwh"));
		repository.save(MeasureUnit.createInstance("1000 M3", "1000 M3","1000 M3"));
	}
}
