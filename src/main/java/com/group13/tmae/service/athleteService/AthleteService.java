package com.group13.tmae.service.athleteService;

import com.group13.tmae.model.Athlete;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AthleteService {

    Athlete createAthlete(Athlete athlete);

    List<Athlete> getAllAthletes();

    Athlete getAthleteById(Long id);

    Athlete updateAthlete(Athlete athlete);

    void deleteAthlete(Long id);

    Page<Athlete> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

}
