package com.integrador.backend2.service;

import com.integrador.backend2.model.Pedido;

import java.util.List;

public interface PedidoService {
    Pedido crearPedido(Pedido pedido);
    List<Pedido> obtenerTodosLosPedidos();
    Pedido actualizarPedido(Pedido pedido, Integer id);
    void eliminarPedido(Integer id);
}
