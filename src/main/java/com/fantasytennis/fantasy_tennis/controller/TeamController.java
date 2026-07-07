package com.fantasytennis.fantasy_tennis.controller;

import org.springframework.web.bind.annotation.*;

import com.fantasytennis.fantasy_tennis.model.Team;
import com.fantasytennis.fantasy_tennis.repository.TeamRepository;
import com.fantasytennis.fantasy_tennis.service.TeamService;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;
    private final TeamRepository teamRepository;

    public TeamController(TeamService teamService, TeamRepository teamRepository) {
        this.teamService = teamService;
        this.teamRepository = teamRepository;
    }

    @PostMapping("/{teamId}/addPlayer/{playerId}")
    public Team addPlayerToTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        return teamService.addPlayerToTeam(teamId, playerId);
    }

    @DeleteMapping("/{teamId}/removePlayer/{playerId}")
    public Team removePlayerFromTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        return teamService.removePlayerFromTeam(teamId, playerId);
    }

    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable Long id) {
        return teamRepository.findById(id).orElseThrow();
    }

    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/user/{userId}")
    public List<Team> getTeamsByUserId(@PathVariable Long userId) {
        return teamService.getTeamsByUserId(userId);
    }

    @PostMapping
    public Team createTeam(@RequestBody CreateTeamRequest request) {
        return teamService.createTeam(request.teamName(), request.userId(), request.tournamentId());
    }

    public record CreateTeamRequest(String teamName, Long userId, Long tournamentId) {}
}

