package net.brilliance.service.impl.invt;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.domain.entity.config.Item;
import net.brilliance.domain.entity.config.Language;
import net.brilliance.domain.entity.config.LocalizedItem;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.repository.inventory.ItemRepository;
import net.brilliance.repository.inventory.LocalizedItemRepository;
import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.service.api.invt.ItemService;

@Service
public class ItemServiceImpl extends GenericServiceImpl<Item, Long> implements ItemService{
	private static final long serialVersionUID = -3874412554298276460L;

	@Inject
	private ItemRepository repository;

	@Inject
	private LocalizedItemRepository localizedRepository;

	@Override
	protected BaseRepository<Item, Long> getRepository() {
		return this.repository;
	}

	@Override
	protected Page<Item> performSearch(String keyword, Pageable pageable) {
		return this.repository.search(keyword, pageable);
	}

	@Override
	public Item getOne(String code) throws ObjectNotFoundException {
		return this.repository.findByCode(code);
	}

	@Override
	public LocalizedItem getLocalizedItem(Item item, Language language) {
		EntityManager em = this.getEntityManager();
		List results = em.createQuery("select li from LocalizedItem li where li.item = :item and li.language = :language")
		.setParameter("item", item)
		.setParameter("language", language)
		.getResultList();
		return (results.size() > 0)?(LocalizedItem)results.get(0):null;
	}

	@Override
	public LocalizedItem saveLocalizedItem(LocalizedItem localizedItem) {
		return this.localizedRepository.saveAndFlush(localizedItem);
	}

	@Override
	public List<LocalizedItem> getLocalizedItems(String subtype, Language language) {
		EntityManager em = this.getEntityManager();
		return em.createQuery("select li from LocalizedItem li where li.item.subtype = :itemSubtype and li.language = :language")
		.setParameter("itemSubtype", subtype)
		.setParameter("language", language)
		.getResultList();
	}
}
