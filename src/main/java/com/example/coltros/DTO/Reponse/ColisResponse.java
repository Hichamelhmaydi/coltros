package com.example.coltros.DTO.Reponse;

import com.example.coltros.enums.StatutColis;
import com.example.coltros.enums.TypeColis;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColisResponse {
    private String id;
    private TypeColis type;
    private Double poids;
    private String adresseDestination;
    private StatutColis statut;
    private String instructionsManutention;
    private Double temperatureMin;
    private Double temperatureMax;
    private String transporteurId;
    private String transporteurNom;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}