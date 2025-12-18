package com.example.coltros.entity;

import com.example.coltros.enums.TypeColis;
import com.example.coltros.enums.StatutColis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "colis")
public class Colis {

    @Id
    private String id;

    @NotNull
    @Field("type")
    private TypeColis type;

    @NotNull
    @Min(0)
    @Field("poids")
    private Double poids;

    @NotBlank
    @Field("adresse_destination")
    private String adresseDestination;

    @NotNull
    @Field("statuts")
    private StatutColis statuts = StatutColis.EN_ATTENTE;

    @Field("instructions_manutention")
    private String instructionsManutention;

    @Field("temperature_min")
    private Double temperatureMin;

    @Field("temperature_max")
    private Double temperatureMax;

    @Field("transporteur_id")
    private String transporteurId;

    @Field("transporteur_nom")
    private String transporteurNom;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;
}