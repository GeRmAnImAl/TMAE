package com.group13.tmae.controller.tournaments;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.model.Bracket;
import com.group13.tmae.model.Match;
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

        List<List<List<Object>>> bracketInfo = new ArrayList<>();

        for (int i = 1; i <= this.tournamentService.calculateTotalRounds(currentTournament); i++){

            List<List<Object>> roundInfo = new ArrayList<>();

            for(Match match : currentBracket.getMatches()){

                List<Object> matchInfo = new ArrayList<>();

                if(match.getRoundNumber() == i){

                    String firstAthlete = match.getAthlete1().getFirstName() + " " + match.getAthlete1().getLastName();
                    String secondAthlete = match.getAthlete2().getFirstName() + " " + match.getAthlete2().getLastName();
                    Integer firstAthleteScore = match.getAthlete1Score();
                    Integer secondAthleteScore = match.getAthlete2Score();

                    matchInfo.add(currentTournament.getCurrentRoundInfo());
                    matchInfo.add(match.getMatchID());
                    matchInfo.add(firstAthlete);
                    matchInfo.add(secondAthlete);
                    matchInfo.add(firstAthleteScore);
                    matchInfo.add(secondAthleteScore);
                }
                roundInfo.add(matchInfo);
            }

            if(roundInfo.isEmpty()){
                List<Object> placeholder = new ArrayList<>();

                placeholder.add(i);
                placeholder.add(null);
                placeholder.add("To Be Determined");
                placeholder.add("To Be Determined");
                placeholder.add("-");
                placeholder.add("-");

                roundInfo.add(placeholder);
            }

            bracketInfo.add(roundInfo);
        }

        model.addAttribute("bracket", bracketInfo);
        model.addAttribute("totalRounds", this.tournamentService.calculateTotalRounds(currentTournament));
        model.addAttribute("currentRound", currentTournament.getCurrentRoundInfo());

        return "bracket";
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
