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

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AthleteRepository athleteRepository;

    public CustomUserDetailsService(AthleteRepository athleteRepository){
        this.athleteRepository = athleteRepository;
    }
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
     * Retrieves the currently logged-in {@code Athlete} based on the authentication context.
     * @return The {@code Athlete} object of the currently logged-in user.
     * @throws UsernameNotFoundException if no {@code Athlete} is found with the username obtained from the authentication context.
     */
    public Athlete getLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = auth.getName();

        return athleteRepository.findByUserName(loggedInUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
