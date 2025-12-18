package com.example.coltros.DTO.Request;

import com.example.coltros.enums.Specialite;
import com.example.coltros.enums.Statut;
import lombok.Data;

@Data
public class TransporteurUpdateRequest {

    private String password;

    private Boolean active;

    private Statut statut;

    private Specialite specialite;
}