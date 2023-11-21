package com.group13.tmae.service.Impl;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.repository.AthleteRepository;
import com.group13.tmae.service.AthleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service implementation for managing athlete-related operations.
 * This service provides methods to create, retrieve, update, and delete athlete records,
 * as well as to facilitate pagination and sorting of athlete data.
 */
@Service
public class AthleteServiceImpl implements AthleteService {

    /**
     * Autowired AthleteRepository instance for database interaction.
     */
    @Autowired
    private AthleteRepository athleteRepository;

    /**
     * Encoder for hashing passwords to enhance security.
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Creates a new athlete in the database.
     *
     * @param athlete The Athlete object to be created.
     */
    @Override
    public void createAthlete(Athlete athlete) {
        athlete.setPassword(passwordEncoder.encode(athlete.getPassword()));
        this.athleteRepository.save(athlete);
    }

    /**
     * Retrieves a list of all athletes from the database.
     *
     * @return List of Athlete objects.
     */
    @Override
    public List<Athlete> getAllAthletes() {
        return this.athleteRepository.findAll();
    }

    /**
     * Retrieves an athlete by their unique identifier from the database.
     *
     * @param id The unique identifier of the Athlete.
     * @return The Athlete object with the specified ID, or null if not found.
     */
    @Override
    public Athlete getAthleteById(Long id) {
        return this.athleteRepository.findById(id).orElse(null);
    }

    /**
     * Updates an existing athlete in the database.
     *
     * @param athlete The Athlete object to be updated.
     */
    @Override
    public void updateAthlete(Athlete athlete) {
        athlete.setPassword(passwordEncoder.encode(athlete.getPassword()));
        this.athleteRepository.save(athlete);
    }

    /**
     * Updates the photo of an existing athlete.
     *
     * @param athlete The Athlete object whose photo is to be updated.
     */
    @Override
    public void updateAthletePhoto(Athlete athlete){
        this.athleteRepository.save(athlete);
    }

    /**
     * Deletes an athlete by their unique identifier from the database.
     *
     * @param id The unique identifier of the Athlete to be deleted.
     */
    @Override
    public void deleteAthlete(Long id) {
        this.athleteRepository.deleteById(id);
    }

    /**
     * Retrieves a paginated list of athletes from the database, sorted based on the specified criteria.
     *
     * @param pageNo         The page number to be retrieved.
     * @param pageSize       The number of items per page.
     * @param sortField      The field by which the results should be sorted.
     * @param sortDirection  The sorting direction, either "asc" (ascending) or "desc" (descending).
     * @return A Page object containing the paginated list of Athlete objects.
     */
    @Override
    public Page<Athlete> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        return this.athleteRepository.findAll(pageable);
    }
}
