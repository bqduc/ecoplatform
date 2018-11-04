/**
 * 
 */
package net.brilliance.manager.mail;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import net.brilliance.common.DateTimeUtility;
import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.exceptions.EcosysException;

/**
 * @author ducbq
 *
 */
@Service
public class MailClientService /*extends RootService */{
	protected Logger logger = GlobalLoggerFactory.getLogger(this.getClass());

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendMail(String subject, String messageBody, String sender, String[] toAddresses, String[] ccAddresses, String[] bccAddresses) throws EcosysException {
		logger.info("About to send email at: " + DateTimeUtility.getSystemDateTime());
		try {
      MimeMessage message = javaMailSender.createMimeMessage();

      MimeMessageHelper messageHelper = new MimeMessageHelper(message);
      messageHelper.setTo(toAddresses);
      messageHelper.setText(messageBody, true);
      messageHelper.setSubject(subject);
      javaMailSender.send(message);
			logger.info("Email sent at: " + DateTimeUtility.getSystemDateTime());
		} catch (Exception e) {
			throw new EcosysException(e);
		}
	}
}