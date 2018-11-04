package net.brilliance.controller.controller.catalog;

import java.net.URI;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.domain.entity.general.Book;
import net.brilliance.manager.catalog.BookManager;
import net.brilliance.util.ImageUtil;

@RequestMapping(CommonConstants.REST_API + "product")
@RestController
public class ProductRestControllerOrigin {

	final Logger logger = GlobalLoggerFactory.getLogger(ProductRestControllerOrigin.class);

	@Inject
	private BookManager bookService;

	@RequestMapping(value = "/get/{name}", method = RequestMethod.GET)
	public @ResponseBody Book get(HttpServletRequest request, @PathVariable("name") String name) {
		Book fetchedObject = null;
		try {
			fetchedObject = this.bookService.getByName(name);
			if (null == fetchedObject) {
				fetchedObject = new Book();
				fetchedObject.setName("Sách này là Khộng Tưởng. Chưa có trong hệ thống. Kết quả trả vể từ RESTFul. ");
				fetchedObject.setId(new Long(2123));
			}
			System.out.println("Found book: [" + fetchedObject + "]");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return fetchedObject;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> add(HttpServletRequest request, @RequestBody Book book) {
		ResponseEntity<?> responseEntity = null;
		try {
			book.setPhoto(ImageUtil.smallNoImage());
			this.bookService.save(book);
			URI projectUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();

			responseEntity = ResponseEntity.created(projectUri).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseEntity = ResponseEntity.noContent().build();
		}
		return responseEntity;
	}
}
