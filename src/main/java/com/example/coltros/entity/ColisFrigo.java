package com.example.coltros.entity;

import com.example.coltros.enums.TypeColis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "colis")
public class ColisFrigo extends Colis {

    @NotNull
    private Double temperatureMin;

    @NotNull
    private Double temperatureMax;

    public ColisFrigo() {
        super();
        this.setType(TypeColis.FRIGO);
    }

    public ColisFrigo(Double poids, String adresseDestination, Double temperatureMin, Double temperatureMax) {
        this();
        this.setPoids(poids);
        this.setAdresseDestination(adresseDestination);
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
    }

    public boolean temperaturesValides() {
        return temperatureMin != null && temperatureMax != null && temperatureMin < temperatureMax;
    }
}