package com.redlab.plainnet.config;

import com.redlab.plainnet.security.SecurityChainConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityChainConfigurator securityChainConfigurator;

    @Autowired
    public SecurityConfig(SecurityChainConfigurator securityChainConfigurator) {
        this.securityChainConfigurator = securityChainConfigurator;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .authorizeRequests()
                .antMatchers("/api/user/registration", "/api/user/login").permitAll()
                .antMatchers("/api/user/check").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .apply(securityChainConfigurator);
    }
}
