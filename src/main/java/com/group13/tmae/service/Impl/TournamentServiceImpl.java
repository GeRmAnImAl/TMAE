package com.group13.tmae.service.Impl;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.model.Bracket;
import com.group13.tmae.model.Tournament;
import com.group13.tmae.repository.BracketRepository;
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
     *
     * @param athlete
     * @param tournament
     */
    @Override
    public void joinTournament(Athlete athlete, Tournament tournament) {

        if(!tournament.getParticipants().contains(athlete)){
            tournament.getParticipants().add(athlete);
            updateTournament(tournament);
        }
        else {
            System.out.println("Already Joined That Tournament");
        }
    }

    /**
     *
     * @param athlete
     * @param tournament
     */
    @Override
    public void leaveTournament(Athlete athlete, Tournament tournament) {

        if(tournament.getParticipants().contains(athlete)){
            tournament.getParticipants().remove(athlete);
            updateTournament(tournament);
        }
        else {
            System.out.println("The athlete is not registered for the tournament.");
        }
    }

    @Transactional
    public void generateAndSaveBrackets(Tournament tournament) {
        List<List<Athlete>> bracketsData = generateBrackets(tournament);
        for (List<Athlete> bracketData : bracketsData) {
            Bracket bracket = new Bracket(tournament, bracketData);
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
        // Randomizes the list
        Collections.shuffle(participants);

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


        return brackets;
    }
}
