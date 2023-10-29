package com.group13.tmae.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * This configuration class extends WebSecurityConfigurerAdapter to customize
 * Spring Security configurations.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * Constructs a SecurityConfig with the specified UserDetailsService and
     * CustomAuthenticationSuccessHandler.
     * @param userDetailsService        the service to fetch user details
     * @param authenticationSuccessHandler the handler for authentication success events
     */
    @Autowired
    public SecurityConfig(@Lazy UserDetailsService userDetailsService, CustomAuthenticationSuccessHandler authenticationSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    /**
     * Configures the specified AuthenticationManagerBuilder to use the custom
     * UserDetailsService and a BCryptPasswordEncoder.
     * @param auth          the AuthenticationManagerBuilder to configure
     * @throws Exception    if an error occurs
     */
   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Provides the BCryptPasswordEncoder for password encoding.
     * @return A BCryptPasswordEncoder instance.
    */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the specified HttpSecurity object to customize the security
     * settings. This includes configuring URL access permissions and login settings.
     * @param http          the HttpSecurity object to configure
     * @throws Exception    if an error occurs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/sign_up", "/athlete_profile/saveAthlete").permitAll()
                .antMatchers("/**.css", "/**.js", "/**.png", "/**.jpg", "/**.svg").permitAll()
                .antMatchers("/backgrounds/login-background.svg").permitAll()
                .antMatchers("/").authenticated()
                .anyRequest().authenticated()
                .and()
                //Go to landing page after login
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .successHandler(authenticationSuccessHandler)
                .and()
                .logout()
                .logoutUrl("/logout") // Logout URL
                .invalidateHttpSession(true) // Invalidate user session
                .clearAuthentication(true) // Clear user authentication
                .permitAll();
    }


}
