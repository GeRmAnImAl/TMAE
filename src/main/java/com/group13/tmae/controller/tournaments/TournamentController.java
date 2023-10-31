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
        Tournament tournament = tournamentService.getTournamentById(id);
        //TODO implement the logic to display tournament information.
        //TODO change this string to the actual link.
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

}
