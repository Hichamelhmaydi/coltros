package com.example.coltros.Controller;

import com.example.coltros.DTO.Request.LoginRequest;
import com.example.coltros.DTO.Reponse.LoginResponse;
import com.example.coltros.Service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.authenticate(loginRequest);
        return ResponseEntity.ok(response);
    }
}