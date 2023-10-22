package com.group13.tmae.controller.athletes;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.repository.AthleteRepository;
import com.group13.tmae.service.AthleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/athlete_profile")
public class AthleteProfileController {
    @Autowired
    private AthleteService athleteService;
    @Autowired
    private AthleteRepository athleteRepository;

    @PostMapping("/saveAthlete")
    public String saveAthlete(@ModelAttribute("athlete")Athlete athlete){

        athleteService.createAthlete(athlete);
        return "redirect:/landing_page";
    }
}
