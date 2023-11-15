package com.group13.tmae.model;

import javax.persistence.*;
import java.time.Instant;

/**
 * Entity representing a match between two athletes within a bracket of a tournament.
 */
@Entity
@Table(name = "matches")
public class Match {
    /**
     * The unique identifier for the match, automatically generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchID;

    /**
     * The bracket to which this match belongs.
     */
    @ManyToOne
    @JoinColumn(name = "bracket_id")
    private Bracket bracket;

    /**
     * The tournament in which this match takes place.
     */
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    /**
     * The first athlete participating in this match.
     */
    @ManyToOne
    @JoinColumn(name = "athlete1_id")
    private Athlete athlete1;

    /**
     * The second athlete participating in this match.
     */
    @ManyToOne
    @JoinColumn(name = "athlete2_id")
    private Athlete athlete2;

    /**
     * The round in which this match takes place.
     */
    private Integer roundNumber;

    /**
     * The score of the first athlete in this match.
     */
    private Integer athlete1Score;

    /**
     * The score of the second athlete in this match.
     */
    private Integer athlete2Score;

    /**
     * The athlete who won this match.
     */
    @ManyToOne
    private Athlete winner;

    /**
     * The athlete who lost this match.
     */
    @ManyToOne
    private Athlete loser;

    /**
     * Constructs a match with the specified details.
     *
     * @param bracket    The bracket to which this match belongs.
     * @param tournament The tournament in which this match takes place.
     * @param athlete1   The first athlete participating in this match.
     * @param athlete2   The second athlete participating in this match.
     */
    public Match(Bracket bracket, Tournament tournament, Athlete athlete1, Athlete athlete2) {
        this.bracket = bracket;
        this.tournament = tournament;
        this.athlete1 = athlete1;
        this.athlete2 = athlete2;
        this.athlete1Score = 0;
        this.athlete2Score = 0;
        this.winner = null;
        this.loser = null;
    }

    /**
     * Default constructor for creating a match without initializing its fields.
     */
    public Match() {
    }

    /**
     * Gets the unique identifier of this match.
     *
     * @return The unique identifier for the match.
     */
    public Long getMatchID() {
        return matchID;
    }

    /**
     * Retrieves the bracket associated with this match.
     *
     * @return The bracket entity to which this match is linked.
     */
    public Bracket getBracket() {
        return bracket;
    }

    /**
     * Sets the bracket associated with this match.
     *
     * @param bracket The bracket entity to link to this match.
     */
    public void setBracket(Bracket bracket) {
        this.bracket = bracket;
    }

    /**
     * Retrieves the tournament associated with this match.
     *
     * @return The tournament entity to which this match is linked.
     */
    public Tournament getTournament() {
        return tournament;
    }

    /**
     * Sets the tournament associated with this match.
     *
     * @param tournament The tournament entity to link to this match.
     */
    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    /**
     * Retrieves the first athlete participating in this match.
     *
     * @return The athlete entity representing the first participant in the match.
     */
    public Athlete getAthlete1() {
        return athlete1;
    }

    /**
     * Sets the first athlete to participate in this match.
     *
     * @param athlete1 The athlete entity to set as the first participant in the match.
     */
    public void setAthlete1(Athlete athlete1) {
        this.athlete1 = athlete1;
    }

    /**
     * Retrieves the second athlete participating in this match.
     *
     * @return The athlete entity representing the second participant in the match.
     */
    public Athlete getAthlete2() {
        return athlete2;
    }

    /**
     * Sets the second athlete to participate in this match.
     *
     * @param athlete2 The athlete entity to set as the second participant in the match.
     */
    public void setAthlete2(Athlete athlete2) {
        this.athlete2 = athlete2;
    }

    /**
     * Retrieves the score of the first athlete in this match.
     *
     * @return The score of the first athlete.
     */
    public Integer getAthlete1Score() {
        return athlete1Score;
    }

    /**
     * Sets the score for the first athlete in this match.
     *
     * @param athlete1Score The score to set for the first athlete.
     */
    public void setAthlete1Score(Integer athlete1Score) {
        this.athlete1Score = athlete1Score;
    }

    /**
     * Retrieves the score of the second athlete in this match.
     *
     * @return The score of the second athlete.
     */
    public Integer getAthlete2Score() {
        return athlete2Score;
    }

    /**
     * Sets the score for the second athlete in this match.
     *
     * @param athlete2Score The score to set for the second athlete.
     */
    public void setAthlete2Score(Integer athlete2Score) {
        this.athlete2Score = athlete2Score;
    }

    /**
     * Retrieves the winner of this match.
     *
     * @return The athlete entity that won the match.
     */
    public Athlete getWinner() {
        return winner;
    }

    /**
     * Sets the winner of this match.
     *
     * @param winner The athlete entity to set as the winner of the match.
     */
    public void setWinner(Athlete winner) {
        this.winner = winner;
    }

    /**
     * Retrieves the loser of this match.
     *
     * @return The athlete entity that lost the match.
     */
    public Athlete getLoser() {
        return loser;
    }

    /**
     * Sets the loser of this match.
     *
     * @param loser The athlete entity to set as the loser of the match.
     */
    public void setLoser(Athlete loser) {
        this.loser = loser;
    }

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }
}
