package com.group13.tmae.controller.tournaments;

import com.group13.tmae.model.Bracket;
import com.group13.tmae.model.Match;
import com.group13.tmae.model.Tournament;
import com.group13.tmae.service.AthleteService;
import com.group13.tmae.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for handling requests related to tournament brackets.
 */
@Controller
@RequestMapping("/bracket")
public class BracketController {

    @Autowired
    public AthleteService athleteService;

    @Autowired
    public TournamentService tournamentService;

    /**
     * Displays the tournament bracket for a specified tournament.
     *
     * @param tournamentID the ID of the tournament to display
     * @param model        the model used for adding attributes to the view
     * @return the view name for the tournament bracket
     */
    @GetMapping("/{tournamentID}")
    public String showTournament(@PathVariable(value = "tournamentID") Long tournamentID, Model model) {

        Tournament currentTournament = this.tournamentService.getTournamentById(tournamentID);
        Bracket currentBracket = currentTournament.getBrackets().get(0);

        List<List<List<Object>>> bracketInfo = new ArrayList<>();

        // Iterate over each round of the tournament
        for (int roundNum = 1; roundNum <= this.tournamentService.calculateTotalRounds(currentTournament) +1; roundNum++){

            List<List<Object>> roundInfo = new ArrayList<>();

            // Iterate over each match in the current round
            for(Match match : currentBracket.getMatches()){

                List<Object> matchInfo = new ArrayList<>();

                // Check if the match is part of the current round
                if(match.getRoundNumber() == roundNum){

                    String firstAthlete = match.getAthlete1().getFirstName() + " " + match.getAthlete1().getLastName();
                    String secondAthlete = match.getAthlete2().getFirstName() + " " + match.getAthlete2().getLastName();
                    Integer firstAthleteScore = match.getAthlete1Score();
                    Integer secondAthleteScore = match.getAthlete2Score();

                    // Add match information to the round
                    matchInfo.add(match.getRoundNumber());
                    matchInfo.add(match.getMatchID());
                    matchInfo.add(firstAthlete);
                    matchInfo.add(secondAthlete);
                    matchInfo.add(firstAthleteScore);
                    matchInfo.add(secondAthleteScore);

                    roundInfo.add(matchInfo);
                }
            }

            // If no matches found for the round, add placeholder entries
            if(roundInfo.isEmpty()){

                int matchesPerRound = getMatchesPerRound(currentTournament, roundNum);

                for(int futureMatches = 0; futureMatches < matchesPerRound; futureMatches++){
                    List<Object> placeholder = new ArrayList<>();

                    placeholder.add(roundNum);
                    placeholder.add(null);
                    placeholder.add("To Be Determined");
                    placeholder.add("To Be Determined");
                    placeholder.add("-");
                    placeholder.add("-");

                    roundInfo.add(placeholder);
                }
            }

            bracketInfo.add(roundInfo);
        }

        // Add bracket information to the model for rendering in the view
        model.addAttribute("bracket", bracketInfo);
        model.addAttribute("totalRounds", this.tournamentService.calculateTotalRounds(currentTournament));
        model.addAttribute("currentRound", currentTournament.getCurrentRoundInfo());

        return "bracket";
    }

    /**
     * Retrieves the number of matches per round for a given tournament and round number.
     *
     * @param currentTournament the tournament for which to calculate matches
     * @param roundNum          the round number for which to calculate matches
     * @return the number of matches per round
     */
    private static int getMatchesPerRound(Tournament currentTournament, int roundNum) {
        int allParticipants = currentTournament.getAllParticipants().size();

        // Make value of all the participants even.
        int makeParticipantsEven = (allParticipants % 2 == 0) ? allParticipants : allParticipants + 1;

        // Calculate the number of matches in each round
        for (int roundCalc = 1; roundCalc <= roundNum; roundCalc++) {
            makeParticipantsEven = (makeParticipantsEven % 2 == 0) ? makeParticipantsEven / 2 : (makeParticipantsEven / 2) + 1;
        }

        return makeParticipantsEven;
    }


    /**
     * Starts the tournament by generating and saving initial brackets if not already done.
     *
     * @param tournamentID the ID of the tournament to start
     * @return the redirect URL after starting the tournament
     */
    @GetMapping("/startTournament/{tournamentID}")
    public String startTournament(@PathVariable(value = "tournamentID") Long tournamentID) {

        Tournament tournament = this.tournamentService.getTournamentById(tournamentID);

        // Generate and save brackets if not already done
        if (!tournament.isInitialBracketsGenerated()) {
            this.tournamentService.generateAndSaveBrackets(tournament);
        }

        return "redirect:/bracket/" + tournamentID;
    }
}
