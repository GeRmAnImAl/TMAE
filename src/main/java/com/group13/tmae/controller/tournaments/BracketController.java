package com.group13.tmae.controller.tournaments;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.model.Bracket;
import com.group13.tmae.model.Tournament;
import com.group13.tmae.service.AthleteService;
import com.group13.tmae.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/bracket")
public class BracketController {

    @Autowired
    public AthleteService athleteService;

    @Autowired
    public TournamentService tournamentService;

    @GetMapping("/{tournamentID}")
    public String showTournament(@PathVariable(value = "tournamentID") Long tournamentID, Model model) {

        Tournament currentTournament = this.tournamentService.getTournamentById(tournamentID);
        Bracket currentBracket = currentTournament.getBrackets().get(0);
        model.addAttribute("totalRounds", this.tournamentService.calculateTotalRounds(currentTournament));
        model.addAttribute("currentRound", currentTournament.getCurrentRoundInfo());
        model.addAttribute("matches", currentBracket.getMatches());

        return "/bracket";
    }

    @GetMapping("/startTournament/{tournamentID}")
    public String startTournament(@PathVariable(value = "tournamentID") Long tournamentID) {

        Tournament tournament = this.tournamentService.getTournamentById(tournamentID);

        if (!tournament.isInitialBracketsGenerated()) {
            this.tournamentService.generateAndSaveBrackets(tournament);
        }

        return "redirect:/bracket/" + tournamentID;
    }
}
