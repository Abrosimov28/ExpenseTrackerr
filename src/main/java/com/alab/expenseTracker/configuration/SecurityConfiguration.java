package com.alab.expenseTracker.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// ak chceme robime java configuracie pre Spring Security, musime extendovat WebSecurityConfigurerAdapter

// configure anotacia hovori ze toto je config class
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//      Allows all requests to the /, /products, /product/show/*, /console/** paths (Line 5)
//      Secures all other paths of the application to require authentication (Line 6)
//      Allows everyone to view a custom /login page specified by loginPage()(Line 8)
//      Permits all to make logout calls (Line 10)
//      Disables CSRF protection (Line 12)
//      Disables X-Frame-Options in Spring Security (Line 13) for access to H2 database console. By default, Spring Security will protect against CRSF attacks.
//
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests().antMatchers("/","/expenses","/expenseGet/*","/console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().permitAll();

        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ADMIN")
                .and().withUser("user").password("user").roles("USER");;
    }

}