package com.example.coltros.entity;

import com.example.coltros.enums.Role;
import com.example.coltros.enums.Specialite;
import com.example.coltros.enums.Statut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @NotBlank
    @Field("login")
    private String login;

    @NotBlank
    @Field("password")
    private String password;

    @NotNull
    @Field("role")
    private Role role;

    @Field("active")
    private boolean active = true;

    @Field("statut")
    private Statut statut;

    @Field("specialite")
    private Specialite specialite;

    public boolean isTransporteur() {
        return this.role == Role.TRANSPORTEUR;
    }

    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }
}