package com.group13.tmae.Security;

import com.group13.tmae.repository.AthleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom implementation of {@link AuthenticationSuccessHandler} to define custom behavior on authentication success.
 * This handler redirects the user to their profile page upon successful authentication.
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /**
     *
     */
    private final AthleteRepository athleteRepository;

    /**
     * Constructs a new CustomAuthenticationSuccessHandler.
     * @param athleteRepository the user repository used to fetch user details.
     */
    @Autowired
    public CustomAuthenticationSuccessHandler(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    /**
     * Handles the behavior immediately after a successful authentication.
     * Redirects the authenticated user to their profile page.
     *
     * @param request        The HttpServletRequest being processed.
     * @param response       The HttpServletResponse to be produced as a result of the authentication.
     * @param authentication The current authentication object which contains the user's details.
     * @throws IOException      If an input or output exception occurs during redirect.
     * @throws ServletException If a servlet exception occurs during redirect.
     */
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        response.sendRedirect(request.getContextPath() + "/athlete_profile/userInfo"); // Replace "/" with your desired URL.
    }
}
