package com.fantasytennis.fantasy_tennis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fantasytennis.fantasy_tennis.model.User;
import com.fantasytennis.fantasy_tennis.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {

        if (user.getUsername() == null || user.getEmail() == null) {
            throw new RuntimeException("Username and email are required");
        }

        if(user.getTotalPoints() == null){
            user.setTotalPoints(0);
        }

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getLeaderboard() {
        return userRepository.findAllByOrderByTotalPointsDesc();
    }

}
