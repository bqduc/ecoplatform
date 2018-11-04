package net.brilliance.manager.catalog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.domain.entity.general.Book;
import net.brilliance.repository.general.BookRepository;

@Service
@Transactional
public class BookManager {
	final Logger logger = GlobalLoggerFactory.getLogger(BookManager.class);

	@Inject
	private BookRepository bookRepository;

	@Transactional(readOnly = true)
	public List<Book> findAll() {
		List<Book> results = new ArrayList<>();
		results.addAll(bookRepository.findAll());
		return results;
	}

	@Transactional(readOnly = true)
	public Book findById(Long id) {
		Optional<Book> result = bookRepository.findOneById(id);
		return result.isPresent()?result.get():null;
	}

	public Book save(Book book) {
		bookRepository.save(book);
		return book;
	}

	public void delete(Book book) {
		bookRepository.delete(book);
	}

	/**
	 * Removes all Book entities from database.
	 */
	public void deleteAll() {
		bookRepository.deleteAll();
	}

	/**
	 * Restore the original set of books to the database.
	 */
	public void restoreDefaultBooks() {
		ClassPathResource resource = new ClassPathResource("/config/liquibase/books.csv");

		BufferedReader br = null;

		try {

			br = new BufferedReader(new InputStreamReader(
					resource.getInputStream()));

			// Skip first line that only holds headers.
			br.readLine();

			String line;

			while ((line = br.readLine()) != null) {
				String[] words = line.split("~");

				Integer version = Integer.valueOf(words[0]);
				String name = words[1];
				String publisher = words[2];

				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date dateOfPublication = null;
				try {
					dateOfPublication = sdf.parse(words[3]);
				} catch (ParseException e) {
					logger.error(e.getMessage(), e);
				}

				String description = words[4];
				String photo = words[5];

				Book b = new Book(version, name, publisher, dateOfPublication,
						description, photo);

				bookRepository.save(b);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Release resources.
				br.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}

	}

	public Book getByName(String name) {
		return bookRepository.findByName(name);
	}
}
