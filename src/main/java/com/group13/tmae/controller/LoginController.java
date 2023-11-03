package com.group13.tmae.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling login and sign-up operations.
 * It provides mappings for login, sign-up, and the root context.
 */
@Controller
@RequestMapping
public class LoginController {

    /**
     * Displays the login page or redirects to the landing page if the user is already authenticated.
     *
     * @return The name of the login view if the user is not authenticated, or a redirect to the landing page if they are.
     */
    @GetMapping("/login")
    public String showLoginPage(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/landing_page";
        }
        return "login";
    }

    /**
     * Redirects any requests to the root context to the login page.
     *
     * @return A redirect string to the login page.
     */
    @GetMapping("/")
    public String redirectToLogin(){
        return "redirect:/login";
    }

    /**
     * Displays the sign-up page for new users to create an account.
     *
     * @return The name of the sign-up view.
     */
    @GetMapping("/sign_up")
    public String showSignUpPage(){
        return "sign_up";
    }
}
