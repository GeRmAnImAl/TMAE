package com.group13.tmae.repository;

import com.group13.tmae.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    /**
     *
     * @param id
     * @return
     */
    @Override
    Optional<Match> findById(Long id);
}
