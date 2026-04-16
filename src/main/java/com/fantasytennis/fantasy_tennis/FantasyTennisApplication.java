package com.fantasytennis.fantasy_tennis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fantasytennis.fantasy_tennis.model.Player;
import com.fantasytennis.fantasy_tennis.repository.PlayerRepository;

@SpringBootApplication
public class FantasyTennisApplication {

	public static void main(String[] args) {
		SpringApplication.run(FantasyTennisApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(PlayerRepository playerRepository) {
		return args -> {
			playerRepository.save(new Player(null, "Novak Djokovic", 1, "Serbia", 25));
			playerRepository.save(new Player(null, "Carlos Alcaraz", 2, "Spain", 24));
			playerRepository.save(new Player(null, "Jannik Sinner", 3, "Italy", 22));
			playerRepository.save(new Player(null, "Iga Swiatek", 1, "Poland", 25));
			playerRepository.save(new Player(null, "Aryna Sabalenka", 2, "Belarus", 23));

			System.out.println("Dummy players added to database!");
		};
	}

}
