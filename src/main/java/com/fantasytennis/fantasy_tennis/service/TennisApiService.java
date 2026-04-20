package com.fantasytennis.fantasy_tennis.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import com.fantasytennis.fantasy_tennis.repository.PlayerRepository;

@Service
public class TennisApiService {

    private final RestTemplate restTemplate;
    private final PlayerService playerService;  
    private final PlayerRepository playerRepository;

    @Value("${tennis.api.key}")
    private String apiKey;

    public TennisApiService(RestTemplate restTemplate, PlayerService playerService, PlayerRepository playerRepository) {
        this.restTemplate = restTemplate;
        this.playerService = playerService;
        this.playerRepository = playerRepository;
    }

    @Scheduled(fixedRate = 1800000)
    public void fetchResults() {
        System.out.println("Checking for new results...");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", apiKey);
        headers.set("X-RapidAPI-Host", "tennisapi1.p.rapidapi.com");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        System.out.println("API Call executed successfully.");

    }

    

    
}
