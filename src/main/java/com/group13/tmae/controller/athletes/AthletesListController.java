package com.group13.tmae.controller.athletes;

import com.group13.tmae.service.AthleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for managing athlete-related operations and views.
 * This controller handles requests related to athletes and interacts with the AthleteService
 * to retrieve athlete data.
 */
@Controller
@RequestMapping("/athletes")
public class AthletesListController {

    /**
     * Autowired AthleteService instance to facilitate athlete-related operations.
     */
    @Autowired
    public AthleteService athleteService;

    /**
     * Handles the HTTP GET request for displaying the list of athletes.
     * Retrieves the list of athletes from the AthleteService and adds it to the model
     * for rendering in the corresponding view.
     *
     * @param model the Spring Model object to which attributes are added
     * @return the logical view name for the athletes list view
     */
    @GetMapping("athletes_list")
    public String showAthletesList(Model model) {
        // Retrieve the list of athletes from the AthleteService
        // and add it to the model for rendering in the view.
        model.addAttribute("listAthletes", this.athleteService.getAllAthletes());

        // Return the logical view name for the athletes list view.
        return "athletes/athletes_list";
    }

}

