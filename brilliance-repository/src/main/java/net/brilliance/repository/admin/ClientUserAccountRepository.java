/**
 * 
 */
package net.brilliance.repository.admin;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.admin.ClientUserAccount;
import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.framework.repository.BaseRepository;

/**
 * @author ducbq
 *
 */
@Repository
public interface ClientUserAccountRepository extends BaseRepository<ClientUserAccount, Long> {
	Page<ClientUserAccount> findAll(Pageable pageable);
	Page<ClientUserAccount> findAllByOrderByIdAsc(Pageable pageable);
	Optional<ClientUserAccount> findByUserAccount(UserAccount userAccount);

	Long countByUserAccount(UserAccount userAccount);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.userAccount.ssoId) like LOWER(CONCAT('%',:keyword,'%'))"
			+ "or LOWER(entity.userAccount.firstName) like LOWER(CONCAT('%',:keyword,'%')) "
			+ "or LOWER(entity.userAccount.lastName) like LOWER(CONCAT('%',:keyword,'%')) "
			+ "or LOWER(entity.userAccount.email) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<ClientUserAccount> search(@Param("keyword") String keyword, Pageable pageable);
}
