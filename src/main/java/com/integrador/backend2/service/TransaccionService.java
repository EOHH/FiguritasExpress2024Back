package com.integrador.backend2.service;

import com.integrador.backend2.domain.TransaccionDTO;
import java.util.List;

public interface TransaccionService {
    TransaccionDTO crearTransaccion(TransaccionDTO transaccionDTO); // No throws ParseException
    List<TransaccionDTO> obtenerTodasLasTransacciones();
    TransaccionDTO actualizarTransaccion(TransaccionDTO transaccionDTO, Integer id); // No throws ParseException
    void eliminarTransaccion(Integer id);
}
