package com.example.coltros.DTO.Request;

import com.example.coltros.enums.TypeColis;
import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class ColisRequest {

    @NotNull(message = "Le type est obligatoire")
    private TypeColis type;

    @NotNull(message = "Le poids est obligatoire")
    @Min(value = 0, message = "Le poids doit être positif")
    private Double poids;

    @NotBlank(message = "L'adresse de destination est obligatoire")
    private String adresseDestination;

    private String instructionsManutention;

    private Double temperatureMin;

    private Double temperatureMax;

    public void validate() {
        if (TypeColis.FRAGILE.equals(type) &&
                (instructionsManutention == null || instructionsManutention.trim().isEmpty())) {
            throw new IllegalArgumentException("Les instructions de manutention sont obligatoires pour les colis fragiles");
        }

        if (TypeColis.FRIGO.equals(type)) {
            if (temperatureMin == null || temperatureMax == null) {
                throw new IllegalArgumentException("Les températures sont obligatoires pour les colis frigorifiques");
            }
            if (temperatureMin >= temperatureMax) {
                throw new IllegalArgumentException("La température minimale doit être inférieure à la température maximale");
            }
        }
    }
}