package net.brilliance.service.api.invt;

import net.brilliance.domain.entity.config.Language;
import net.brilliance.framework.service.GenericService;

public interface LanguageService extends GenericService<Language, Long>{
	Language getByCode(String code);
	Language getByName(String name);
}
