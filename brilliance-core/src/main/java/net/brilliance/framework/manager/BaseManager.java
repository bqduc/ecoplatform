package net.brilliance.framework.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.CommonConstants;
import net.brilliance.framework.entity.BaseObject;
import net.brilliance.framework.logging.CommonLoggingService;
import net.brilliance.framework.repository.BaseRepository;

public abstract class BaseManager<T extends BaseObject, PK extends Serializable> extends RootService <T, PK>{
	private static final long serialVersionUID = -1326030262778654331L;

	protected abstract BaseRepository<T, PK> getRepository();

	@Inject 
	protected CommonLoggingService cLog;
	
	protected Page<T> performSearch(String keyword, Pageable pageable){
		return DUMMY_PAGEABLE;
	}

	protected Page<T> getPaginatedObjects(Integer page, Integer size){
    PageRequest pageRequest = new PageRequest(page-1, size, Sort.Direction.ASC, "id");
    return getRepository().findAll(pageRequest);
	}

	public Page<T> getList(Integer pageNumber) {
		return getPaginatedObjects(pageNumber, CommonConstants.DEFAULT_PAGE_SIZE);
	}

	public Page<T> getList(Integer pageNumber, Integer size) {
		return getPaginatedObjects(pageNumber, size);
	}

	public T save(T entity) {
		return getRepository().save(entity);
	}

	public T create(T entity) {
		return getRepository().save(entity);
	}

	public T get(PK id) {
		T entity = getRepository().findOne(id);
		return entity;
	}

	public T getById(PK id) {
		return get(id);
	}

	public void delete(PK id) {
		try {
			getRepository().delete(id);
		} catch (EmptyResultDataAccessException e) {
			cLog.error("Delete object by key", e);
		}
	}

	public void delete(T entity) {
		try {
			getRepository().delete(entity);
		} catch (EmptyResultDataAccessException e) {
			cLog.error("Delete object. ", e);
		}
	}

	public void deleteAll() {
		try {
			getRepository().deleteAll();
		} catch (EmptyResultDataAccessException e) {
			cLog.error("Delete all objects. ", e);
		}
	}

	@SuppressWarnings("unchecked")
	public T update(T entity) {
		T getEntity = getRepository().findOne((PK) entity.getId());
		getRepository().save(entity);
		cLog.info("Merged entity: " + getEntity.getId());
		return entity;
	}

	public long count() {
		return getRepository().count();
	}

	@Transactional(readOnly = true)
	public List<T> getAll() {
		List<T> results = new ArrayList<>();
		getRepository().findAll().forEach(results::add);
		return results;
	}
	
	@Transactional(readOnly = true)
	public Page<T> search(String keyword, Pageable pageable) {
		return performSearch(keyword, pageable);
	}
}