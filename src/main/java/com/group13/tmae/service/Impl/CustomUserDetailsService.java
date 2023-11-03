package com.group13.tmae.service.Impl;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.repository.AthleteRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Custom implementation of the {@link UserDetailsService} interface that loads user-specific data.
 * It is used by the Spring Security framework to handle user authentication and authorization.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Repository for accessing and managing athlete entities.
     */
    private final AthleteRepository athleteRepository;

    /**
     * Constructs a new {@code CustomUserDetailsService} with the specified athlete repository.
     *
     * @param athleteRepository The repository used for retrieving athlete data from the database.
     */
    public CustomUserDetailsService(AthleteRepository athleteRepository){
        this.athleteRepository = athleteRepository;
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search may be case-sensitive or case-insensitive
     * depending on how the {@code AthleteRepository} implementation behaves.
     *
     * @param username The username identifying the user whose data is required.
     * @return A fully populated user record (never {@code null}).
     * @throws UsernameNotFoundException If the user could not be found or the user has no GrantedAuthority.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Athlete athlete = athleteRepository.findByUserName(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found!"));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + athlete);

        return org.springframework.security.core.userdetails.User
                .withUsername(athlete.getUserName())
                .password(athlete.getPassword())
                .authorities(Collections.singleton(authority))
                .build();
    }

    /**
     * Retrieves the currently logged-in {@link Athlete} based on the authentication context.
     *
     * @return The {@link Athlete} object of the currently logged-in user.
     * @throws UsernameNotFoundException If no {@link Athlete} is found with the username obtained from the authentication context.
     */
    public Athlete getLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = auth.getName();

        return athleteRepository.findByUserName(loggedInUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
