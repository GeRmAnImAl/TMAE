package com.group13.tmae.model;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Represents an athlete participating in a sports event.
 * <p>
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

    /**
     * The username of the athlete.
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * The password of the athlete.
     */
    @Column(name = "password")
    private String password;

    /**
     * The first name of the athlete.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * The last name of the athlete.
     */
    @Column(name = "last_name")
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
     * The athlete's affiliation, if any.
     */
    @Column(name = "affiliation")
    private String affiliation;

    /**
     * The birthdate of the athlete.
     */
    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    /**
     * The weight of the athlete.
     */
    @Column(name = "weight")
    private Double weight;

    /**
     * The address of the athlete.
     */
    @Column(name = "address")
    private String address;

    /**
     * The email address of the athlete.
     */
    @Column(name = "email")
    private String email;

    /**
     * The phone number of the athlete.
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * Photo data for the athlete in binary format.
     */
    @Column(name = "photo_data")
    private byte[] photoData;

    /**
     * The file of the athlete's photo to be uploaded.
     */
    @Transient
    private MultipartFile photoFile;

    /**
     * The content type of the athlete's photo.
     */
    @Column(name = "photo_content_type")
    private String photoContentType;

    /**
     * Tournaments the athlete is registered for.
     */
    @ManyToMany(mappedBy = "participants")
    private List<Tournament> tournaments;

    /**
     * Constructs an athlete with specified details.
     *
     * @param firstName The first name of the athlete.
     * @param lastName  The last name of the athlete.
     */
    public Athlete(String userName, String password, String firstName, String lastName, String affiliation, LocalDate birthDate,
                   Double weight, String address, String email, String phoneNumber, byte[] photoData) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.wins = 0;
        this.losses = 0;
        this.ties = 0;
        this.affiliation = affiliation;
        this.birthDate = birthDate;
        this.weight = weight;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.photoData = photoData;
        this.tournaments = new ArrayList<>();
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
        this.birthDate = LocalDate.now();
        this.weight = 0.0;
        this.address = "";
        this.email = "";
        this.phoneNumber = "";
        this.tournaments = new ArrayList<>();
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
     * Retrieves the username of the athlete.
     *
     * @return the athlete's username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the athlete's username.
     *
     * @param userName the new username for the athlete.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Retrieves the athlete's password.
     *
     * @return the athlete's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the athlete's password.
     *
     * @param password the new password for the athlete.
     */
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

    /**
     * Retrieves the athlete's affiliation.
     *
     * @return the athlete's affiliation.
     */
    public String getAffiliation() {
        return affiliation;
    }

    /**
     * Sets the athlete's affiliation.
     *
     * @param affiliation the new affiliation for the athlete.
     */
    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    /**
     * Retrieves the athlete's age.
     *
     * @return the athlete's age.
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the athlete's age.
     *
     * @param birthDate the new age for the athlete.
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Retrieves the athlete's weight.
     *
     * @return the athlete's weight.
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * Sets the athlete's weight.
     *
     * @param weight the new weight for the athlete.
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     * Retrieves the athlete's address.
     *
     * @return the athlete's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the athlete's address.
     *
     * @param address the new address for the athlete.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retrieves the athlete's email.
     *
     * @return the athlete's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the athlete's email.
     *
     * @param email the new email address for the athlete.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the athlete's phone number.
     *
     * @return the athlete's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the athlete's phone number.
     *
     * @param phoneNumber the new phone number for the athlete.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retrieves the raw photo data of the athlete.
     *
     * @return A byte array containing the binary data of the athlete's photo.
     */
    public byte[] getPhotoData() {
        return photoData;
    }

    /**
     * Sets the raw photo data of the athlete.
     *
     * @param photoData A byte array representing the binary data of the athlete's photo to be set.
     */
    public void setPhotoData(byte[] photoData) {
        this.photoData = photoData;
    }

    /**
     * Converts the athlete's photo data to a Base64-encoded string.
     *
     * @return the Base64-encoded string of the athlete's photo data.
     */
    public String getPhotoDataAsBase64() {
        return Base64.getEncoder().encodeToString(this.photoData);
    }

    /**
     * Retrieves the athlete's photo file.
     *
     * @return the athlete's photo file.
     */
    public MultipartFile getPhotoFile() {
        return photoFile;
    }

    /**
     * Sets the athlete's photo file.
     *
     * @param photoFile the new photo file for the athlete.
     */
    public void setPhotoFile(MultipartFile photoFile) {
        this.photoFile = photoFile;
    }

    /**
     * Retrieves the content type of the athlete's photo.
     *
     * @return the content type of the athlete's photo.
     */
    public String getPhotoContentType() {
        return photoContentType;
    }

    /**
     * Sets the content type of the athlete's photo.
     *
     * @param photoContentType the new content type for the athlete's photo.
     */
    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    /**
     * Retrieves the list of tournaments the athlete is registered for.
     *
     * @return the list of tournaments the athlete is participating in.
     */
    public List<Tournament> getTournaments() {
        return tournaments;
    }

    /**
     * Sets the list of tournaments the athlete is registered for.
     *
     * @param tournaments the new list of tournaments for the athlete.
     */
    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }
}

