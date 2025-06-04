package org.example.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private String message;

    public AuthResponseDTO(String message, String token) {
        this.token = token;
        this.message = message;
    }
}
