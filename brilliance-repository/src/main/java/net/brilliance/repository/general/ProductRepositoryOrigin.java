package net.brilliance.repository.general;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.stock.Product;
import net.brilliance.framework.repository.AdvancedSearchRepository;

@Repository
public interface ProductRepositoryOrigin extends AdvancedSearchRepository<Product, Long> {
	Optional<Product> findOneById(Long id);

	Product findByName(String name);

	Product findByCode(String code);
}
