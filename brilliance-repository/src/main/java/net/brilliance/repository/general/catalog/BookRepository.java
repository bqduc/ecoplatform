/**
 * 
 */
package net.brilliance.repository.general.catalog;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.general.Book;
import net.brilliance.framework.repository.JBaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface BookRepository extends JBaseRepository<Book, Long> {
	Optional<Book> findByIsbn(String license);
	Optional<Book> findByName(String name);
}
