/**
 * 
 */
package net.brilliance.converter;

import java.text.ParseException;
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.domain.entity.general.Category;
import net.brilliance.manager.catalog.CategoryManager;

/**
 * @author ducbq
 *
 */
@Component
public class CategoryFormatter implements Formatter<Category> {
	@Inject 
	private CategoryManager categoryService;

	public CategoryFormatter(CategoryManager categoryService){
		this.categoryService = categoryService;
	}

	@Override
	public Category parse(String id, Locale locale) throws ParseException {
		Category result = categoryService.getById(Long.valueOf(id));
		return CommonUtility.isNotEmpty(result)?result:new Category();
	}

	@Override
	public String print(Category department, Locale locale) {
		return (department != null) ? String.valueOf(department.getId()):CommonConstants.STRING_BLANK; 
	}
}
