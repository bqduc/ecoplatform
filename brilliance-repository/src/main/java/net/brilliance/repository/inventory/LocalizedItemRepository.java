package net.brilliance.repository.inventory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.config.Item;
import net.brilliance.domain.entity.config.Language;
import net.brilliance.domain.entity.config.LocalizedItem;
import net.brilliance.framework.repository.BaseRepository;

@Repository
public interface LocalizedItemRepository extends BaseRepository<LocalizedItem, Long>{

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " entity.item = :item and entity.language = :language"
			+ ")"
	)
	public LocalizedItem findByLocalizedItem(Language language, Item item);
}
