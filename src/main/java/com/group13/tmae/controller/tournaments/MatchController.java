package com.group13.tmae.controller.tournaments;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.model.Match;
import com.group13.tmae.repository.MatchRepository;
import com.group13.tmae.repository.TournamentRepository;
import com.group13.tmae.service.AthleteService;
import com.group13.tmae.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 *
 */
@Controller
@RequestMapping("/matches")
public class MatchController {
    /**
     *
     */
    @Autowired
    private MatchRepository matchRepository;

    /**
     *
     */
    @Autowired
    private TournamentService tournamentService;

    /**
     *
     * @param matchID
     * @param model
     * @return
     */
    @GetMapping("/{matchID}")
    public String showScoreboard(@PathVariable Long matchID, Model model){
        Match match = matchRepository.findById(matchID).orElseThrow(() -> new RuntimeException("Match not found with ID: " + matchID));
        System.out.println("*HERE* showScoreboard MatchID: " + match.getMatchID());
        model.addAttribute("match", match);
        return "scoreboard";
    }

    /**
     *
     * @param matchID
     * @param athlete1Score
     * @param athlete2Score
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/updateMatch")
    public String updateMatch(@RequestParam("matchID") Long matchID, @RequestParam("athlete1Score") Integer athlete1Score,
                              @RequestParam("athlete2Score") Integer athlete2Score, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("success", "Match update was successful!");

        return "redirect:/matches/" + this.tournamentService.updateMatch(matchID, athlete1Score, athlete2Score);
    }

    /**
     *
     * @param matchID
     * @param winnerID
     * @return
     */
    @PostMapping("/completeMatch")
    public String completeMatch(@RequestParam("matchID") Long matchID, @RequestParam("winner") Long winnerID, RedirectAttributes redirectAttributes){
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

        tournamentService.recordMatchResult(matchID, winnerID, loserID);

        return "redirect:/tournament/tournament/" + match.getTournament().getTournamentID();
    }


}
