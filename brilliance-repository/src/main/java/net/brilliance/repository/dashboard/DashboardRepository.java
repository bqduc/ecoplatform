/**
 * 
 */
package net.brilliance.repository.dashboard;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.system.DigitalDashboard;

/**
 * @author ducbq
 *
 */
@Repository
public interface DashboardRepository extends DashboardBaseRepository<DigitalDashboard, Long> {
}
