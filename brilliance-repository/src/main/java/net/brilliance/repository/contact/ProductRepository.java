package net.brilliance.repository.contact;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.stock.Product;
import net.brilliance.framework.repository.BaseRepository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	Optional<Product> findOneById(Long id);

	Product findByCode(String code);
	Long countByCode(String code);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.code) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.longName) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.translatedName) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<Product> search(@Param("keyword") String keyword, Pageable pageable);
}
