package com.group13.tmae.model;

import javax.persistence.*;
import org.springframework.lang.NonNull;
import java.io.Serializable;

/**
 * Represents an athlete participating in a sports event.
 *
 * This entity class is annotated with JPA annotations to define its mapping
 * to the underlying database table "athletes". It includes fields for athlete
 * details such as name, wins, losses, and ties.
 */
@Entity
@Table(name = "athletes")
public class Athlete implements Serializable {

    /**
     * The unique identifier for the athlete. Generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long athleteID;

    /**
     * The first name of the athlete.
     */
    @Column(name = "firstName")
    private String firstName;

    /**
     * The last name of the athlete.
     */
    @Column(name = "lastName")
    private String lastName;

    /**
     * The number of wins achieved by the athlete.
     */
    @Column(name = "wins")
    private Integer wins;

    /**
     * The number of losses experienced by the athlete.
     */
    @Column(name = "losses")
    private Integer losses;

    /**
     * The number of ties (draws) in which the athlete has participated.
     */
    @Column(name = "ties")
    private Integer ties;

    /**
     * Constructs an athlete with specified details.
     *
     * @param firstName The first name of the athlete.
     * @param lastName  The last name of the athlete.
     * @param wins      The number of wins achieved by the athlete.
     * @param losses    The number of losses experienced by the athlete.
     * @param ties      The number of ties (draws) in which the athlete has participated.
     */
    public Athlete(String firstName, String lastName, Integer wins, Integer losses, Integer ties) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    /**
     * Constructs an athlete with default values.
     */
    public Athlete() {
        this.firstName = "";
        this.lastName = "";
        this.wins = 0;
        this.losses = 0;
        this.ties = 0;
    }

    /**
     * Gets the unique identifier of the athlete.
     *
     * @return The athlete's unique identifier.
     */
    @NonNull
    public Long getAthleteID() {
        return athleteID;
    }

    /**
     * Sets the unique identifier of the athlete.
     *
     * @param athleteID The new unique identifier for the athlete.
     */
    public void setAthleteID(@NonNull Long athleteID) {
        this.athleteID = athleteID;
    }

    /**
     * Gets the first name of the athlete.
     *
     * @return The first name of the athlete.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the athlete.
     *
     * @param firstName The new first name for the athlete.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the athlete.
     *
     * @return The last name of the athlete.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the athlete.
     *
     * @param lastName The new last name for the athlete.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the number of wins achieved by the athlete.
     *
     * @return The number of wins.
     */
    public Integer getWins() {
        return wins;
    }

    /**
     * Sets the number of wins achieved by the athlete.
     *
     * @param wins The new number of wins for the athlete.
     */
    public void setWins(Integer wins) {
        this.wins = wins;
    }

    /**
     * Gets the number of losses experienced by the athlete.
     *
     * @return The number of losses.
     */
    public Integer getLosses() {
        return losses;
    }

    /**
     * Sets the number of losses experienced by the athlete.
     *
     * @param losses The new number of losses for the athlete.
     */
    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    /**
     * Gets the number of ties (draws) in which the athlete has participated.
     *
     * @return The number of ties.
     */
    public Integer getTies() {
        return ties;
    }

    /**
     * Sets the number of ties (draws) in which the athlete has participated.
     *
     * @param ties The new number of ties for the athlete.
     */
    public void setTies(Integer ties) {
        this.ties = ties;
    }
}

