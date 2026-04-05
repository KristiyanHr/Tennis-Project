package com.fantasytennis.fantasy_tennis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team{

    @Id
    @GeneratedValue(strategy = GeneratedType.IDENTIFY)
    private Long id;

    private String teamName;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @ManyToOne
    @JoinTable(
        name = "team_players",
        joinColumns = @JoinColumn(name = "team_id"),
        inverseJoinColumn = @JoinColumn(name = "player_id")
    )
    private List<Player> players;

    private int totalPoints = 0
    private int budgetRemaining = 100;
}