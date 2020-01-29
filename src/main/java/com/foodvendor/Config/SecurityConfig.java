package com.foodvendor.Config;

import com.foodvendor.payload.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Security configuration class
 */
@Configuration
@EnableConfigurationProperties
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Method configures security access control based on roles
     * @param http
     * @throws Exception unauthorized and forbidden access
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
                http
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        .authorizeRequests()
                        .antMatchers("/auth/signup","/auth/signin").permitAll()
                        .antMatchers("/menu/**","/order/id","/auth/order").hasAnyRole("USER", "ADMIN")
                        .antMatchers("/developer/**","/order/**").hasRole("ADMIN").anyRequest().authenticated()
                        .and().
                        httpBasic();
    }

    /**
     * Method encrypts password using bcrypt encryption scheme
     * @return encrypted password
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Method configures user profile for security access. Two sets of authentications are implemented, one for customers stored in mongo database
     * and one in memory user that serves as admin (for this assessment purpose)
     * @param builder
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(customUserDetailsService);
        builder
                .inMemoryAuthentication().withUser("Admin")
                .password(passwordEncoder().encode("adminpassword"))
                .roles("ADMIN");
    }

}