package net.brilliance.repository.inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.config.Language;
import net.brilliance.framework.repository.BaseRepository;

@Repository("languageRepository")
public interface LanguageRepository extends BaseRepository<Language, Long>{
	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.code) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	public Page<Language> search(@Param("keyword") String keyword, Pageable pageable);

	Language findByCode(String code);
	Language findByName(String name);
}
