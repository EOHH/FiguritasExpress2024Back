package com.integrador.backend2.repository;

import com.integrador.backend2.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("SELECT p.fechaPedido, COUNT(p) FROM Pedido p WHERE p.fechaPedido >= :fechaInicio GROUP BY p.fechaPedido ORDER BY p.fechaPedido ASC")
    List<Object[]> obtenerPedidosUltimosDias(@Param("fechaInicio") LocalDate fechaInicio);
}
