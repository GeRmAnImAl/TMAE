package com.group13.tmae.service.Impl;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.model.Bracket;
import com.group13.tmae.model.Match;
import com.group13.tmae.model.Tournament;
import com.group13.tmae.repository.AthleteRepository;
import com.group13.tmae.repository.BracketRepository;
import com.group13.tmae.repository.MatchRepository;
import com.group13.tmae.repository.TournamentRepository;
import com.group13.tmae.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service implementation for managing tournaments.
 * Provides methods to create, retrieve, update, and delete tournaments,
 * as well as to manage tournament participation and brackets.
 */
@Service
public class TournamentServiceImpl implements TournamentService {

    /**
     * Autowired TournamentRepository instance for database interaction.
     */
    @Autowired
    private TournamentRepository tournamentRepository;

    /**
     * Autowired BracketRepository instance for database interaction.
     */
    @Autowired
    private BracketRepository bracketRepository;

    /**
     * Autowired MatchRepository instance for database interaction.
     */
    @Autowired
    private MatchRepository matchRepository;

    /**
     * Autowired AthleteRepository instance for database interaction.
     */
    @Autowired
    private AthleteRepository athleteRepository;

    /**
     * Creates a new tournament.
     *
     * @param tournament The Tournament object to be created.
     */
    @Override
    public void createTournament(Tournament tournament) {
        this.tournamentRepository.save(tournament);
    }

    /**
     * Retrieves a list of all tournaments.
     *
     * @return List of Tournament objects.
     */
    @Override
    public List<Tournament> getAllTournaments() {
        return this.tournamentRepository.findAll();
    }

    /**
     * Retrieves a tournament by their unique identifier.
     *
     * @param id The unique identifier of the Tournament.
     * @return The Tournament object with the specified ID, or null if not found.
     */
    @Override
    public Tournament getTournamentById(Long id) {
        return this.tournamentRepository.findById(id).orElse(null);
    }

    /**
     * Updates an existing athlete.
     *
     * @param tournament The Tournament object to be updated.
     */
    @Override
    public void updateTournament(Tournament tournament) {this.tournamentRepository.save(tournament);
    }

    /**
     * Deletes an Tournament by their unique identifier.
     *
     * @param id The unique identifier of the Tournament to be deleted.
     */
    @Override
    public void deleteTournament(Long id) {
        this.tournamentRepository.deleteById(id);
    }

