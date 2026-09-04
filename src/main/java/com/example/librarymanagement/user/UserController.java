package com.example.librarymanagement.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        return userService.signup(user);
    }
    @PostMapping("/login")
    public User login(
            @RequestParam String email,
            @RequestParam String password) {

        return userService.login(email, password);
    }
    @PostMapping("/admin-login")
    public User adminLogin(
            @RequestParam String email,
            @RequestParam String password) {

        return userService.adminLogin(email, password);
    }
}