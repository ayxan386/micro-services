package org.aykhan.loginservice.config;

import lombok.RequiredArgsConstructor;
import org.aykhan.loginservice.logger.LoggingFilter;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@RequiredArgsConstructor
public class LoggingConfig {

  private final LoggingFilter loggingFilter;

  @Bean
  public ServletRegistrationBean dispatcherRegistration() {
    return new ServletRegistrationBean(dispatcherServlet());
  }

  @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
  public DispatcherServlet dispatcherServlet() {
    return loggingFilter;
  }
}
