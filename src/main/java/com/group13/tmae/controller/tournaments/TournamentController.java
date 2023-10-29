package com.group13.tmae.controller.tournaments;

import com.group13.tmae.model.Tournament;
import com.group13.tmae.repository.TournamentRepository;
import com.group13.tmae.service.Impl.CustomUserDetailsService;
import com.group13.tmae.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tournament")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/saveTournament")
    public String saveTournament(@ModelAttribute("tournament") Tournament tournament) {
        System.out.println(tournament.getStartDate().toString());
        tournamentService.createTournament(tournament);
        return "redirect:/landing_page";
    }

    @GetMapping("/tournament/{id}")
    public String showTournamentPage(@PathVariable(value = "id") Long id, Model model) {
        Tournament tournament = tournamentService.getTournamentById(id);
        //TODO implement the logic to display tournament information.
        //TODO change this string to the actual link.
        return "link to tournament page";
    }

    @GetMapping("/creation")
    public String createEvent(Model model) {

        return "/event_creation";
    }

}
