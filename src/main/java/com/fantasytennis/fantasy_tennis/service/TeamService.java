package com.fantasytennis.fantasy_tennis.service;

import org.springframework.stereotype.Service;

import com.fantasytennis.fantasy_tennis.model.Player;
import com.fantasytennis.fantasy_tennis.model.Team;
import com.fantasytennis.fantasy_tennis.repository.PlayerRepository;
import com.fantasytennis.fantasy_tennis.repository.TeamRepository;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public Team addPlayerToTeam(Long teamId, Long playerId) {

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));

        if (team.getPlayers().contains(player)) {
            throw new RuntimeException("Player already in this team");
        }
        if (team.getBudgetRemaining() < player.getCost()) {
            throw new RuntimeException("Not enough budget to buy this player");
        }

        team.getPlayers().add(player);
        team.setBudgetRemaining(team.getBudgetRemaining() - player.getCost());

        return teamRepository.save(team);
    }

    public Team removePlayerFromTeam(Long teamId, Long playerId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));

        if (!team.getPlayers().contains(player)) {
            throw new RuntimeException("Player not found in this team");
        }

        team.getPlayers().remove(player);
        team.setBudgetRemaining(team.getBudgetRemaining() + player.getCost());

        return teamRepository.save(team);
    }

}
