package com.example.coltros.entity;

import com.example.coltros.enums.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Document(collection = "users")
@TypeAlias("user")
public abstract class User {

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

    public User(Role role) {
        this.role = role;
    }
}