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

/**
 *
 */
@Controller
@RequestMapping("/tournament")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     *
     * @param tournament
     * @return
     */
    @PostMapping("/saveTournament")
    public String saveTournament(@ModelAttribute("tournament") Tournament tournament) {
        tournamentService.createTournament(tournament);

        return "redirect:/tournament/listAllTournaments";
    }

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tournament/{id}")
    public String showTournamentPage(@PathVariable(value = "id") Long id, Model model) {
        Tournament tournament = this.tournamentService.getTournamentById(id);
        Athlete user = this.customUserDetailsService.getLoggedInUser();

        model.addAttribute("tournamentName", tournament.getTournamentName());
        model.addAttribute("tournamentLocation", tournament.getLocation());
        model.addAttribute("tournamentWebsite", tournament.getWebsiteLink());
        model.addAttribute("maxParticipants", tournament.getMaxNumParticipants());
        model.addAttribute("startDate", tournament.getStartDate());
        model.addAttribute("endDate", tournament.getEndDate());
        model.addAttribute("registerBy", tournament.getRegistrationDeadline());
        model.addAttribute("participants", tournament.getParticipants());
        model.addAttribute("participantsNumber", tournament.getParticipants().size());
        model.addAttribute("readOnly", String "readonly");

        return "link to tournament page";
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/creation")
    public String createEvent(Model model) {

        return "/event_creation";
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/listAllTournaments")
    public String showAllTournaments(Model model){
        model.addAttribute("listAllTournaments", this.tournamentService.getAllTournaments());

        return "/tournament_list";
    }

    /**
     *
     * @param tournamentID
     * @param model
     * @return
     */
    @GetMapping("/joinTournament/{id}")
    public String joinTournament(@PathVariable("id") Long tournamentID, Model model){
        Athlete user = this.customUserDetailsService.getLoggedInUser();
        Tournament tournament = this.tournamentService.getTournamentById(tournamentID);

        this.tournamentService.joinTournament(user, tournament);

        model.addAttribute("listAllTournaments", this.tournamentService.getAllTournaments());

        return "/tournament_list";
    }

    @GetMapping("/leaveTournament/{id}")
    public String leaveTournament(@PathVariable("id") Long tournamentID){
        Athlete user = this.customUserDetailsService.getLoggedInUser();
        Tournament tournament = this.tournamentService.getTournamentById(tournamentID);

        this.tournamentService.leaveTournament(user, tournament);

        return "redirect:/athlete_profile/userInfo";
    }

}
