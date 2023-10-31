package com.group13.tmae.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/landing_page";
        }
        return "login";
    }

    @GetMapping("/")
    public String redirectToLogin(){
        return "redirect:/login";
    }

    @GetMapping("/sign_up")
    public String showSignUpPage(){
        return "sign_up";
    }
}
