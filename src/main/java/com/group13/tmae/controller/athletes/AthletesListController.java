package com.group13.tmae.controller.athletes;

import com.group13.tmae.service.AthleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/athletes")
public class AthletesListController {

    @Autowired
    public AthleteService athleteService;

    @GetMapping("athletes_list")
    public String showAthletesList(Model model) {
        model.addAttribute("listAthletes", this.athleteService.getAllAthletes());

        return "athletes/athletes_list";
    }

}
