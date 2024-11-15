package com.integrador.backend2.domain;

import lombok.Data;

import java.util.List;

@Data
public class PedidoDTO {
    private Integer idUser; // ID del usuario que realiza el pedido
    private List<Integer> idProducto; // ID del producto
    private String estado;
    private Integer cantidad;
    private String fechaPedido; // Manténlo como String para la conversión
    private String direccion;
    private String estadoEnvio;
}
