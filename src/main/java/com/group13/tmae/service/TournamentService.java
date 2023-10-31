package com.group13.tmae.service;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.model.Tournament;
import org.springframework.data.domain.Page;

import java.util.List;

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

    void joinTournament(Athlete athlete, Tournament tournament);
}
