package com.fantasytennis.fantasy_tennis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fantasytennis.fantasy_tennis.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByExternalApiId(Long externalApiId);

}
