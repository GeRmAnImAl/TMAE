package com.group13.tmae.service.athleteService;

import com.group13.tmae.model.Athlete;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AthleteService {

    Athlete createAthlete(Athlete athlete);

    List<Athlete> getAllAthletes();

    Athlete getEmployeeById(Long id);

    Athlete updateAthlete(Long id);

    void deleteAthlete(Long id);

    Page<Athlete> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

}
