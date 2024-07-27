package tw.teddysoft.tasks.io.spring.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Bean
  ServletRegistrationBean h2servletRegistration() {
    ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
    registrationBean.addUrlMappings("/console/*");
    return registrationBean;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    //      add this line to use H2 web console
    http.authorizeRequests()
        .antMatchers("/")
        .permitAll()
        .and()
        .authorizeRequests()
        .antMatchers("/console/**")
        .permitAll();
    http.csrf().disable();
    http.headers().frameOptions().disable();
  }
}
