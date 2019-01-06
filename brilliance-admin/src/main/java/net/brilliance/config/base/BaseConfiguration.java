/**
 * 
 */
package net.brilliance.config.base;

import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.brilliance.config.handler.listener.WebHttpSessionListener;

/**
 * @author ducbq
 *
 */
@EnableCaching
@Configuration
@ComponentScan({
	ConfigurationConstants.PACKAGE_ROOT
})
@EntityScan(
		/*basePackageClasses = {AbstractAuditEntity.class}, */
		basePackages={ ConfigurationConstants.PACKAGE_ENTITY, ConfigurationConstants.PACKAGE_DOMAIN })
@EnableJpaRepositories(ConfigurationConstants.PACKAGE_REPOSITORY)
@EnableTransactionManagement
public abstract class BaseConfiguration {
	/**
	 * {@link PasswordEncoder} bean.
	 * 
	 * @return <b>{@code BCryptPasswordEncoder}</b> with strength (passed as
	 *         argument) the log rounds to use, between 4 and 31
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return /*new VpxPasswordEncoder();*/new BCryptPasswordEncoder();
	}
	
  @Bean
  public ServletListenerRegistrationBean<HttpSessionListener> sessionListener(){
      return new ServletListenerRegistrationBean<HttpSessionListener>(new WebHttpSessionListener());
  }	
}
