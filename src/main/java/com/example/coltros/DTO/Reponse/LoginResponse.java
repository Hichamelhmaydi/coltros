package com.example.coltros.DTO.Reponse;

import com.example.coltros.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String login;
    private Role role;
}