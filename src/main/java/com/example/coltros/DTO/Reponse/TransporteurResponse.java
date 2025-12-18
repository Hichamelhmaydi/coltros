package com.example.coltros.DTO.Reponse;

import com.example.coltros.enums.Specialite;
import com.example.coltros.enums.Statut;
import lombok.Data;

@Data
public class TransporteurResponse {
    private String id;
    private String login;
    private boolean active;
    private Statut statut;
    private Specialite specialite;
    private Long nombreColisEnLivraison;
}