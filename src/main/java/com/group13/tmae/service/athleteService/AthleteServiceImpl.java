package com.group13.tmae.service.athleteService;

import com.group13.tmae.model.Athlete;
import org.springframework.data.domain.Page;

import java.util.List;

public class AthleteServiceImpl implements  AthleteService{

    @Override
    public Athlete createAthlete(Athlete athlete) {
        return null;
    }

    @Override
    public List<Athlete> getAllAthletes() {
        return null;
    }

    @Override
    public Athlete getEmployeeById(Long id) {
        return null;
    }

    @Override
    public Athlete updateAthlete(Long id) {
        return null;
    }

    @Override
    public void deleteAthlete(Long id) {

    }

    @Override
    public Page<Athlete> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        return null;
    }
}
