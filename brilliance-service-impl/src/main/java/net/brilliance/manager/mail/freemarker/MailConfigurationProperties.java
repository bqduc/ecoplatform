package net.brilliance.manager.mail.freemarker;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config/mail.properties")
@ConfigurationProperties("email")
public class MailConfigurationProperties {
	private String host;
	private String port;
	private String username;
	private String password;
	private String sender;
	private String senderSignature;
	private String appUrl;
	private String smtpStartTlsRequired;//Transport Layer Security
	private String smtpAuth;
	private String transportProtocol;
	private String debugEnabled;
	//private String secureSocketLayerRequired;//SSL
	//private String transportLayerSecurityRequired;//TLS
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getPort() {
		return port;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSenderSignature() {
		return senderSignature;
	}

	public void setSenderSignature(String senderSignature) {
		this.senderSignature = senderSignature;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getSmtpStartTlsRequired() {
		return smtpStartTlsRequired;
	}

	public void setSmtpStartTlsRequired(String smtpStartTlsRequired) {
		this.smtpStartTlsRequired = smtpStartTlsRequired;
	}

	public String getSmtpAuth() {
		return smtpAuth;
	}

	public void setSmtpAuth(String smtpAuth) {
		this.smtpAuth = smtpAuth;
	}

	public String getTransportProtocol() {
		return transportProtocol;
	}

	public void setTransportProtocol(String transportProtocol) {
		this.transportProtocol = transportProtocol;
	}

	public String getDebugEnabled() {
		return debugEnabled;
	}

	public void setDebugEnabled(String debugEnabled) {
		this.debugEnabled = debugEnabled;
	}
}
