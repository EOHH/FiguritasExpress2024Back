package com.integrador.backend2.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;
    private String nombre;
    private Float precio;
    private String condicion;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fechapost")
    private Date fechaPost;

    private String imagen;

    @ManyToOne
    @JoinColumn(name = "idUser") // Asegúrate de que esta columna existe
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "idCategoria") // Debe coincidir con tu base de datos
    private Categoria idcategoria;

    public Producto() { }

    // Constructor con Long para idProducto
    // Constructor que acepta idProducto
    public Producto(Integer idProducto) {
        this.idProducto = idProducto;
    }
}
