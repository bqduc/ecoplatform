package net.brilliance.manager.mail.freemarker;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import net.brilliance.manager.mail.MailConstants;

@Component
@Configuration
public class FreeMarkerEmailConfiguration {

	@Autowired
	private MailConfigurationProperties emailProperties;

	@Bean
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(emailProperties.getHost());
		mailSender.setPort(Integer.parseInt(emailProperties.getPort()));
		mailSender.setDefaultEncoding(MailConstants.MAIL_TEMPLATE_ENCODING);

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", emailProperties.getSmtpStartTlsRequired());
		javaMailProperties.put("mail.smtp.auth", emailProperties.getSmtpAuth());
		javaMailProperties.put("mail.transport.protocol", emailProperties.getTransportProtocol());
		javaMailProperties.put("mail.debug", emailProperties.getDebugEnabled());
		if ("true".equalsIgnoreCase(emailProperties.getSmtpAuth())) {
			mailSender.setUsername(emailProperties.getUsername());
			mailSender.setPassword(emailProperties.getPassword());
		}
		mailSender.setJavaMailProperties(javaMailProperties);
		
		return mailSender;
	}
}
