package com.ysyesilyurt.EntityModel;

import com.ysyesilyurt.Enum.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "User")
@Table(name = "user")
public class UserEntityModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotNull
    @Column(name = "password")
    private String password; /* TODO: later on, keep the hashed values in db
                             TODO: (use https and compare the hashed values for authentication) */

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role; // we may also keep a list of roles and handle the mapping on db

    @Override
    public String toString() {
        return String.format("UserEntityModel with id %d and username %s", id, username);
    }
}
