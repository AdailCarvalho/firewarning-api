package com.fortalezasec.firewarning.security;

import com.fortalezasec.firewarning.services.LoadUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.caelum.stella.validation.CNPJValidator;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers("/**")
      .permitAll()
      .anyRequest()
      .authenticated().and()
      .formLogin().and()
      .httpBasic();

    http.csrf().ignoringAntMatchers("/**");
    http.headers().frameOptions().sameOrigin();
  }

  @Bean
  public UserDetailsService userDetailService() {
    return new LoadUserDetailsService();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CNPJValidator cnpjValidator() {
    return new CNPJValidator();
  }
}
