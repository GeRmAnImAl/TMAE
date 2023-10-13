package com.group13.tmae.service.athleteService;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.repository.AthleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;

@Service
public class AthleteServiceImpl implements AthleteService {

    @Autowired
    private AthleteRepository athleteRepository;

    @Override
    public Athlete createAthlete(Athlete athlete) {
        return this.athleteRepository.save(athlete);
    }

    @Override
    public List<Athlete> getAllAthletes() {
        return this.athleteRepository.findAll();
    }

    @Override
    public Athlete getAthleteById(Long id) {
        return this.athleteRepository.findById(id).orElse(null);
    }

    @Override
    public Athlete updateAthlete(Athlete athlete) {
        return this.athleteRepository.save(athlete);
    }

    @Override
    public void deleteAthlete(Long id) {
        this.athleteRepository.deleteById(id);
    }

    @Override
    public Page<Athlete> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        return this.athleteRepository.findAll(pageable);
    }
}
