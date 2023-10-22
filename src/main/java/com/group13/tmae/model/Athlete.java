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
    private Long athleteID;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

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

    @Column(name = "affiliation")
    private String affiliation;

    @Column(name = "age")
    private Integer age;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;


    /**
     * Constructs an athlete with specified details.
     *
     * @param firstName The first name of the athlete.
     * @param lastName  The last name of the athlete.
     */
    public Athlete(String userName, String password, String firstName, String lastName, String affiliation, Integer age,
                   Double weight, String address, String email, String phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.wins = 0;
        this.losses = 0;
        this.ties = 0;
        this.affiliation = affiliation;
        this.age = age;
        this.weight = weight;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructs an athlete with default values.
     */
    public Athlete() {
        this.userName = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.wins = 0;
        this.losses = 0;
        this.ties = 0;
        this.affiliation = "";
        this.age = 0;
        this.weight = 0.0;
        this.address = "";
        this.email = "";
        this.phoneNumber = "";
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

