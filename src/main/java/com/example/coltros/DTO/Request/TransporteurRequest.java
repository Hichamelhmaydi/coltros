package com.example.coltros.DTO.Request;

import com.example.coltros.enums.Specialite;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class TransporteurRequest {

    @NotBlank(message = "Le login est obligatoire")
    private String login;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    @NotNull(message = "La spécialité est obligatoire")
    private Specialite specialite;
}