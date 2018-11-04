package net.brilliance.repository.contact;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.general.Activity;
import net.brilliance.framework.repository.BaseRepository;

@Repository
public interface ActivityRepository extends BaseRepository<Activity, Long>{
	Optional<Activity> findOneById(Long id);

	Activity findByName(String name);
}
