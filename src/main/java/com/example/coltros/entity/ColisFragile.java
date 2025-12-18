package com.example.coltros.entity;

import com.example.coltros.enums.TypeColis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "colis")
public class ColisFragile extends Colis {

    @NotBlank
    private String instructionsManutention;

    public ColisFragile() {
        super();
        this.setType(TypeColis.FRAGILE);
    }

    public ColisFragile(Double poids, String adresseDestination, String instructionsManutention) {
        this();
        this.setPoids(poids);
        this.setAdresseDestination(adresseDestination);
        this.instructionsManutention = instructionsManutention;
    }
}