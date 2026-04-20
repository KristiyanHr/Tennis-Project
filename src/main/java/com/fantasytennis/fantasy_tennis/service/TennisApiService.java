package com.fantasytennis.fantasy_tennis.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.fantasytennis.fantasy_tennis.model.Player;
import com.fantasytennis.fantasy_tennis.repository.PlayerRepository;
import com.fantasytennis.fantasy_tennis.repository.ProcessedMatchRepository;
import com.fantasytennis.fantasy_tennis.model.ProcessedMatch;
import java.time.LocalDateTime;


import tools.jackson.databind.JsonNode;

@Service
public class TennisApiService {

    private final RestTemplate restTemplate;
    private final PlayerService playerService;  
    private final PlayerRepository playerRepository;
    private final ProcessedMatchRepository processedMatchRepository;

    @Value("${tennis.api.key}")
    private String apiKey;

    public TennisApiService(RestTemplate restTemplate, PlayerService playerService, PlayerRepository playerRepository, ProcessedMatchRepository processedMatchRepository) {
        this.restTemplate = restTemplate;
        this.playerService = playerService;
        this.playerRepository = playerRepository;
        this.processedMatchRepository = processedMatchRepository;
    }

    @Scheduled(fixedRate = 1800000)
    public void fetchResults() {
        System.out.println("Checking for new results...");

        String url = "https://tennisapi1.p.rapidapi.com/api/tennis/events/20/4/2026";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", apiKey);
        headers.set("X-RapidAPI-Host", "tennisapi1.p.rapidapi.com");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
         try {
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
            JsonNode events = response.getBody().get("events");

            if (events != null && events.isArray()) {
                for (JsonNode event : events) {
                    Long apiMatchId = event.path("id").asLong();

                    // 1. Skip if already processed
                    if (processedMatchRepository.existsById(apiMatchId)) {
                        continue; 
                    }

                    String status = event.path("status").path("type").asText();
                    if ("finished".equalsIgnoreCase(status)) {
                        
                        // 🛑 MOVE THIS HERE: Save the match ID immediately so we never check it again!
                        processedMatchRepository.save(new ProcessedMatch(apiMatchId, LocalDateTime.now()));

                        int winnerCode = event.path("winnerCode").asInt();
                        Long winnerId = (winnerCode == 1) 
                            ? event.path("homeTeam").path("id").asLong() 
                            : event.path("awayTeam").path("id").asLong();

                        Optional<Player> playerOpt = playerRepository.findByExternalApiId(winnerId);
                        
                        if (playerOpt.isPresent()) {
                            Player player = playerOpt.get();
                            System.out.println("Fantasy Match Winner found! Giving points to: " + player.getName());
                            playerService.updatePlayerPoints(player.getId(), 50);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error calling Tennis API: " + e.getMessage());
        }
    }
}
