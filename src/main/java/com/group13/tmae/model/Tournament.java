package com.group13.tmae.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents a tournament, including its basic details and the list of participants.
 * Also maintains a list of brackets associated with the tournament rounds.
 */
@Entity
@Table(name = "tournaments")
public class  Tournament {
    /**
     * The unique identifier for the tournament. Generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tournamentID;

    /**
     * The name of the tournament.
     */
    @Column(name = "tournament_name")
    private String tournamentName;

    /**
     * The start date of the tournament.
     */
    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /**
     * The end date of the tournament.
     */
    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * The location of the tournament.
     */
    @Column(name = "location")
    private String location;

    /**
     * The website link for the tournament.
     */
    @Column(name = "event_website_link")
    private String websiteLink;

    /**
     * The max number of participants allowed in the tournament.
     */
    @Column(name = "max_num_participants")
    private Integer maxNumParticipants;

    /**
     * The registration deadline for the tournament.
     */
    @Column(name = "registration_deadline")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationDeadline;

    /**
     * The athletes registered for the tournament.
     */
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "tournament_participants",
            joinColumns = @JoinColumn(name = "tournamentid"),
            inverseJoinColumns = @JoinColumn(name = "athleteid")
    )
    private List<Athlete> participants;

    /**
     * The brackets for the tournament.
     */
    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bracket> brackets = new ArrayList<>();

    /**
     * The list of athletes with byes for a given round in the tournament.
     */
    @Transient
    private Set<Athlete> athleteWithBye;

    /**
     * A flag indicating whether the initial set of brackets for the tournament has been generated.
     * It is set to false by default and should be updated to true after the first generation of brackets.
     */
    private boolean initialBracketsGenerated = false;

    /**
     * TODO
     */
    private Integer currentRoundInfo;

    /**
     * Creates a tournament with detailed information.
     *
     * @param tournamentID The unique identifier for the tournament.
     * @param tournamentName The name of the tournament.
     * @param startDate The starting date of the tournament.
     * @param endDate The ending date of the tournament.
     * @param location The location where the tournament is to be held.
     * @param websiteLink The link to the tournament's website.
     * @param maxNumParticipants The maximum number of participants allowed.
     * @param registrationDeadline The deadline for tournament registration.
     */
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
        this.athleteWithBye = null;
        this.currentRoundInfo = 0;
    }

    /**
     * Default constructor for creating a tournament instance without setting any initial values.
     */
    public Tournament() {

    }

    /**
     * Retrieves the unique identifier for the tournament.
     *
     * @return A Long value representing the unique ID of the tournament.
     */
    public Long getTournamentID() {
        return tournamentID;
    }

    /**
     * Retrieves the name of the tournament.
     *
     * @return A string representing the name of the tournament.
     */
    public String getTournamentName() {
        return tournamentName;
    }

    /**
     * Sets the name of the tournament.
     *
     * @param tournamentName The new name for the tournament.
     */
    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    /**
     * Retrieves the starting date of the tournament.
     *
     * @return A LocalDate representing the starting date of the tournament.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the starting date of the tournament.
     *
     * @param startDate The new starting date for the tournament.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Retrieves the ending date of the tournament.
     *
     * @return A LocalDate representing the ending date of the tournament.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the ending date of the tournament.
     *
     * @param endDate The new ending date for the tournament.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Retrieves the list of athletes participating in the tournament.
     *
     * @return A list of {@link Athlete} objects representing the participants of the tournament.
     */
    public List<Athlete> getParticipants() {
        return participants;
    }

    /**
     * Sets the list of athletes participating in the tournament.
     *
     * @param participants A list of {@link Athlete} objects to be set as the participants of the tournament.
     */
    public void setParticipants(List<Athlete> participants) {
        this.participants = participants;
    }

    /**
     * Retrieves the location where the tournament is held.
     *
     * @return A string representing the location of the tournament.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location where the tournament will be held.
     *
     * @param location The new location for the tournament.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Retrieves the website link for the tournament.
     *
     * @return A string representing the website link for the tournament.
     */
    public String getWebsiteLink() {
        return websiteLink;
    }

    /**
     * Sets the website link for the tournament.
     *
     * @param websiteLink The new website link for the tournament.
     */
    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    /**
     * Retrieves the maximum number of participants allowed in the tournament.
     *
     * @return An Integer representing the maximum number of participants.
     */
    public Integer getMaxNumParticipants() {
        return maxNumParticipants;
    }

    /**
     * Sets the maximum number of participants allowed in the tournament.
     *
     * @param maxNumParticipants The new maximum number of participants.
     */
    public void setMaxNumParticipants(Integer maxNumParticipants) {
        this.maxNumParticipants = maxNumParticipants;
    }

    /**
     * Retrieves the registration deadline for the tournament.
     *
     * @return A LocalDate representing the registration deadline.
     */
    public LocalDate getRegistrationDeadline() {
        return registrationDeadline;
    }

    /**
     * Sets the registration deadline for the tournament.
     *
     * @param registrationDeadline The new registration deadline for the tournament.
     */
    public void setRegistrationDeadline(LocalDate registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    /**
     * Retrieves the list of brackets associated with the tournament.
     *
     * @return A list of Bracket objects associated with the tournament.
     */
    public List<Bracket> getBrackets() {
        return brackets;
    }

    /**
     * Sets the list of brackets associated with the tournament.
     *
     * @param brackets The new list of Bracket objects.
     */
    public void setBrackets(List<Bracket> brackets) {
        this.brackets = brackets;
    }

    /**
     * Adds a bracket to the tournament's list of brackets and sets this tournament as the bracket's tournament.
     *
     * @param bracket The bracket to add to the tournament.
     */
    public void addBracket(Bracket bracket){
        this.brackets.add(bracket);
        bracket.setTournament(this);
    }

    /**
     * Removes a bracket from the tournament's list of brackets and clears the tournament reference in the bracket.
     *
     * @param bracket The bracket to remove from the tournament.
     */
    public void removeBracket(Bracket bracket){
        this.brackets.remove(bracket);
        bracket.setTournament(null);
    }

    /**
     * Retrieves the set of athletes who have received a bye in the tournament.
     *
     * @return A set of Athlete objects who have received a bye.
     */
    public Set<Athlete> getAthleteWithBye() {
        return athleteWithBye;
    }

    /**
     * Sets the set of athletes who have received a bye in the tournament.
     *
     * @param athleteWithBye The new set of Athlete objects who have received a bye.
     */
    public void setAthleteWithBye(Set<Athlete> athleteWithBye) {
        this.athleteWithBye = athleteWithBye;
    }

    /**
     * Checks if the initial brackets have already been generated for the tournament.
     *
     * @return true if the initial brackets have been generated, false otherwise.
     */
    public boolean isInitialBracketsGenerated() {
        return initialBracketsGenerated;
    }

    /**
     * Sets the flag indicating whether the initial brackets have been generated for the tournament.
     *
     * @param initialBracketsGenerated The flag to indicate if the initial brackets have been generated.
     */
    public void setInitialBracketsGenerated(boolean initialBracketsGenerated) {
        this.initialBracketsGenerated = initialBracketsGenerated;
    }

    public Integer getCurrentRoundInfo() {
        return currentRoundInfo;
    }

    public void setCurrentRoundInfo(Integer currentRoundInfo) {
        this.currentRoundInfo = currentRoundInfo;
    }
}
