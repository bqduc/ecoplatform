package net.brilliance.repository.general;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.general.Book;
import net.brilliance.framework.repository.IBaseRepository;

@Repository
public interface BookRepository extends IBaseRepository<Book, Long>{
	Optional<Book> findOneById(Long id);
	Book findByName(String name);
}
