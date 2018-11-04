package net.brilliance.framework.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BrillianceRepository<T, PK extends Serializable> extends BaseRepository<T, PK>, JpaSpecificationExecutor<T> {
	/*Optional<T> findByCode(String code);
	Long countByCode(String code);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.code) like LOWER(CONCAT('%',:keyword,'%'))"
			//+ "or LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<T> search(@Param("keyword") String keyword, Pageable pageable);*/
}
