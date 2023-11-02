package com.group13.tmae.repository;

import com.group13.tmae.model.Bracket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 */
@Repository
public interface BracketRepository extends JpaRepository<Bracket, Long> {
    /**
     *
     * @param id
     * @return
     */
    @Override
    Optional<Bracket> findById(Long id);
}
