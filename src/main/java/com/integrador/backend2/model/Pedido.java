package com.integrador.backend2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name="pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;

    @ManyToOne
    @JoinColumn(name = "idUser") // Este es el usuario que realiza el pedido
    private User usuario; // Relación con User

    @ManyToOne
    @JoinColumn(name = "idProducto") // Asegúrate de que esté correcto
    private Producto producto; // Relación con Producto

    private String estado;
    private Date fechaPedido;
    private Integer cantidad;
    private String direccion;
    private String estadoEnvio = "Pendiente de envío";
}
