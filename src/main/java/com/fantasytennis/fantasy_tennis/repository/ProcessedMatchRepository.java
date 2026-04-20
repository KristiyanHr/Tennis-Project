package com.fantasytennis.fantasy_tennis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fantasytennis.fantasy_tennis.model.ProcessedMatch;

public interface ProcessedMatchRepository extends JpaRepository<ProcessedMatch, Long> {
    
}
