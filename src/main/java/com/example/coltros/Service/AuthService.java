package com.example.coltros.Service;

import com.example.coltros.DTO.Request.LoginRequest;
import com.example.coltros.DTO.Reponse.LoginResponse;

public interface AuthService {
    LoginResponse authenticate(LoginRequest loginRequest);
}