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

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
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
     * Handles the behavior after a successful authentication.
     * @param request        the servlet request.
     * @param response       the servlet response.
     * @param authentication the current authentication object.
     * @throws IOException      in case of IO errors.
     * @throws ServletException in case of servlet errors.
     */
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        response.sendRedirect(request.getContextPath() + "/landing_page"); // Replace "/" with your desired URL.
    }
}
