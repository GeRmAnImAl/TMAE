package com.group13.tmae.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }

    @GetMapping("/")
    public String redirectToLogin(){
        return "redirect:/login";
    }
}
