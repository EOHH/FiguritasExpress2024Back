package com.integrador.backend2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idUser;

    private String username; // Almacena el nombre del usuario de Google
    private String email;
    private String password;
    private String profileImage;
    private String lastLogin;
    private String estado;
    private String isAdmin;

    // Campo adicional para la autenticación con Google
    private String googleId; // ID único de Google para identificar al usuario

    public User() {}

    public User(Integer idUser) {
        this.idUser = idUser;
    }

    public User(String username, String email, String password, String profileImage) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
    }
}
