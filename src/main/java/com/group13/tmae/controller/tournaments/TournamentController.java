package com.group13.tmae.controller.tournaments;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.model.Tournament;
import com.group13.tmae.repository.TournamentRepository;
import com.group13.tmae.service.Impl.CustomUserDetailsService;
import com.group13.tmae.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Controller for handling tournament-related operations.
 */
@Controller
@RequestMapping("/tournament")
public class TournamentController {
    /**
     * Service for handling operations related to tournament management.
     * This includes creating, updating, deleting, and querying tournament data.
     */
    @Autowired
    private TournamentService tournamentService;

    /**
     * Repository for direct database access and operations on tournament entities.
     * Provides a more granular level of data manipulation than TournamentService.
     */
    @Autowired
    private TournamentRepository tournamentRepository;

    /**
     * Custom service for user details, providing access to the currently authenticated user's data
     * and handling user-related business logic.
     */
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Saves a new tournament to the database and redirects to the tournament list view.
     *
     * @param tournament The tournament object to save.
     * @return The view name to redirect to after saving the tournament.
     */
    @PostMapping("/saveTournament")
    public String saveTournament(@ModelAttribute("tournament") Tournament tournament) {

        Athlete user = this.customUserDetailsService.getLoggedInUser();

        ArrayList<Athlete> admins = new ArrayList<>();

        admins.add(user);

        tournament.setAdmins(admins);

        tournamentService.createTournament(tournament);

        return "redirect:/tournament/listAllTournaments";
    }

    /**
     * Displays the tournament page for a specific tournament.
     *
     * @param id    The unique identifier of the tournament to display.
     * @param model The view model to pass to the front end.
     * @return The name of the view to render the tournament page.
     */
    @GetMapping("/{id}")
    public String showTournamentPage(@PathVariable(value = "id") Long id, Model model) {
        Tournament tournament = this.tournamentService.getTournamentById(id);
        Athlete user = this.customUserDetailsService.getLoggedInUser();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");

        model.addAttribute("tournamentName", tournament.getTournamentName());
        model.addAttribute("tournamentLocation", tournament.getLocation());
        model.addAttribute("tournamentWebsite", tournament.getWebsiteLink());
        model.addAttribute("maxParticipants", tournament.getMaxNumParticipants());
        model.addAttribute("startDate", tournament.getStartDate());
        model.addAttribute("endDate", tournament.getEndDate());
        model.addAttribute("registerBy", tournament.getRegistrationDeadline());
        model.addAttribute("participants", tournament.getParticipants());
        model.addAttribute("participantsNumber", tournament.getParticipants().size());
        model.addAttribute("readOnly", "readonly");
        model.addAttribute("tournamentID", tournament.getTournamentID());

        return "/eventInfo";
    }

    /**
     * Displays the view for creating a new event.
     *
     * @param model The view model to pass to the front end.
     * @return The name of the view to render the event creation page.
     */
    @GetMapping("/creation")
    public String createEvent(Model model) {

        return "/event_creation";
    }

    /**
     * Displays the list of all tournaments.
     *
     * @param model The view model to pass to the front end.
     * @return The name of the view to render the list of all tournaments.
     */
    @GetMapping("/listAllTournaments")
    public String showAllTournaments(Model model){
        model.addAttribute("listAllTournaments", this.tournamentService.getAllTournaments());

        return "/tournament_list";
    }

    /**
     * Registers the currently logged-in athlete to the specified tournament and redirects to the tournament list view.
     *
     * @param tournamentID The unique identifier of the tournament to join.
     * @param model        The view model to pass to the front end.
     * @return The name of the view to render after joining the tournament.
     */
    @GetMapping("/joinTournament/{id}")
    public String joinTournament(@PathVariable("id") Long tournamentID, Model model){
        Athlete user = this.customUserDetailsService.getLoggedInUser();
        Tournament tournament = this.tournamentService.getTournamentById(tournamentID);

        this.tournamentService.joinTournament(user, tournament);

        model.addAttribute("listAllTournaments", this.tournamentService.getAllTournaments());

        return "/tournament_list";
    }

    /**
     * Withdraws the currently logged-in athlete from the specified tournament and redirects to the athlete profile view.
     *
     * @param tournamentID The unique identifier of the tournament to leave.
     * @return The view name to redirect to after leaving the tournament.
     */
    @GetMapping("/leaveTournament/{id}")
    public String leaveTournament(@PathVariable("id") Long tournamentID){
        Athlete user = this.customUserDetailsService.getLoggedInUser();
        Tournament tournament = this.tournamentService.getTournamentById(tournamentID);

        this.tournamentService.leaveTournament(user, tournament);

        return "redirect:/athlete_profile/userInfo";
    }

    /**
     * Updates an existing tournament with new information and redirects to the updated tournament's page.
     *
     * @param tournamentInput The tournament object with updated information.
     * @return The view name to redirect to after updating the tournament.
     */
    @PostMapping("/updateTournament")
    public String updateTournament(@ModelAttribute("tournament") Tournament tournamentInput){

        /* Since the incoming model doesn't include the participants list, we need to get it from the existing DB entry */
        Tournament savedTournament = this.tournamentService.getTournamentById(tournamentInput.getTournamentID());

        tournamentInput.setParticipants(savedTournament.getParticipants());

        /* Do the same for admins */
        tournamentInput.setAdmins(savedTournament.getAdmins());

        this.tournamentService.updateTournament(tournamentInput);

        return "redirect:/tournament/" + tournamentInput.getTournamentID();
    }

    @GetMapping("/tournament{tournamentID}/bracket")
    public String showTournamentBracket(){
        return "";
    }

}
