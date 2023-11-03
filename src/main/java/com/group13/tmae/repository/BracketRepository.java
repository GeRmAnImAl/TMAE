package com.group13.tmae.repository;

import com.group13.tmae.model.Bracket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link Bracket} entity. It extends JpaRepository to provide
 * standard CRUD operations and queries for Bracket entities.
 */
@Repository
public interface BracketRepository extends JpaRepository<Bracket, Long> {
    /**
     * Retrieves an {@link Optional} of a {@link Bracket} by its ID. The Optional is used
     * to indicate that the bracket may or may not exist in the database.
     *
     * @param id The ID of the bracket to retrieve.
     * @return An {@link Optional} containing the bracket if found, or empty otherwise.
     */
    @Override
    Optional<Bracket> findById(Long id);
}
