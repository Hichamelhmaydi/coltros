package com.example.coltros.entity;

import com.example.coltros.enums.Role;
import com.example.coltros.enums.Specialite;
import com.example.coltros.enums.Statut;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "users")
@TypeAlias("transporteur")
public class Transporteur extends User {

    @NotNull
    @Field("statut")
    private Statut statut = Statut.DISPONIBLE;

    @NotNull
    @Field("specialite")
    private Specialite specialite;

    public Transporteur() {
        super(Role.TRANSPORTEUR);
    }

    public Transporteur(String login, String password, Specialite specialite) {
        this();
        this.setLogin(login);
        this.setPassword(password);
        this.specialite = specialite;
        this.statut = Statut.DISPONIBLE;
        this.setActive(true);
    }
}