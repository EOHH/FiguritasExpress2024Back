package com.integrador.backend2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAdmin;
    private String username;
    private String email;
    private String password;
    private String lastLogin;
    private String estado;
    private String profileImage;

    // Constructor con los campos necesarios
    public Admin(String username, String email, String password, String profileImage) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
    }

    // Constructor sin imagen de perfil
    public Admin(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImage = null;  // En caso de no proporcionar imagen, se establece en null
    }

    // Constructor por defecto
    public Admin() {}
}
