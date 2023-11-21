package com.group13.tmae.service;

import com.group13.tmae.model.Athlete;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service interface defining operations for managing athlete-related functionality.
 */
public interface AthleteService {

    /**
     * Creates a new athlete.
     *
     * @param athlete The Athlete object to be created.
     */
    void createAthlete(Athlete athlete);

    /**
     * Retrieves a list of all athletes.
     *
     * @return List of Athlete objects.
     */
    List<Athlete> getAllAthletes();

    /**
     * Retrieves an athlete by their unique identifier.
     *
     * @param id The unique identifier of the Athlete.
     * @return The Athlete object with the specified ID, or null if not found.
     */
    Athlete getAthleteById(Long id);

    /**
     * Updates an existing athlete.
     *
     * @param athlete The Athlete object to be updated.
     */
    void updateAthlete(Athlete athlete);

    /**
     * Updates the photo of an existing athlete.
     *
     * @param athlete The Athlete object whose photo is to be updated.
     */
    void updateAthletePhoto(Athlete athlete);

    /**
     * Deletes an athlete by their unique identifier.
     *
     * @param id The unique identifier of the Athlete to be deleted.
     */
    void deleteAthlete(Long id);

    /**
     * Retrieves a paginated list of athletes, sorted based on specified criteria.
     *
     * @param pageNo         The page number to be retrieved.
     * @param pageSize       The number of items per page.
     * @param sortField      The field by which the results should be sorted.
     * @param sortDirection  The sorting direction, either "asc" (ascending) or "desc" (descending).
     * @return A Page object containing the paginated list of Athlete objects.
     */
    Page<Athlete> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
