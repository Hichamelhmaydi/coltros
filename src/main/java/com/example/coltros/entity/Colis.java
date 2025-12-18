package com.example.coltros.entity;

import com.example.coltros.enums.StatutColis;
import com.example.coltros.enums.TypeColis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "colis")
@TypeAlias("colis")
public abstract class Colis {

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

    @Field("transporteur_id")
    private String transporteurId;

    @Field("transporteur_login")
    private String transporteurLogin;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;

    public Colis(TypeColis type) {
        this.type = type;
    }
}