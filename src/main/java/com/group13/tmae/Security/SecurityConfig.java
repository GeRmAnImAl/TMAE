package com.group13.tmae.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Security configuration class that extends WebSecurityConfigurerAdapter to customize
 * security settings for the application. This configuration sets up authentication mechanisms,
 * password encoding, and authorization rules for accessing different parts of the application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Service for loading user-specific data for authentication.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Handler that is triggered upon successful user authentication.
     */
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * Utility class for generating security keys, used for remember-me functionality.
     */
    private final SecurityKeyGenerator securityKeyGenerator = new SecurityKeyGenerator();

    /**
     * Constructor that injects the necessary dependencies for the security configuration.
     *
     * @param userDetailsService The service that provides user authentication data.
     * @param authenticationSuccessHandler The handler for successful authentication events.
     */
    @Autowired
    public SecurityConfig(@Lazy UserDetailsService userDetailsService, CustomAuthenticationSuccessHandler authenticationSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    /**
     * Configures the AuthenticationManagerBuilder to use the provided UserDetailsService
     * and a BCryptPasswordEncoder for user authentication.
     *
     * @param auth The AuthenticationManagerBuilder to configure.
     * @throws Exception If any error occurs during configuration.
     */
   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Defines a Bean for BCryptPasswordEncoder to encode and verify passwords securely.
     *
     * @return A BCryptPasswordEncoder instance.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the HttpSecurity object to specify security settings, such as session management,
     * login configuration, and remember-me functionality. It sets up the authorization for
     * protected resources and configures the login and logout behavior.
     *
     * @param http The HttpSecurity object to configure.
     * @throws Exception If any error occurs during configuration.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/sign_up", "/athlete_profile/saveAthlete").permitAll()
                .antMatchers("/**.css", "/**.js", "/**.png", "/**.jpg", "/**.svg").permitAll()
                .antMatchers("/backgrounds/SportsBG1.svg").permitAll()
                .antMatchers("/").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .successHandler(authenticationSuccessHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
                .and()
                .sessionManagement()
                .invalidSessionUrl("/login")
                .sessionFixation().migrateSession()
                .maximumSessions(1)
                .expiredUrl("/login?expired")
                .and()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .rememberMe()
                .key(securityKeyGenerator.generateSecureKey())
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(1800); // 30 minutes
    }


}
