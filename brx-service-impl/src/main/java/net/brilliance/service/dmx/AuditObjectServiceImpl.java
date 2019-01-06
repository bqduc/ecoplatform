package net.brilliance.service.dmx;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import net.brilliance.domain.entity.audit.AuditObject;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.repository.dmx.AuditObjectRepository;
import net.brilliance.service.api.dmx.AuditObjectService;

@Async
@Service
public class AuditObjectServiceImpl extends GenericServiceImpl<AuditObject, Long> implements AuditObjectService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7992683322367417338L;

	@Inject 
	private AuditObjectRepository repository;

	protected BaseRepository<AuditObject, Long> getRepository() {
		return this.repository;
	}
}
