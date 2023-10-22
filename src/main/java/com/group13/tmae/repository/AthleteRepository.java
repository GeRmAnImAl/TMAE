package com.group13.tmae.repository;

import com.group13.tmae.model.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Athlete entities in the database.
 *
 * This interface extends the JpaRepository interface provided by Spring Data JPA
 * and specifies the type of entity (Athlete) and the type of its primary key (Long).
 */
@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {

    /**
     * Retrieves an Athlete entity by its unique identifier.
     *
     * Overrides the default findById method in JpaRepository to return an Optional
     * containing the Athlete with the specified ID, if it exists.
     *
     * @param id The unique identifier of the Athlete.
     * @return Optional containing the Athlete with the specified ID, or an empty Optional if not found.
     */
    @Override
    Optional<Athlete> findById(Long id);

    Optional<Athlete> findByUserName(String userName);

}

