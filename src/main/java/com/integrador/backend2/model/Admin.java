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

    public Admin() {}

    public Admin(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
