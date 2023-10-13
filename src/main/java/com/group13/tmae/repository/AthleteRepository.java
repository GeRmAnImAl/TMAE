package com.group13.tmae.repository;

import com.group13.tmae.model.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {

    @Override
    Optional<Athlete> findById(Long id);

}
