package com.group13.tmae.controller.tournaments;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.model.Match;
import com.group13.tmae.repository.MatchRepository;
import com.group13.tmae.service.Impl.CustomUserDetailsService;
import com.group13.tmae.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Controller class for managing match-related actions within a tournament.
 * Provides endpoints for displaying the scoreboard, updating match scores, and completing a match.
 */
@Controller
@RequestMapping("/matches")
public class MatchController {
    /**
     * Repository for accessing and manipulating match data.
     */
    @Autowired
    private MatchRepository matchRepository;

    /**
     * Service for managing tournament-related logic and operations.
     */
    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Displays the scoreboard for a specific match.
     *
     * @param matchID The ID of the match to display.
     * @param model   The Spring Model to pass attributes to the view.
     * @return The scoreboard view for the specified match.
     */
    @GetMapping("/{matchID}")
    public String showScoreboard(@PathVariable Long matchID, Model model){
        Match match = matchRepository.findById(matchID).orElseThrow(() -> new RuntimeException("Match not found with ID: " + matchID));
        Athlete user = this.customUserDetailsService.getLoggedInUser();

        // Show buttons if user is an admin of tournament.
        // Hide buttons is match is over.
        // Show Winner
        boolean showButton = false;
        boolean showWinner = false;
        if(match.getTournament().getAdmins().contains(user)){
            showButton = true;
        }
        if(match.getWinner() != null){
            showButton = false;
            showWinner = true;
        }

        System.out.println("*HERE* showScoreboard MatchID: " + match.getMatchID());
        model.addAttribute("match", match);
        model.addAttribute("showButton", showButton);
        model.addAttribute("showWinner", showWinner);
        return "scoreboard";
    }

    /**
     * Updates the scores of a match.
     *
     * @param matchID The ID of the match to be updated.
     * @param athlete1Score The new score for athlete 1.
     * @param athlete2Score The new score for athlete 2.
     * @param redirectAttributes Attributes for redirect scenarios.
     * @return Redirects to the updated match's scoreboard view.
     */
    @PostMapping("/updateMatch")
    public String updateMatch(@RequestParam("matchID") Long matchID, @RequestParam("athlete1Score") Integer athlete1Score,
                              @RequestParam("athlete2Score") Integer athlete2Score, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("success", "Match update was successful!");

        return "redirect:/matches/" + this.tournamentService.updateMatch(matchID, athlete1Score, athlete2Score);
    }

    /**
     * Completes a match by recording the result and determining the winner and loser.
     *
     * @param matchID The ID of the match to complete.
     * @param winnerID The ID of the winning athlete.
     * @param redirectAttributes Attributes for redirect scenarios.
     * @return Redirects to the tournament view after recording the match result.
     */
    @PostMapping("/completeMatch")
    public String completeMatch(@RequestParam("matchID") Long matchID,
                                @RequestParam("winner") Long winnerID,
                                @RequestParam("athlete1Score") Integer athlete1Score,
                                @RequestParam("athlete2Score") Integer athlete2Score,
                                RedirectAttributes redirectAttributes){
        if (winnerID == null){
            redirectAttributes.addFlashAttribute("error", "A winner must be selected to complete the match.");
            return "redirect:/matches/" + matchID;
        }
        Match match = matchRepository.findById(matchID).orElseThrow(null);
        Long loserID;
        if(match.getAthlete1().getAthleteID().equals(winnerID)){
            loserID = match.getAthlete2().getAthleteID();
        }
        else{
            loserID = match.getAthlete1().getAthleteID();
        }

        this.tournamentService.updateMatch(matchID, athlete1Score, athlete2Score);
        tournamentService.recordMatchResult(matchID, winnerID, loserID);

        return "redirect:/bracket/" + match.getTournament().getTournamentID();
    }


}
