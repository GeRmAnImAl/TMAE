package com.group13.tmae.model;

import javax.persistence.*;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchID;

    @ManyToOne
    @JoinColumn(name = "bracket_id")
    private Bracket bracket;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "athlete1_id")
    private Athlete athlete1;

    @ManyToOne
    @JoinColumn(name = "athlete2_id")
    private Athlete athlete2;

    private Integer athlete1Score;

    private Integer athlete2Score;

    @ManyToOne
    private Athlete winner;

    @ManyToOne
    private Athlete loser;

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

    public Long getMatchID() {
        return matchID;
    }

    public Bracket getBracket() {
        return bracket;
    }

    public void setBracket(Bracket bracket) {
        this.bracket = bracket;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Athlete getAthlete1() {
        return athlete1;
    }

    public void setAthlete1(Athlete athlete1) {
        this.athlete1 = athlete1;
    }

    public Athlete getAthlete2() {
        return athlete2;
    }

    public void setAthlete2(Athlete athlete2) {
        this.athlete2 = athlete2;
    }

    public Integer getAthlete1Score() {
        return athlete1Score;
    }

    public void setAthlete1Score(Integer athlete1Score) {
        this.athlete1Score = athlete1Score;
    }

    public Integer getAthlete2Score() {
        return athlete2Score;
    }

    public void setAthlete2Score(Integer athlete2Score) {
        this.athlete2Score = athlete2Score;
    }

    public Athlete getWinner() {
        return winner;
    }

    public void setWinner(Athlete winner) {
        this.winner = winner;
    }

    public Athlete getLoser() {
        return loser;
    }

    public void setLoser(Athlete loser) {
        this.loser = loser;
    }
}
