/**
 * 
 */
package net.brilliance.runnable;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.brilliance.common.CommonUtility;
import net.brilliance.framework.runnable.RunnableBase;
import net.brilliance.manager.system.SystemSequenceManager;

/**
 * @author ducbq
 *
 */
@Component
@Scope("prototype")
public class UpdateSystemSequenceThread extends RunnableBase {

	@Inject 
	private SystemSequenceManager systemSequenceManager;

	private String sequentialNumber;

	public UpdateSystemSequenceThread(String sequentialNumber){
		this.sequentialNumber = sequentialNumber;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		cLog.info("Start to run system sequence updating thread....");
		//System.out.println("SystemSequenceService: [" + systemSequenceService + "]");
		syncPersistenceServce();
		cLog.info("System sequence updating thread is done");
	}

	private void syncPersistenceServce(){
		if (CommonUtility.isEmpty(this.sequentialNumber)){
			cLog.info("There is an empty system sequence object");
			return;
		}

		systemSequenceManager.registerSequence(this.sequentialNumber);
	}
}
