package com.group13.tmae.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class LandingPageController {

    @GetMapping("/landing_page")
    public String showLandingPage(){
        return "landing_page";
    }
}
