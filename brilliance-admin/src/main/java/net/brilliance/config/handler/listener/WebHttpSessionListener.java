/**
 * 
 */
package net.brilliance.config.handler.listener;

import java.util.Calendar;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;

import net.brilliance.common.logging.GlobalLoggerFactory;

/**
 * @author ducbq
 *
 */
@WebListener
public class WebHttpSessionListener implements HttpSessionListener {
	private Logger logger = GlobalLoggerFactory.getLogger(this.getClass());

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		if (sessionEvent.getSession().isNew()){
			logger.info("New session: [" +sessionEvent.getSession().getId() + "] created at" + Calendar.getInstance().getTime());
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		logger.info("Session: ["+ sessionEvent.getSession().getId()+ "] destroyed");
	}
}
