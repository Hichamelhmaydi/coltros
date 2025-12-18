package com.example.coltros.entity;

import com.example.coltros.enums.TypeColis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Document(collection = "colis")
public class ColisStandard extends Colis {

    public ColisStandard() {
        super();
        this.setType(TypeColis.STANDARD);
    }

    public ColisStandard(Double poids, String adresseDestination) {
        this();
        this.setPoids(poids);
        this.setAdresseDestination(adresseDestination);
    }
}