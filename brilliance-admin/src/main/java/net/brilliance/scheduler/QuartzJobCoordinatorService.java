/**
 * 
 */
package net.brilliance.scheduler;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.brilliance.framework.logging.CommonLoggingService;

/**
 * @author ducbq
 *
 */
@Service
public class QuartzJobCoordinatorService {
	@Inject 
	private CommonLoggingService cLog;
	
	/*@Autowired
	private CategoryService categoryService;

	@Inject 
	private ClientProfileService clientProfileManager;
	*/
	public void processData() {
		//cLog.info("Process data in quartz job coordinator !");
	}

	public void processFoodSynchronized() {
		//cLog.info("Synchronized food!");
	}

	public void processDayEnd() {
		//cLog.info("Day end data reconcile!");
	}

	public void processMonthEnd() {
	//	cLog.info("Month end data reconcile!");
	}

	public void processGeneralLedgerData() {
		//cLog.info("General ledger data reconcile!");
	}

	public void processClientExpired() {
		//cLog.info("Client expiration process! ");
	}

	public void processFeedAlert() {
		//cLog.info("Feed alert fired! ");
	}

	public void processYearEnd() {
		//cLog.info("Year end job fired! ");
	}
}