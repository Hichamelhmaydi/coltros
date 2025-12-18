package com.example.coltros.entity;

import com.example.coltros.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "users")
@TypeAlias("admin")
public class Admin extends User {

    public Admin() {
        super(Role.ADMIN);
    }

    public Admin(String login, String password) {
        this();
        this.setLogin(login);
        this.setPassword(password);
        this.setActive(true);
    }
}