package com.integrador.backend2.domain;

import lombok.Data;

@Data
public class TransaccionDTO {
    private Integer idUser; // ID del usuario que realiza la transacción
    private String estado;
    private Float precioTransaccion;
    private String metodoPago; // Nuevo campo para método de pago
    private String fechaVenta; // Manténlo como String para la conversión
}
