package com.example.coltros.Service.impl;

import com.example.coltros.DTO.Request.LoginRequest;
import com.example.coltros.DTO.Reponse.LoginResponse;
import com.example.coltros.entity.User;
import com.example.coltros.Repository.UserRepository;
import com.example.coltros.security.JwtTokenProvider;
import com.example.coltros.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        User user = userRepository.findByLogin(loginRequest.getLogin())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return new LoginResponse(
                jwt,
                "Bearer",
                user.getId(),
                user.getLogin(),
                user.getRole()
        );
    }
}