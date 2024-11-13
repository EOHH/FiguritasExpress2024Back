package com.integrador.backend2.service.Impl;

import com.integrador.backend2.model.Pedido;
import com.integrador.backend2.repository.PedidoRepository;
import com.integrador.backend2.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public Pedido crearPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido actualizarPedido(Pedido pedido, Integer id) {
        Pedido pedidoDB = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El pedido con id: " + id + " no fue encontrado."));
        return pedidoRepository.save(pedido);
    }

    @Override
    public void eliminarPedido(Integer id) {
        pedidoRepository.deleteById(id);
    }
}
