/**
 * 
 */
package net.brilliance.repository.specification.quartz;

import lombok.Builder;
import net.brilliance.domain.entity.schedule.JobDefinition;
import net.brilliance.framework.specifications.predicator.BrilliancePredicator;

/**
 * @author ducbq
 *
 */
@Builder
public class JobDefinitionPredicator extends BrilliancePredicator<JobDefinition>/*BrillianceSpecifications<JobDefinition, DefaultSearchRequest>*/{
}
