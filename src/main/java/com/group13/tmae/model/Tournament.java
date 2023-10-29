package com.group13.tmae.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tournaments")
public class  Tournament {
    /**
     * The unique identifier for the tournament. Generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tournamentID;

    @Column(name = "tournament_name")
    private String tournamentName;

    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Column(name = "location")
    private String location;

    @Column(name = "event_website_link")
    private String websiteLink;

    @Column(name = "max_num_participants")
    private Integer maxNumParticipants;

    @Column(name = "registration_deadline")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationDeadline;

    @ManyToMany
    @JoinTable(
            name = "tournament_participants",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "athlete_id")
    )
    private List<Athlete> participants;

    public Tournament(Long tournamentID, String tournamentName, LocalDate startDate, LocalDate endDate, String location,
                      String websiteLink, Integer maxNumParticipants, LocalDate registrationDeadline) {
        this.tournamentID = tournamentID;
        this.tournamentName = tournamentName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.websiteLink = websiteLink;
        this.maxNumParticipants = maxNumParticipants;
        this.registrationDeadline = registrationDeadline;
        this.participants = new ArrayList<>();
    }

    public Tournament() {

    }

    public Long getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(Long tournamentID) {
        this.tournamentID = tournamentID;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Athlete> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Athlete> participants) {
        this.participants = participants;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public Integer getMaxNumParticipants() {
        return maxNumParticipants;
    }

    public void setMaxNumParticipants(Integer maxNumParticipants) {
        this.maxNumParticipants = maxNumParticipants;
    }

    public LocalDate getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(LocalDate registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }
}
