package com.group13.tmae.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a bracket in a tournament. A bracket is a set of matches that
 * determines the progression of athletes through the stages of the tournament.
 */
@Entity
@Table(name = "brackets")
public class Bracket {
    /**
     * The unique identifier for the bracket. Generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bracketID;

    /**
     * The tournament to which this bracket belongs.
     */
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    /**
     * The list of athletes participating in this bracket.
     */
    @ManyToMany
    @JoinTable(name = "bracket_athletes", joinColumns = @JoinColumn(name = "bracket_id"), inverseJoinColumns = @JoinColumn(name = "athlete_id"))
    private List<Athlete> athletes;

    /**
     * The list of matches that make up this bracket.
     */
    @OneToMany(mappedBy = "bracket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Match> matches = new ArrayList<>();

    private Long byeAthleteID;

    /**
     * Constructs a new Bracket with a reference to its tournament and a list of participating athletes.
     *
     * @param tournament The tournament to which this bracket is associated.
     * @param athletes   The list of athletes participating in the bracket.
     */
    public Bracket(Tournament tournament, List<Athlete> athletes) {
        this.tournament = tournament;
        this.athletes = athletes;
    }

    /**
     * Default constructor for creating a bracket without initializing its fields.
     */
    public Bracket(){

    }

    /**
     * Gets the unique identifier for this bracket.
     *
     * @return The unique identifier for the bracket.
     */
    public Long getBracketID() {
        return bracketID;
    }

    /**
     * Gets the tournament associated with this bracket.
     *
     * @return The tournament to which the bracket belongs.
     */
    public Tournament getTournament() {
        return tournament;
    }

    /**
     * Sets the tournament associated with this bracket.
     *
     * @param tournament The tournament to associate with this bracket.
     */
    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    /**
     * Gets the list of athletes participating in this bracket.
     *
     * @return The list of athletes in the bracket.
     */
    public List<Athlete> getAthletes() {
        return athletes;
    }

    /**
     * Sets the list of athletes participating in this bracket.
     *
     * @param athletes The list of athletes to participate in the bracket.
     */
    public void setAthletes(List<Athlete> athletes) {
        this.athletes = athletes;
    }

    /**
     * Gets the list of matches that make up this bracket.
     *
     * @return The list of matches in the bracket.
     */
    public List<Match> getMatches() {
        return matches;
    }

    /**
     * Sets the list of matches for this bracket.
     *
     * @param matches The list of matches to set for the bracket.
     */
    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public Long getByeAthleteID() {
        return byeAthleteID;
    }

    public void setByeAthleteID(Long byeAthleteID) {
        this.byeAthleteID = byeAthleteID;
    }
}
