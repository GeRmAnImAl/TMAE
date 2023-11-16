package com.group13.tmae;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.model.Tournament;
import com.group13.tmae.repository.TournamentRepository;
import com.group13.tmae.service.AthleteService;
import com.group13.tmae.service.Impl.AthleteServiceImpl;
import com.group13.tmae.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TmaeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmaeApplication.class, args);
    }
}
