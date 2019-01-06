package net.brilliance.service.api.invt;

import java.util.List;

import net.brilliance.domain.entity.config.Item;
import net.brilliance.domain.entity.config.Language;
import net.brilliance.domain.entity.config.LocalizedItem;
import net.brilliance.framework.service.GenericService;
import net.brilliance.exceptions.ObjectNotFoundException;

public interface ItemService extends GenericService<Item, Long>{

  /**
   * Get one item with the provided code.
   * 
   * @param code The item code
   * @return The item
   * @throws ObjectNotFoundException If no such account exists.
   */
	Item getOne(String code) throws ObjectNotFoundException;

	LocalizedItem getLocalizedItem(Item item, Language language);

	LocalizedItem saveLocalizedItem(LocalizedItem localizedItem);
	
	List<LocalizedItem> getLocalizedItems(String subtype, Language language);
}
