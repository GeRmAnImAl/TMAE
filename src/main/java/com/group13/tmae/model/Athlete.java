package com.group13.tmae.model;

import javax.persistence.*;

import org.springframework.lang.NonNull;

import java.io.Serializable;

@Entity
@Table(name = "athletes")
public class Athlete implements Serializable {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long athleteID;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "wins")
    private Integer wins;

    @Column(name = "losses")
    private Integer losses;

    @Column(name = "ties")
    private Integer ties;

    @Column(name = "age")
    private Integer age;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "address")
    private String address;

    @Column(name = "affiliation")
    private String affiliation;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    public Athlete(String userName, String password, String firstName, String lastName, Integer wins, Integer losses,
                   Integer ties, Integer age, Integer weight, String address, String affiliation, String email,
                   String phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
        this.age = age;
        this.weight = weight;
        this.address = address;
        this.affiliation = affiliation;
        this.email = email;
        this.phoneNumber = phoneNumber;

    }

    public Athlete() {
        this.userName = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.wins = 0;
        this.losses = 0;
        this.ties = 0;
        this.age = 0;
        this.weight = 0;
        this.address = "";
        this.affiliation = "";
        this.email = "";
        this.phoneNumber = "";
    }

    @NonNull
    public Long getAthleteID() {
        return athleteID;
    }

    public void setAthleteID(@NonNull Long athleteID) {
        this.athleteID = athleteID;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Integer getTies() {
        return ties;
    }

    public void setTies(Integer ties) {
        this.ties = ties;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
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
