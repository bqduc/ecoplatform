/**
 * 
 */
package net.brilliance.repository.general;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.general.Attachment;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface AttachmentRepository extends BaseRepository<Attachment, Long> {
}
