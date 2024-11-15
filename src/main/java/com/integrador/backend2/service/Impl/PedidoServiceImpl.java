package com.integrador.backend2.service.Impl;

import com.integrador.backend2.model.Pedido;
import com.integrador.backend2.repository.PedidoRepository;
import com.integrador.backend2.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        // Solo actualiza los campos que vienen en el DTO o en el objeto Pedido
        if (pedido.getEstado() != null) {
            pedidoDB.setEstado(pedido.getEstado());
        }
        if (pedido.getCantidad() != null) {
            pedidoDB.setCantidad(pedido.getCantidad());
        }
        if (pedido.getDireccion() != null) {
            pedidoDB.setDireccion(pedido.getDireccion());
        }
        if (pedido.getFechaPedido() != null) {
            pedidoDB.setFechaPedido(pedido.getFechaPedido());
        }

        // Aquí no modificamos otros campos, solo los que se pasan
        return pedidoRepository.save(pedidoDB);
    }

    @Override
    public Pedido obtenerPedidoPorId(int id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Map<String, Object>> obtenerPedidosUltimosDias(int dias) {
        LocalDate fechaInicio = LocalDate.now().minusDays(dias);

        // Realizar la consulta a la base de datos para obtener los pedidos a partir de `fechaInicio`
        List<Object[]> resultados = pedidoRepository.obtenerPedidosUltimosDias(fechaInicio);

        // Convertir los resultados en una lista de mapas para una fácil serialización
        return resultados.stream()
                .map(obj -> Map.of("fecha", obj[0], "cantidad", obj[1]))
                .collect(Collectors.toList());
    }

    @Override
    public void setEstadoEnvio(int idPedido, String estadoEnvio) {
        Pedido pedido = pedidoRepository.findById(idPedido).orElse(null);
        if (pedido != null) {
            pedido.setEstadoEnvio(estadoEnvio);  // Solo actualiza el estado de envío
            pedidoRepository.save(pedido);  // Guardar el cambio
        }
    }

    @Override
    public void eliminarPedido(Integer id) {
        pedidoRepository.deleteById(id);
    }
}
