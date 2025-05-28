package org.example.controller;

import org.example.dto.AuthDto;
import org.example.dto.UserDto;
import org.example.model.AppUser;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public AuthController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        if (userService.findUserByEmail(userDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email Already Exists");
        }
        AppUser appUser = new AppUser();
        appUser.setEmail(userDto.getEmail());
        appUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        appUser.setFullName(userDto.getFullName());

        userService.createUser(appUser);
        return ResponseEntity.ok("User created");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDto authDto) {
        userService.findUserByEmail(authDto.getEmail());
        return ResponseEntity.ok("Login Successfully");
    }
}
