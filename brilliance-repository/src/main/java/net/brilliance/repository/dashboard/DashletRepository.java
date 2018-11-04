/**
 * 
 */
package net.brilliance.repository.dashboard;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.system.DigitalDashlet;

/**
 * @author ducbq
 *
 */
@Repository
public interface DashletRepository extends DashboardBaseRepository<DigitalDashlet, Long> {
}