    /**
     * Retrieves a paginated list of tournaments, sorted based on specified criteria.
     *
     * @param pageNo        The page number to be retrieved.
     * @param pageSize      The number of items per page.
     * @param sortField     The field by which the results should be sorted.
     * @param sortDirection The sorting direction, either "asc" (ascending) or "desc" (descending).
     * @return A Page object containing the paginated list of Tournament objects.
     */
    @Override
    public Page<Tournament> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        return this.tournamentRepository.findAll(pageable);
    }

    /**
     * Registers an athlete for a given tournament.
     * If the athlete is already registered, a message is printed indicating so.
     *
     * @param athlete    The athlete to register.
     * @param tournament The tournament for which to register the athlete.
     */
    @Override
    public void joinTournament(Athlete athlete, Tournament tournament) {

        if(!tournament.getAllParticipants().contains(athlete)){
            tournament.getAllParticipants().add(athlete);
            tournament.getParticipants().add(athlete);
            updateTournament(tournament);
        }
        else {
            System.out.println("Already Joined That Tournament");
        }
    }

    /**
     * Withdraws an athlete from a given tournament.
     * If the athlete is not registered for the tournament, a message is printed indicating so.
     *
     * @param athlete    The athlete to withdraw.
     * @param tournament The tournament from which to withdraw the athlete.
     */
    @Override
    public void leaveTournament(Athlete athlete, Tournament tournament) {

        if(tournament.getAllParticipants().contains(athlete)){
            tournament.getAllParticipants().remove(athlete);
            tournament.getParticipants().remove(athlete);
            updateTournament(tournament);
        }
        else {
            System.out.println("The athlete is not registered for the tournament.");
        }
    }

    /**
     * Generates and saves brackets for a given tournament.
     *
     * @param tournament The Tournament object for which to generate and save brackets.
     */
    @Override
    @Transactional
    public void generateAndSaveBrackets(Tournament tournament) {
        List<List<Athlete>> bracketsData = generateBrackets(tournament);
        for (List<Athlete> bracketData : bracketsData) {
            Bracket bracket = new Bracket(tournament, bracketData);
            generateMatches(bracket);
            bracketRepository.save(bracket);
        }
    }

    /**
     * Generates brackets for a given tournament.
     *
     * @param tournament The Tournament object for which to generate brackets.
     * @return A list containing lists of athletes, each representing a bracket.
     */
    @Override
    @Transactional
    public List<List<Athlete>> generateBrackets(Tournament tournament) {
        List<Athlete> participants = tournament.getParticipants();
        // Randomizes the list if creating the initial brackets for the tournament
        if(!tournament.isInitialBracketsGenerated()){
            Collections.shuffle(participants);
            tournament.setInitialBracketsGenerated(true);
            tournamentRepository.save(tournament);
        }

        List<List<Athlete>> brackets = new ArrayList<>();
        Queue<Athlete> queue = new LinkedList<>(participants);

        while(!queue.isEmpty()){
            List<Athlete> bracket = new ArrayList<>();
            int bracketSize = Math.min(queue.size(), 32);
            for(int i = 0; i < bracketSize; i++){
                bracket.add(queue.poll());
            }
            brackets.add(bracket);
        }
        Integer currentRound = tournament.getCurrentRoundInfo();
        tournament.setCurrentRoundInfo(currentRound += 1);

        return brackets;
    }

    /**
     * Generates matches for a given bracket
     *
     * @param bracket The Bracket object for which to generate matches.
     */
    @Override
    @Transactional
    public void generateMatches(Bracket bracket){
        List<Athlete> athletes = bracket.getAthletes();
        Tournament tournament = bracket.getTournament();
        Set<Athlete> athleteWithBye = null;

        if(isByeNeeded(athletes)){
            // Added code to save athlete that had the bye when bracket was created
            Athlete athleteToRemove = athletes.remove(0);
            athleteWithBye = new HashSet<>();
            athleteWithBye.add(athleteToRemove);
            bracket.setByeAthleteID(athleteToRemove.getAthleteID());
        }

        for(int i = 0; i < athletes.size(); i += 2){
            if(i + 1 < athletes.size()){
                Athlete athlete1 = athletes.get(i);
                Athlete athlete2 = athletes.get(i + 1);
                Match match = new Match(bracket, tournament, athlete1, athlete2, tournament.getCurrentRoundInfo());
                matchRepository.save(match);
                bracket.getMatches().add(match);
            }
        }

        if(athleteWithBye != null){
            tournament.setAthleteWithBye(athleteWithBye);
            tournamentRepository.save(tournament);
        }

    }

    /**
     * Determines if a bye is necessary for bracket generation based on the number of athletes.
     * A bye is needed if there is an odd number of athletes.
     *
     * @param athletes The list of athletes participating in the tournament.
     * @return true if a bye is needed, false otherwise.
     */
    @Override
    public boolean isByeNeeded(List<Athlete> athletes) {
        return athletes.size() % 2 != 0;
    }

    /**
     * Updates the scores of a specific match.
     *
     * @param matchID The unique identifier of the match to update.
     * @param athlete1Score The new score to set for the first athlete in the match.
     * @param athlete2Score The new score to set for the second athlete in the match.
     * @return The unique identifier of the updated match.
     * @throws RuntimeException If no match is found with the given ID.
     */
    @Override
    public Long updateMatch(Long matchID, Integer athlete1Score, Integer athlete2Score){
        Match matchToUpdate = this.matchRepository.findById(matchID).orElseThrow(() -> new RuntimeException("Match not found with ID: " + matchID));

        matchToUpdate.setAthlete1Score(athlete1Score);
        matchToUpdate.setAthlete2Score(athlete2Score);

        this.matchRepository.save(matchToUpdate);

        return matchToUpdate.getMatchID();
    }

    /**
     * Records the result of a match, updating the winner's and loser's statistics.
     * The match winner is advanced, and the loser is removed from the tournament participants.
     * After updating, it checks if all matches are complete to generate the next round.
     *
     * @param matchId   The unique identifier of the match.
     * @param winnerId  The unique identifier of the winning athlete.
     * @param loserId   The unique identifier of the losing athlete.
     */
    @Override
    @Transactional
    public void recordMatchResult(Long matchId, Long winnerId, Long loserId) {

        Match match = matchRepository.findById(matchId).orElseThrow(() -> new NoSuchElementException("Match not found with ID: " + matchId));
        Athlete winner = athleteRepository.findById(winnerId).orElseThrow(() -> new NoSuchElementException("Winner athlete not found with ID: " + winnerId));
        Athlete loser = athleteRepository.findById(loserId).orElseThrow(() -> new NoSuchElementException("Loser athlete not found with ID: " + loserId));

        match.setWinner(winner);
        match.setLoser(loser);
        winner.setWins(winner.getWins() + 1);
        loser.setLosses(loser.getLosses() + 1);

        matchRepository.save(match);
        athleteRepository.save(winner);
        athleteRepository.save(loser);

        Tournament tournament = match.getTournament();
        tournament.getParticipants().remove(loser);
        tournamentRepository.save(tournament);

        checkAndGenerateNextRound(tournament);
    }

    /**
     * Checks if all matches in the current round are complete and generates the next round of brackets.
     * If an athlete was given a bye in the previous round, they are added back into the participants.
     *
     * @param tournament The tournament for which to check matches and generate the next round.
     */
    @Override
    public void checkAndGenerateNextRound(Tournament tournament) {
        // Check if all matches in the current round are complete
        boolean allMatchesComplete = tournament.getBrackets().stream()
                .flatMap(bracket -> bracket.getMatches().stream())
                .allMatch(match -> match.getWinner() != null);

        if (allMatchesComplete) {
            // If all matches are complete, generate new brackets with the remaining participants
            Set<Athlete> athleteWithBye = tournament.getAthleteWithBye();
            if(athleteWithBye != null){
                for(Athlete athlete : athleteWithBye){
                    tournament.getParticipants().add(athlete);
                }
                tournament.setAthleteWithBye(null);
            }
            generateAndSaveBrackets(tournament);
        }
    }

    /**
     * Calculates the total number of rounds required for a given tournament based on the number of participants.
     * The method accounts for multiple brackets and the possibility of byes in the tournament structure.
     *
     * @param tournament The Tournament object for which the total rounds need to be calculated.
     * @return The total number of rounds required for the tournament.
     */
    @Override
    public int calculateTotalRounds(Tournament tournament) {
        int rounds = 0;
        int maxBracketSize = 32;
        int totalParticipants = tournament.getAllParticipants().size();
        if (totalParticipants % 2 != 0) {
            totalParticipants++;
        }

        while (totalParticipants > 1) {
            int numBrackets = totalParticipants / maxBracketSize;
            if (totalParticipants % maxBracketSize != 0) {
                numBrackets++;
            }

            int participantsNextRound = 0;
            for (int i = 0; i < numBrackets; i++) {
                int bracketSize = maxBracketSize;
                if (i == numBrackets - 1 && totalParticipants % maxBracketSize != 0) {
                    bracketSize = totalParticipants % maxBracketSize;
                }
                participantsNextRound += (bracketSize + 1) / 2;
            }

            totalParticipants = participantsNextRound;
            rounds++;
        }

        return rounds;
    }

}
