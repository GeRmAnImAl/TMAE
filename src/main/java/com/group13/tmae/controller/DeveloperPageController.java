package com.group13.tmae.controller;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.model.Tournament;
import com.group13.tmae.service.AthleteService;
import com.group13.tmae.service.Impl.TournamentServiceImpl;
import com.group13.tmae.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/developer")
public class DeveloperPageController {

    @Autowired
    public AthleteService athleteService;

    @Autowired
    public TournamentService tournamentService;

    @GetMapping("")
    public String developerNoTournamentSelected(Model model) {
        model.addAttribute("tournaments", this.tournamentService.getAllTournaments());

        List<Athlete> athletes = this.athleteService.getAllAthletes();

        List<List<Object>> athleteEntries = new ArrayList<>();

        for (Athlete athlete : athletes) {
            List<Object> athleteInTournament = new ArrayList<>();
            athleteInTournament.add(athlete);
            athleteInTournament.add(false);

            athleteEntries.add(athleteInTournament);
        }

        model.addAttribute("athletes", athleteEntries);

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

        return "redirect:/developer/";
    }

    @GetMapping("/{tournamentID}")
    public String showTournament(@PathVariable(value = "tournamentID") Long tournamentID, Model model) {

        List<Athlete> athletes = this.athleteService.getAllAthletes();
        Tournament tournament = this.tournamentService.getTournamentById(tournamentID);

        System.out.println("tournamentID: " + tournamentID);

        List<List<Object>> allAthletes = new ArrayList<>();

        for (Athlete athlete : athletes) {
            List<Object> athleteInTournament = new ArrayList<>();
            athleteInTournament.add(athlete);

            if (tournament.getParticipants().contains(athlete)) {
                athleteInTournament.add(true);
            } else {
                athleteInTournament.add(false);
            }

            allAthletes.add(athleteInTournament);
        }
        model.addAttribute("athletes", allAthletes);
        model.addAttribute("tournaments", this.tournamentService.getAllTournaments());

        return "secret_developer_page";
    }
}
