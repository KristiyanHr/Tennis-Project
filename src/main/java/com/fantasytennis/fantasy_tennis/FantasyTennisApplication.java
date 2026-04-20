package com.fantasytennis.fantasy_tennis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fantasytennis.fantasy_tennis.model.Player;
import com.fantasytennis.fantasy_tennis.model.Team;
import com.fantasytennis.fantasy_tennis.model.Tournament;
import com.fantasytennis.fantasy_tennis.model.User;
import com.fantasytennis.fantasy_tennis.repository.PlayerRepository;
import com.fantasytennis.fantasy_tennis.repository.TeamRepository;
import com.fantasytennis.fantasy_tennis.repository.TournamentRepository;
import com.fantasytennis.fantasy_tennis.repository.UserRepository;

@SpringBootApplication
@EnableScheduling
public class FantasyTennisApplication {

	public static void main(String[] args) {
		SpringApplication.run(FantasyTennisApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(
			PlayerRepository playerRepository,
			UserRepository userRepository,
			TournamentRepository tournamentRepository,
			TeamRepository teamRepository) {

		return args -> {

			if (playerRepository.count() == 0) {
				
				Player djokovic = playerRepository.save(new Player(null, "Novak Djokovic", 1, "Serbia", 25, 0, 14882L));
				Player alcaraz = playerRepository.save(new Player(null, "Carlos Alcaraz", 2, "Spain", 24, 0, 275923L));
				Player sinner = playerRepository.save(new Player(null, "Jannik Sinner", 3, "Italy", 22, 0, 206570L));
				playerRepository.save(new Player(null, "Iga Swiatek", 1, "Poland", 25, 0, 228272L));
				playerRepository.save(new Player(null, "Aryna Sabalenka", 2, "Belarus", 23, 0, 157754L));

				User me = userRepository.save(new User(null, "Kristiyan", "kriscohr@gmail.com", 1234));

				Tournament wimbledon = tournamentRepository.save(new Tournament(null, "Wimbledon", "Grass", 100, true));
				Tournament australianOpen = tournamentRepository
						.save(new Tournament(null, "Australian Open", "Hard", 100, true));
				Tournament frenchOpen = tournamentRepository.save(new Tournament(null, "French Open", "Clay", 100, true));
				Tournament usOpen = tournamentRepository.save(new Tournament(null, "US Open", "Hard", 100, true));

				Team myTeam = new Team();
				myTeam.setTeamName("Kristiyan`s Smashers");
				myTeam.setUser(me);
				myTeam.setTournament(wimbledon);
				myTeam.setBudgetRemaining(wimbledon.getBudgetCap());
				teamRepository.save(myTeam);

				System.out.println("Dummy data setup complete! Ready to play.");
			}
		};
	}

}
