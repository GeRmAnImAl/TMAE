package com.group13.tmae.service.Impl;

import com.group13.tmae.model.Tournament;
import com.group13.tmae.repository.TournamentRepository;
import com.group13.tmae.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentServiceImpl implements TournamentService {

    /**
     * Autowired TournamentRepository instance for database interaction.
     */
    @Autowired
    private TournamentRepository tournamentRepository;

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
    public void updateTournament(Tournament tournament) {
        this.tournamentRepository.save(tournament);
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
}
