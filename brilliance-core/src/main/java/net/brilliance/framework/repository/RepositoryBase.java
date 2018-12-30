/**
 * 
 */
package net.brilliance.framework.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author ducbq
 *
 */
@NoRepositoryBean
public interface RepositoryBase<EntityClassType, KeyClassType extends Serializable> extends BaseRepository<EntityClassType, KeyClassType>, JpaSpecificationExecutor<EntityClassType> {
}
//ContactTeamRepository extends BaseRepository<ContactTeam, Long>, JpaSpecificationExecutor<ContactTeam>