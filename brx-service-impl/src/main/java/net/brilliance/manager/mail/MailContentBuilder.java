/**
 * 
 */
package net.brilliance.manager.mail;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author ducbq
 *
 */
@Service
public class MailContentBuilder {
	private TemplateEngine emailTemplateEngine;

	@Autowired
	public MailContentBuilder(TemplateEngine templateEngine) {
		this.emailTemplateEngine = templateEngine;
  }

	public String build(String message) {
		Context context = new Context();
		context.setVariable("message", message);
		return emailTemplateEngine.process("mailTemplate", context);
	}

	public String build(Locale locale, String template, Map<String, Object> contextParameters) {
		//TemplateEngine templateEngine = MailConfig.getEmailTemplateEngine();
		Context context = new Context(locale);
		emailTemplateEngine.process(template, context);

		context.setVariables(contextParameters);
		return emailTemplateEngine.process(template, context);
	}
}
