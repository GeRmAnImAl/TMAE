package com.group13.tmae.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing the landing page of the application.
 *
 * This controller handles requests related to the landing page and is responsible
 * for rendering the corresponding view.
 */
@Controller
@RequestMapping("")
public class LandingPageController {

    /**
     * Handles the HTTP GET request for displaying the landing page.
     *
     * Returns the logical view name for the landing page view.
     *
     * @return the logical view name for the landing page
     */
    @GetMapping("/landing_page")
    public String showLandingPage() {
        // Return the logical view name for the landing page.
        return "landing_page";
    }
}

