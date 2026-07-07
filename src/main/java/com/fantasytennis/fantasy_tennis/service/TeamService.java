package com.fantasytennis.fantasy_tennis.service;

import org.springframework.stereotype.Service;

import com.fantasytennis.fantasy_tennis.model.Player;
import com.fantasytennis.fantasy_tennis.model.Team;
import com.fantasytennis.fantasy_tennis.model.User;
import com.fantasytennis.fantasy_tennis.model.Tournament;
import com.fantasytennis.fantasy_tennis.repository.PlayerRepository;
import com.fantasytennis.fantasy_tennis.repository.TeamRepository;
import com.fantasytennis.fantasy_tennis.repository.UserRepository;
import com.fantasytennis.fantasy_tennis.repository.TournamentRepository;

import java.util.List;
import java.util.ArrayList;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final TournamentRepository tournamentRepository;

    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository, UserRepository userRepository, TournamentRepository tournamentRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public Team addPlayerToTeam(Long teamId, Long playerId) {

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));

        if (team.getPlayers().contains(player)) {
            throw new RuntimeException("Player already in this team");
        }

        if(team.getPlayers().size() >= 8){
            throw new RuntimeException("Your roster is full. Maximum 8 players are allowed!");
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

    public Team createTeam(String teamName, Long userId, Long tournamentId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow(() -> new RuntimeException("Tournament not found"));

        List<Team> existingTeams = teamRepository.findByUserId(userId);
        for (Team existing : existingTeams) {
            if (existing.getTournament().getId().equals(tournamentId)) {
                throw new RuntimeException("User already has a team for this tournament!");
            }
        }

        Team team = new Team();
        team.setTeamName(teamName);
        team.setUser(user);
        team.setTournament(tournament);
        team.setBudgetRemaining(tournament.getBudgetCap());
        team.setPlayers(new ArrayList<>());
        team.setTotalPoints(0);

        return teamRepository.save(team);
    }

    public List<Team> getTeamsByUserId(Long userId) {
        return teamRepository.findByUserId(userId);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
}

