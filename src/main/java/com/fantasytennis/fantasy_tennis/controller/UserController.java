package com.fantasytennis.fantasy_tennis.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.fantasytennis.fantasy_tennis.model.User;
import com.fantasytennis.fantasy_tennis.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
