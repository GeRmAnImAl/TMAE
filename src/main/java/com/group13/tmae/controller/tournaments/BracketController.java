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

import java.util.*;

/**
 * Controller class for handling requests related to tournament brackets.
 */
@Controller
@RequestMapping("/bracket")
public class BracketController {

    /**
     * todo
     */
    @Autowired
    public AthleteService athleteService;

    /**
     * todo
     */
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
        ArrayList<Match> matches = new ArrayList<>();
        Map<Integer, Long> byes = new HashMap<>();
        int roundCount = this.tournamentService.calculateTotalRounds(currentTournament);
        int totalRounds = this.tournamentService.calculateTotalRounds(currentTournament);
        Athlete winner = null;

        for(Bracket bracket : currentTournament.getBrackets()){
            matches.addAll(bracket.getMatches());
            // Get the athlete that had a bye for each bracket in a tournament
            if(bracket.getByeAthleteID() != null  && !bracket.getMatches().isEmpty()){
                byes.put(bracket.getMatches().get(0).getRoundNumber(), bracket.getByeAthleteID());
            }
        }

        List<List<List<Object>>> bracketInfo = new ArrayList<>();

        // Iterate over each round of the tournament
        for (Integer roundNum = 1; roundNum <= totalRounds; roundNum++){

            List<List<Object>> roundInfo = new ArrayList<>();

            // If there is a bye this round
            if(byes.containsKey(roundNum)){
                List<Object> byePlaceholder = new ArrayList<>();
                Long byeAthleteID = byes.get(roundNum);
                Athlete byeAthlete = this.athleteService.getAthleteById(byeAthleteID);

                byePlaceholder.add(roundNum);
                byePlaceholder.add(null);
                byePlaceholder.add(byeAthlete.getFirstName() + " " + byeAthlete.getLastName());
                byePlaceholder.add("-----BYE-----");
                byePlaceholder.add("-");
                byePlaceholder.add("-");

                roundInfo.add(byePlaceholder);
            }

            // Iterate over each match in the current round
            for(Match match : matches){

                List<Object> matchInfo = new ArrayList<>();

                // Check if the match is part of the current round
                if(Objects.equals(match.getRoundNumber(), roundNum)){

                    String firstAthlete = match.getAthlete1().getFirstName() + " " + match.getAthlete1().getLastName();
                    String secondAthlete = match.getAthlete2().getFirstName() + " " + match.getAthlete2().getLastName();
                    Integer firstAthleteScore = match.getAthlete1Score();
                    Integer secondAthleteScore = match.getAthlete2Score();
                    boolean loserAthlete1 = false;
                    boolean loserAthlete2 = false;

                    // Add match information to the round
                    matchInfo.add(match.getRoundNumber());
                    matchInfo.add(match.getMatchID());
                    matchInfo.add(firstAthlete);
                    matchInfo.add(secondAthlete);
                    matchInfo.add(firstAthleteScore);
                    matchInfo.add(secondAthleteScore);

                    // Check to see if there is a winner and loser
                    if(match.getLoser() != null){
                        if(match.getLoser().equals(match.getAthlete1())){
                            loserAthlete1 = true;
                        }
                        if (match.getLoser().equals(match.getAthlete2())){
                            loserAthlete2 = true;
                        }
                    }

                    matchInfo.add(loserAthlete1);
                    matchInfo.add(loserAthlete2);

                    roundInfo.add(matchInfo);
                }
                // Code to display tournament champion
                if((match.getRoundNumber() == totalRounds) && (match.getWinner() != null)){
                    winner = match.getWinner();
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
        model.addAttribute("totalRounds", roundCount);
        model.addAttribute("currentRound", currentTournament.getCurrentRoundInfo());
        model.addAttribute("tournamentName", currentTournament.getTournamentName());
        model.addAttribute("champion", winner);

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
