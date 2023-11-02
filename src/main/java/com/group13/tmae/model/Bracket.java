package com.group13.tmae.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "brackets")
public class Bracket {
    /**
     * The unique identifier for the bracket. Generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bracketID;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToMany
    @JoinTable(name = "bracket_athletes", joinColumns = @JoinColumn(name = "bracket_id"), inverseJoinColumns = @JoinColumn(name = "athlete_id"))
    private List<Athlete> athletes;

    public Bracket(Tournament tournament, List<Athlete> athletes) {
        this.tournament = tournament;
        this.athletes = athletes;
    }

    public Long getBracketID() {
        return bracketID;
    }

    public void setBracketID(Long bracketID) {
        this.bracketID = bracketID;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public List<Athlete> getAthletes() {
        return athletes;
    }

    public void setAthletes(List<Athlete> athletes) {
        this.athletes = athletes;
    }
}
