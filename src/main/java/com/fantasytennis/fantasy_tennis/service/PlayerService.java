package com.fantasytennis.fantasy_tennis.service;

import com.fantasytennis.fantasy_tennis.model.Player;
import com.fantasytennis.fantasy_tennis.model.Team;
import com.fantasytennis.fantasy_tennis.model.User;
import com.fantasytennis.fantasy_tennis.repository.PlayerRepository;
import com.fantasytennis.fantasy_tennis.repository.TeamRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player addPlayer(Player player) {
        return playerRepository.save(player);
    }

    public void updatePlayerPoints(Long playerId, int newPoints) {
        Player player = playerRepository.findById(playerId)
            .orElseThrow(() -> new RuntimeException("Player not found"));

        Integer currentPoints = player.getPoints();
        if(currentPoints == null) {
            currentPoints = 0;
        }
        
        player.setPoints(currentPoints + newPoints);
        playerRepository.save(player);

        List<Team> teamsWithPlayer = teamRepository.findAll();

        for (Team team : teamsWithPlayer) {
            if (team.getPlayers().contains(player)) {
                
                Integer currentTeamPoints = team.getTotalPoints();
                if (currentTeamPoints == null) currentTeamPoints = 0;
                team.setTotalPoints(currentTeamPoints + newPoints);
                
                User user = team.getUser();
                Integer currentUserPoints = user.getTotalPoints();  
                if (currentUserPoints == null) currentUserPoints = 0;
                user.setTotalPoints(currentUserPoints + newPoints);
                
                teamRepository.save(team);
            }
        }
    }
}
