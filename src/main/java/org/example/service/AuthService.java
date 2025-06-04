package org.example.service;

import org.example.dto.AuthDto;
import org.example.dto.AuthResponseDTO;
import org.example.dto.UserDto;

public interface AuthService {
    AuthResponseDTO login(AuthDto authDto);
    AuthResponseDTO register(UserDto userDto);
}
