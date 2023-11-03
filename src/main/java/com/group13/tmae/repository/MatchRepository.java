package com.group13.tmae.repository;

import com.group13.tmae.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link Match} entity. It extends JpaRepository to provide
 * standard database operation methods for Match entities.
 */
@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    /**
     * Retrieves an {@link Optional} of a {@link Match} by its ID. The Optional container
     * indicates that the match may or may not exist.
     *
     * @param id The ID of the match to retrieve.
     * @return An {@link Optional} containing the match if found, or empty otherwise.
     */
    @Override
    Optional<Match> findById(Long id);
}
