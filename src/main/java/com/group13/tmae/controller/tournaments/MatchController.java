package com.group13.tmae.controller.tournaments;

import com.group13.tmae.model.Match;
import com.group13.tmae.repository.MatchRepository;
import com.group13.tmae.repository.TournamentRepository;
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
     */
    @Autowired
    private TournamentRepository tournamentRepository;

    /**
     *
     * @param matchID
     * @param model
     * @return
     */
    @GetMapping("/{matchID}")
    public String showScoreboard(@PathVariable Long matchID, Model model){
        Match match = matchRepository.findById(matchID).orElseThrow(null);
        model.addAttribute("match", match);
        return "scoreboard";
    }

    /**
     *
     * @param match
     * @return
     */
    @PostMapping("/updateMatch")
    public String updateMatch(@ModelAttribute Match match){
        matchRepository.save(match);
        return "redirect:/matches/" + match.getMatchID();
    }

    /**
     *
     * @param matchID
     * @param winnerID
     * @return
     */
    @GetMapping("/completeMatch/{matchID}")
    public String completeMatch(@PathVariable Long matchID, @RequestParam(required = false) Long winnerID, RedirectAttributes redirectAttributes){
        if (winnerID == null){
            redirectAttributes.addFlashAttribute("error", "A winner must be selected to complete the match.");
            return "redirect:/matches/" + matchID;
        }
        Match match = matchRepository.findById(matchID).orElseThrow(null);
        Long loserID;
        if(match.getAthlete1().getAthleteID().equals(match.getWinner().getAthleteID())){
            loserID = match.getAthlete2().getAthleteID();
        }
        else{
            loserID = match.getAthlete1().getAthleteID();
        }

        tournamentService.recordMatchResult(matchID, winnerID, loserID);

        return "redirect:/tournament/" + match.getTournament().getTournamentID();
    }


}
