package com.example.coltros.DTO.Request;

import com.example.coltros.enums.StatutColis;
import lombok.Data;
import jakarta.validation.constraints.Min;

@Data
public class ColisUpdateRequest {

    @Min(value = 0, message = "Le poids doit être positif")
    private Double poids;

    private String adresseDestination;

    private String instructionsManutention;

    private Double temperatureMin;

    private Double temperatureMax;

    private StatutColis statut;

    private String transporteurId;
}