package com.integrador.backend2.service;

import com.integrador.backend2.model.Pedido;

import java.util.List;
import java.util.Map;

public interface PedidoService {
    Pedido crearPedido(Pedido pedido);
    List<Pedido> obtenerTodosLosPedidos();
    Pedido actualizarPedido(Pedido pedido, Integer id);
    void eliminarPedido(Integer id);
    Pedido obtenerPedidoPorId(int id);
    void setEstadoEnvio(int idPedido, String estadoEnvio);
    List<Map<String, Object>> obtenerPedidosUltimosDias(int dias);
}
