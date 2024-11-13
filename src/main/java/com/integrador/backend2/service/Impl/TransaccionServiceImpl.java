package com.integrador.backend2.service.Impl;

import com.integrador.backend2.domain.TransaccionDTO;
import com.integrador.backend2.model.Transaccion;
import com.integrador.backend2.model.User;
import com.integrador.backend2.repository.TransaccionRepository;
import com.integrador.backend2.service.TransaccionService;
import com.integrador.backend2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private UserService userService;

    // Crear una nueva transacción
    @Override
    public TransaccionDTO crearTransaccion(TransaccionDTO transaccionDTO) {
        try {
            // Cargar el usuario desde la base de datos usando el ID proporcionado en el DTO
            User usuario = userService.getUserById(transaccionDTO.getIdUser());

            // Verifica que el usuario fue encontrado
            if (usuario == null) {
                throw new RuntimeException("Usuario no encontrado para el ID: " + transaccionDTO.getIdUser());
            }

            // Crear y configurar la transacción
            Transaccion transaccion = new Transaccion();
            transaccion.setComprador(usuario); // Asigna el usuario persistente
            transaccion.setEstado(transaccionDTO.getEstado());
            transaccion.setPrecioTransaccion(transaccionDTO.getPrecioTransaccion());

            // Convertir y asignar la fecha de venta
            String fechaVentaStr = transaccionDTO.getFechaVenta();
            Date fechaVenta = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(fechaVentaStr).getTime());
            transaccion.setFechaVenta(fechaVenta);

            // Asignar el método de pago
            transaccion.setMetodoPago(transaccionDTO.getMetodoPago());

            // Guardar la transacción en la base de datos
            Transaccion nuevaTransaccion = transaccionRepository.save(transaccion);

            // Retornar el DTO de la nueva transacción
            return convertToDTO(nuevaTransaccion);

        } catch (ParseException e) {
            // Manejar el error de análisis de fecha
            throw new RuntimeException("Error al convertir la fecha de venta", e);
        }
    }

    // Obtener todas las transacciones
    @Override
    public List<TransaccionDTO> obtenerTodasLasTransacciones() {
        List<Transaccion> transacciones = transaccionRepository.findAll();
        return transacciones.stream().map(this::convertToDTO).toList();
    }

    // Actualizar una transacción existente
    @Override
    public TransaccionDTO actualizarTransaccion(TransaccionDTO transaccionDTO, Integer id) {
        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        // Cargar el usuario desde la base de datos
        User usuario = userService.getUserById(transaccionDTO.getIdUser());

        // Verifica que el usuario fue encontrado
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado para el ID: " + transaccionDTO.getIdUser());
        }
        System.out.println("Usuario cargado correctamente: " + usuario);

        // Actualizar los valores de la transacción
        transaccion.setComprador(usuario);
        transaccion.setEstado(transaccionDTO.getEstado());
        transaccion.setPrecioTransaccion(transaccionDTO.getPrecioTransaccion());

        // Convertir y asignar la fecha de venta
        try {
            String fechaVentaStr = transaccionDTO.getFechaVenta();
            Date fechaVenta = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(fechaVentaStr).getTime());
            transaccion.setFechaVenta(fechaVenta);
        } catch (ParseException e) {
            throw new RuntimeException("Error al convertir la fecha de venta", e);
        }

        // Asignar el método de pago
        transaccion.setMetodoPago(transaccionDTO.getMetodoPago());

        // Guardar la transacción actualizada en la base de datos
        Transaccion transaccionActualizada = transaccionRepository.save(transaccion);

        // Retornar el DTO de la transacción actualizada
        return convertToDTO(transaccionActualizada);
    }

    // Eliminar una transacción
    @Override
    public void eliminarTransaccion(Integer id) {
        transaccionRepository.deleteById(id);
    }

    // Método para convertir Transaccion a TransaccionDTO
    private TransaccionDTO convertToDTO(Transaccion transaccion) {
        TransaccionDTO dto = new TransaccionDTO();
        dto.setIdUser(transaccion.getComprador().getIdUser());
        dto.setEstado(transaccion.getEstado());
        dto.setPrecioTransaccion(transaccion.getPrecioTransaccion());
        dto.setFechaVenta(transaccion.getFechaVenta().toString());
        dto.setMetodoPago(transaccion.getMetodoPago());
        return dto;
    }
}
