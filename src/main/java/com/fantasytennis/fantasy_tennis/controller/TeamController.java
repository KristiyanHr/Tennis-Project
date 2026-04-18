package com.fantasytennis.fantasy_tennis.controller;

import org.springframework.web.bind.annotation.*;

import com.fantasytennis.fantasy_tennis.model.Team;
import com.fantasytennis.fantasy_tennis.service.TeamService;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/{teamId}/addPlayer/{playerId}")
    public Team addPlayerToTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        return teamService.addPlayerToTeam(teamId, playerId);
    }

    @DeleteMapping("/{teamId}/removePlayer/{playerId}")
    public Team removePlayerFromTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        return teamService.removePlayerFromTeam(teamId, playerId);
    }

}
