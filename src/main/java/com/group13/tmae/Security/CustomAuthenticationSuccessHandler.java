package com.group13.tmae.Security;

import com.group13.tmae.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler {
    private AppUserRepository appUserRepository;

    /**
     * Constructs a new CustomAuthenticationSuccessHandler.
     *
     * @param appUserRepository the user repository used to fetch user details.
     */
    @Autowired
    public CustomAuthenticationSuccessHandler(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    /**
     * Handles the behavior after a successful authentication. Redirects users
     * to specific pages based on whether it's their first time logging in.
     *
     * @param request        the servlet request.
     * @param response       the servlet response.
     * @param authentication the current authentication object.
     * @throws IOException      in case of IO errors.
     * @throws ServletException in case of servlet errors.
     */
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        response.sendRedirect(request.getContextPath() + "/"); // Replace "dashboard" with your desired URL.
    }
}
