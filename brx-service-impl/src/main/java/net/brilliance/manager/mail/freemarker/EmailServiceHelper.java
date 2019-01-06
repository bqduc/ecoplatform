/**
 * 
 */
package net.brilliance.manager.mail.freemarker;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Base64Utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import net.brilliance.common.CommonUtility;
import net.brilliance.domain.entity.admin.UserAccount;
import net.brilliance.manager.mail.MailConstants;

/**
 * @author ducbq
 *
 */
@Service
public class EmailServiceHelper {
	@Autowired
	private FreeMarkerMailService mailService;

	@Autowired
	private MailConfigurationProperties emailProperties;

  @Autowired
  private Configuration freemarkerConfig;

  public void sendMail() throws Exception {
		Mail mail = new Mail();
		mail.setMailFrom("javabycode@gmail.com");
		mail.setMailTo("ducbuiquy@gmail.com");
		mail.setSubject("Spring Boot - Email with FreeMarker template");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("firstName", "Đức");
		model.put("lastName", "Bùi Quy");
		model.put("location", "Sài Gòn");
		model.put("signature", "www.javabycode.com");
		mail.setModel(model);

		mailService.sendEmail(mail);

		System.out.println("Done!");
	}

	public void sendClientRegisterMail(Locale locale, UserAccount clientUser) throws Exception {
		String confirmUrl = emailProperties.getAppUrl() + "auth/confirm/" + CommonUtility.encodeHex(clientUser.getEmail());
		Mail mail = new Mail();
		mail.setMailFrom(emailProperties.getSender());
		mail.setMailTo(clientUser.getEmail());
		mail.setSubject("Client register confirmation");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("firstName", clientUser.getFirstName() + " " + clientUser.getLastName());
		model.put("email", clientUser.getEmail());
		model.put("password", clientUser.getPassword());
		model.put("location", "Sài Gòn");
		model.put("signature", emailProperties.getSenderSignature());
		model.put("confirm", confirmUrl);
		mail.setModel(model);

		this.freemarkerConfig.setClassForTemplateLoading(this.getClass(), MailConstants.MAIL_TEMPLATE_FREE_MARKER);
    Template template = freemarkerConfig.getTemplate("emailClientRegister.ftl", locale);
    String msgContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getModel());

    mail.setMessageBody(msgContent);
    

		mailService.send(mail);

		System.out.println("Done!");
	}
}
