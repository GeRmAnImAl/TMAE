package com.group13.tmae.repository;

import com.group13.tmae.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Tournament} entities in the database.
 * It extends the JpaRepository interface provided by Spring Data JPA,
 * leveraging standard CRUD operations and enabling custom query methods for Tournament entities.
 */
@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    /**
     * Retrieves a Tournament entity by its unique identifier.
     * Overrides the default findById method in JpaRepository to return an Optional
     * containing the Tournament with the specified ID, if it exists.
     * @param id The unique identifier of the Tournament.
     * @return Optional containing the Tournament with the specified ID, or an empty Optional if not found.
     */
    @Override
    Optional<Tournament> findById(Long id);


}
