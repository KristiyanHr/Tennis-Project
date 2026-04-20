package com.fantasytennis.fantasy_tennis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fantasytennis.fantasy_tennis.service.PlayerService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    private final PlayerService playerService;

    public AdminController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/test")
    public String test() {
        return "Admin Controller is working!";
    }

    @PostMapping("/score/{playerId}/{points}")
    public String recordMatchResult(@PathVariable Long playerId, @PathVariable int points) {
        playerService.updatePlayerPoints(playerId, points);

        return "Points updated succesfully for player and all realated teams!";
    }
}
