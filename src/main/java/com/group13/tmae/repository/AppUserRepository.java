package com.group13.tmae.repository;

import com.group13.tmae.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    /**
     * Retrieves an AppUser by a specific username.
     * @param id Long representing the AppUser in the database.
     * @return an AppUser Object.
     */
    Optional<AppUser> findById(Long id);
}
