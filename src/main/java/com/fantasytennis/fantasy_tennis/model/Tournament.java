package com.fantasytennis.fantasy_tennis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tournaments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tournament{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTIFY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String surface;

    private int budgetCap = 100;

    private boolean isActive = true;
}