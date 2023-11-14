package com.group13.tmae.controller;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.model.Tournament;
import com.group13.tmae.service.AthleteService;
import com.group13.tmae.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;


/**
 * Controller class for handling requests related to developer operations.
 */
@Controller
@RequestMapping("/developer")
public class DeveloperPageController {

    @Autowired
    public AthleteService athleteService;

    @Autowired
    public TournamentService tournamentService;

    /**
     * Handles GET requests to the "/developer" endpoint.
     *
     * @param model the model used for adding attributes to the view
     * @return the view name for the developer page
     */
    @GetMapping("")
    public String developerNoTournamentSelected(Model model) {
        // Get all tournaments and add them to the model
        model.addAttribute("tournaments", this.tournamentService.getAllTournaments());

        // Get all athletes and prepare a list for the view
        List<Athlete> athletes = this.athleteService.getAllAthletes();
        List<List<Object>> athleteEntries = new ArrayList<>();

        // Populate the list with athletes and a flag indicating they are not selected
        for (Athlete athlete : athletes) {
            List<Object> athleteInTournament = new ArrayList<>();
            athleteInTournament.add(athlete);
            athleteInTournament.add(false);
            athleteEntries.add(athleteInTournament);
        }

        // Add the athlete list to the model
        model.addAttribute("athletes", athleteEntries);

        // Return the view name for the developer page
        return "secret_developer_page";
    }

    /**
     * Handles POST requests to the "/developer/update_tournament" endpoint.
     *
     * @param tournamentID the ID of the tournament to be updated
     * @param athleteIDs   a list of athlete IDs to be added to the tournament
     * @return the redirect URL after updating the tournament
     */
    @PostMapping("/update_tournament")
    public String developerAddToTournament(@RequestParam("tournamentDropdown") Long tournamentID,
                                           @RequestParam(name = "developerSelectedAthletes", required = false) List<Long> athleteIDs) {

        // Create a list to store athletes to be added to the tournament
        List<Athlete> updateTournamentAthletes = new ArrayList<>();

        // Check if a valid tournament ID is provided
        if (tournamentID != 0) {
            // Check if athlete IDs are provided
            if (athleteIDs != null) {
                // Retrieve athletes by their IDs and add them to the list
                for (Long athlete : athleteIDs) {
                    updateTournamentAthletes.add(this.athleteService.getAthleteById(athlete));
                }
            }

            // Get the selected tournament
            Tournament selectedTournament = this.tournamentService.getTournamentById(tournamentID);

            // Update the tournament with the new list of participants
            selectedTournament.setParticipants(updateTournamentAthletes);

            // Save the updated tournament
            this.tournamentService.updateTournament(selectedTournament);
        }

        // Redirect to the developer page
        return "redirect:/developer/";
    }

    /**
     * Handles GET requests to the "/developer/{tournamentID}" endpoint.
     *
     * @param tournamentIDString the ID of the tournament to be displayed
     * @param model              the model used for adding attributes to the view
     * @return the view name for the developer page with information about the specified tournament
     */
    @GetMapping("/{tournamentID}")
    public String showTournament(@PathVariable(value = "tournamentID") String tournamentIDString, Model model) {

        // Parse the tournament ID from the path variable
        Long tournamentID = Long.parseLong(tournamentIDString);

        // Get all athletes and the specified tournament
        List<Athlete> athletes = this.athleteService.getAllAthletes();
        Tournament tournament = this.tournamentService.getTournamentById(tournamentID);

        // Create a list to store athletes and a flag indicating their participation status
        List<List<Object>> allAthletes = new ArrayList<>();

        // Populate the list based on athlete participation in the tournament
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

        // Add attributes to the model for the view
        model.addAttribute("athletes", allAthletes);
        model.addAttribute("tournaments", this.tournamentService.getAllTournaments());
        model.addAttribute("currentTournamentID", tournamentID);

        // Return the view name for the developer page
        return "secret_developer_page";
    }
}
