package org.example.service;

import org.example.dto.AuthDto;
import org.example.dto.AuthResponseDTO;
import org.example.dto.UserDto;
import org.example.model.AppUser;
import org.example.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserService userService,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil) {

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponseDTO login(AuthDto authDto) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authDto.getEmail(),
                            authDto.getPassword()
                    )
            );
            final String token = jwtUtil.generateToken(authDto.getEmail());
            return new AuthResponseDTO("Login Successful", token);
        }catch (BadCredentialsException e) {

            return new AuthResponseDTO("Error: Invalid Credentials", null);
        }
    }

    @Override
    public AuthResponseDTO register(UserDto userDto) {

        if (userService.findUserByEmail(userDto.getEmail()).isPresent()) {
            return new AuthResponseDTO("Error: User Already Exists", null);
        }

            AppUser appUser = new AppUser();
            appUser.setEmail(userDto.getEmail());
            appUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
            appUser.setFullName(userDto.getEmail());

            AuthDto authDto = new AuthDto();
            authDto.setEmail(userDto.getEmail());
            authDto.setPassword(userDto.getPassword());

            userService.createUser(appUser);

        return login(authDto);
    }
}