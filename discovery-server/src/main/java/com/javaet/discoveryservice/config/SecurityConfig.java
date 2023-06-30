package com.javaet.discoveryservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Security Config class for spring mvc. Remember that eureka dashboard works with spring mvc.

    @Value("${eureka.username}")
    private String username;
    @Value("${eureka.password}")
    private String password;

    //TODO: Use BCrypt Password Encoder instead of NoOpPasswordEncoder.
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.inMemoryAuthentication()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser (username).password (password)
                .authorities ("USER");
        /*In production we can not use NoOpPasswordEncoder. It's deprecated by the way. For the demo purposes
        * I used it. In production we should use like BCrypt Password Encoder etc. In this way your password will not
        * be encoded it will just displayed plain text.*/
    }
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests().anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }
}
