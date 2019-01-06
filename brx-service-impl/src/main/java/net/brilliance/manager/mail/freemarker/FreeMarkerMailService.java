package net.brilliance.manager.mail.freemarker;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class FreeMarkerMailService {
	
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration freemarkerConfig;

    
    public void sendEmail(Mail mail) throws Exception {
    	try {
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");
        Template t = freemarkerConfig.getTemplate("email-template.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
        System.out.println(text);

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
      
        // Using a subfolder such as /templates here
        //String template = "email/email-templates";

        helper.setTo(mail.getMailTo());
        helper.setText(text, true);
        helper.setSubject(mail.getSubject());

  			Properties javaMailProperties = ((JavaMailSenderImpl)javaMailSender).getJavaMailProperties();
  			javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");
 
        javaMailSender.send(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
      /*MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
      
        // Using a subfolder such as /templates here
        //String template = "email/email-templates";

        helper.setTo(mail.getMailTo());
        helper.setText(text, true);
        helper.setSubject(mail.getMailSubject());

        sender.send(message);*/
    }

    public void send(Mail mail) throws Exception {
      MimeMessage message = javaMailSender.createMimeMessage();

      MimeMessageHelper messageHelper = new MimeMessageHelper(message);
      messageHelper.setTo(mail.getMailTo());
      messageHelper.setText(mail.getMessageBody(), true);
      messageHelper.setSubject(mail.getSubject());
      javaMailSender.send(message);
    }
}
