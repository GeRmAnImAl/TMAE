package com.group13.tmae.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for managing the landing page of the application.
 * This controller handles requests related to the landing page and is responsible
 * for rendering the corresponding view.
 */
@Controller
@RequestMapping("")
public class LandingPageController {

    /**
     * Handles the HTTP GET request for displaying the landing page.
     * Returns the logical view name for the landing page view.
     *
     * @return the logical view name for the landing page
     */
    @GetMapping("/landing_page")
    public String showLandingPage() {
        return "landing_page";
    }
}

