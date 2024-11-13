package com.integrador.backend2.repository;

import com.integrador.backend2.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    // Puedes agregar métodos personalizados aquí si es necesario
}
