package net.brilliance.service.impl.invt;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.domain.entity.config.Language;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.repository.inventory.LanguageRepository;
import net.brilliance.service.api.invt.LanguageService;

@Service
public class LanguageServiceImpl extends GenericServiceImpl<Language, Long> implements LanguageService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5206794177330726569L;
	@Inject
	private LanguageRepository repository;

	@Override
	protected BaseRepository<Language, Long> getRepository() {
		return this.repository;
	}

	@Override
	protected Page<Language> performSearch(String keyword, Pageable pageable) {
		return this.repository.search(keyword, pageable);
	}

	@Override
	public Language getByCode(String code) {
		return this.repository.findByCode(code);
	}

	@Override
	public Language getByName(String name) {
		return this.repository.findByName(name);
	}
}
