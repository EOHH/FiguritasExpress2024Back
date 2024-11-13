package com.integrador.backend2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "transaccion")
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTransaccion;

    @ManyToOne
    @JoinColumn(name = "idUser") // Asegúrate de que sea idUser
    private User comprador;

    private Date fechaVenta;
    private Float precioTransaccion;
    private String estado;
    private String metodoPago;
}
