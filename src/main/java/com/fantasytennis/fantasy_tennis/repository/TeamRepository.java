package com.fantasytennis.fantasy_tennis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fantasytennis.fantasy_tennis.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

}
