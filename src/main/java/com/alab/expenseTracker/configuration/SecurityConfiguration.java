package com.alab.expenseTracker.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// ak chceme robime java configuracie pre Spring Security, musime extendovat WebSecurityConfigurerAdapter

// configure anotacia hovori ze toto je config class
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // povolujeme all request access do rootu a do consoly
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/").permitAll().and()
                .authorizeRequests().antMatchers("/console/**").permitAll();

        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();

    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**");
         web.ignoring().antMatchers("/css/**","/fonts/**","/libs/**");
    }

}