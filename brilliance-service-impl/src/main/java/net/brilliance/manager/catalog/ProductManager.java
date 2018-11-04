package net.brilliance.manager.catalog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.domain.entity.stock.Product;
import net.brilliance.repository.general.ProductRepositoryOrigin;

@Service
@Transactional
public class ProductManager {
	final Logger logger = GlobalLoggerFactory.getLogger(ProductManager.class);

	@Inject
	private ProductRepositoryOrigin productRepository;

	@Transactional(readOnly = true)
	public List<Product> getAll() {
		return Lists.newArrayList(productRepository.findAll());
	}

	@Transactional(readOnly = true)
	public Product getById(Long id) {
		Optional<Product> result = productRepository.findOneById(id);
		return result.isPresent()?result.get():null;
	}

	public Product save(Product object) {
		productRepository.save(object);
		return object;
	}

	public void delete(Product object) {
		productRepository.delete(object);
	}

	/**
	 * Removes all Product entities from database.
	 */
	public void deleteAll() {
		productRepository.deleteAll();
	}

	/**
	 * Restore the original set of contacts to the database.
	 */
	public void restoreDefaultProducts() {
		ClassPathResource resource = new ClassPathResource("/config/liquibase/project.csv");

		BufferedReader br = null;
		try {

			br = new BufferedReader(new InputStreamReader(resource.getInputStream()));

			// Skip first line that only holds headers.
			br.readLine();

			String line;
			String[] words = null;
			Product object = null;
			while ((line = br.readLine()) != null) {
				words = line.split("~");

				Integer version = Integer.valueOf(words[0]);
				String name = words[1];
				String code = "";
				String producer = words[2];

				String description = words[4];

				/*object = new Product(version, code, name, producer, description);

				productRepository.save(object);*/
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Release resources.
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Product getByName(String name) {
		return productRepository.findByName(name);
	}

	public Product getByCode(String code) {
		return productRepository.findByCode(code);
	}

	public void importFromExcel(InputStream inputStream){
		
	}
}
