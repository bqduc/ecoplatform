/**
 * 
 */
package net.brilliance.repository.dmx;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.audit.AuditObject;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface AuditObjectRepository extends BaseRepository<AuditObject, Long> {
}
