package com.group13.tmae.service;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.model.Bracket;
import com.group13.tmae.model.Tournament;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service for managing tournaments.
 * Provides methods to create, retrieve, update, and delete tournaments,
 * as well as to manage tournament participation and brackets.
 */
public interface TournamentService {
    /**
     * Creates a new tournament.
     * @param tournament The Tournament object to be created.
     */
    void createTournament(Tournament tournament);

    /**
     * Retrieves a list of all tournaments.
     * @return List of Tournament objects.
     */
    List<Tournament> getAllTournaments();

    /**
     * Retrieves a tournament by their unique identifier.
     * @param id The unique identifier of the Tournament.
     * @return The Tournament object with the specified ID, or null if not found.
     */
    Tournament getTournamentById(Long id);

    /**
     * Updates an existing athlete.
     * @param tournament The Tournament object to be updated.
     */
    void updateTournament(Tournament tournament);

    /**
     * Deletes an Tournament by their unique identifier.
     * @param id The unique identifier of the Tournament to be deleted.
     */
    void deleteTournament(Long id);

    /**
     * Retrieves a paginated list of tournaments, sorted based on specified criteria.
     * @param pageNo         The page number to be retrieved.
     * @param pageSize       The number of items per page.
     * @param sortField      The field by which the results should be sorted.
     * @param sortDirection  The sorting direction, either "asc" (ascending) or "desc" (descending).
     * @return A Page object containing the paginated list of Tournament objects.
     */
    Page<Tournament> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    /**
     * Registers an athlete for a given tournament.
     * If the athlete is already registered, a message is printed indicating so.
     *
     * @param athlete    The athlete to register.
     * @param tournament The tournament for which to register the athlete.
     */
    void joinTournament(Athlete athlete, Tournament tournament);

    /**
     * Withdraws an athlete from a given tournament.
     * If the athlete is not registered for the tournament, a message is printed indicating so.
     *
     * @param athlete    The athlete to withdraw.
     * @param tournament The tournament from which to withdraw the athlete.
     */
    void leaveTournament(Athlete athlete, Tournament tournament);

    /**
     * Generates and saves brackets for a given tournament.
     *
     * @param tournament The Tournament object for which to generate and save brackets.
     */
    void generateAndSaveBrackets(Tournament tournament);

    /**
     * Generates brackets for a given tournament.
     *
     * @param tournament The Tournament object for which to generate brackets.
     * @return A list containing lists of athletes, each representing a bracket.
     */
    List<List<Athlete>> generateBrackets(Tournament tournament);

    /**
     * Generates matches for a given bracket
     *
     * @param bracket The Bracket object for which to generate matches.
     */
    void generateMatches(Bracket bracket);

    /**
     * Determines if a bye is necessary for bracket generation based on the number of athletes.
     * A bye is needed if there is an odd number of athletes.
     *
     * @param athletes The list of athletes participating in the tournament.
     * @return true if a bye is needed, false otherwise.
     */
    boolean isByeNeeded(List<Athlete> athletes);

    /**
     * Records the result of a match, updating the winner's and loser's statistics.
     * The match winner is advanced, and the loser is removed from the tournament participants.
     * After updating, it checks if all matches are complete to generate the next round.
     *
     * @param matchId   The unique identifier of the match.
     * @param winnerId  The unique identifier of the winning athlete.
     * @param loserId   The unique identifier of the losing athlete.
     */
    void recordMatchResult(Long matchId, Long winnerId, Long loserId);

    /**
     * Checks if all matches in the current round are complete and generates the next round of brackets.
     * If an athlete was given a bye in the previous round, they are added back into the participants.
     *
     * @param tournament The tournament for which to check matches and generate the next round.
     */
    void checkAndGenerateNextRound(Tournament tournament);
}
