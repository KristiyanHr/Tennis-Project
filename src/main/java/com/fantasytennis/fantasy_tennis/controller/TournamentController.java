package com.fantasytennis.fantasy_tennis.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fantasytennis.fantasy_tennis.model.Tournament;
import com.fantasytennis.fantasy_tennis.repository.TournamentRepository;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final TournamentRepository tournamentRepository;

    public TournamentController(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @GetMapping
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }
}
