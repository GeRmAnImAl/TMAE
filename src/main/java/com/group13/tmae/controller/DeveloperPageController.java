package com.group13.tmae.controller;

import com.group13.tmae.model.Tournament;
import com.group13.tmae.service.AthleteService;
import com.group13.tmae.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequestMapping("/developer")
public class DeveloperPageController {

    @Autowired
    public AthleteService athleteService;

    @Autowired
    public TournamentService tournamentService;

    @GetMapping("")
    public String athleteTournamentAdd(Model model) {
        model.addAttribute("athletes", this.athleteService.getAllAthletes());
        model.addAttribute("tournaments", this.tournamentService.getAllTournaments());

        return "secret_developer_page";
    }

    @PostMapping("/add_to_tournament")
    public String developerAddToTournament(@RequestParam("tournamentDropdown") Long tournamentID,
                                           @RequestParam(name = "developerSelectedAthletes", required = false) List<Long> athleteIDs) {

        if (tournamentID != 0) {
            if (athleteIDs != null) {
                for (Long athlete : athleteIDs) {
                    System.out.println(this.athleteService.getAthleteById(athlete).getFirstName());
                    System.out.println("Selected tournament: " + this.tournamentService.getTournamentById(tournamentID).getTournamentName());
                }
            }
        }

        return "redirect:/developer";
    }
}
