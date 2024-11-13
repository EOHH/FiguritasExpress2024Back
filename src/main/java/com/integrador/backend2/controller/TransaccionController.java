package com.integrador.backend2.controller;

import com.integrador.backend2.domain.TransaccionDTO;
import com.integrador.backend2.model.Transaccion;
import com.integrador.backend2.service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api_int_2024/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    // Crear una nueva transacción
    @PostMapping("/store")
    public ResponseEntity<?> crearTransaccion(@RequestBody TransaccionDTO transaccionDTO) {
        try {
            TransaccionDTO nuevaTransaccion = transaccionService.crearTransaccion(transaccionDTO);
            return ResponseEntity.status(201).body(nuevaTransaccion); // Devuelve el nuevo DTO con estado 201
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("{\"error\": \"Error al crear la transacción: " + e.getMessage() + "\"}");
        }
    }

    // Obtener todas las transacciones
    @GetMapping("/getAll")
    public List<TransaccionDTO> obtenerTodasLasTransacciones() {
        return transaccionService.obtenerTodasLasTransacciones();
    }

    // Actualizar una transacción existente
    @PutMapping("/update/{id}")
    public ResponseEntity<TransaccionDTO> actualizarTransaccion(@RequestBody TransaccionDTO transaccionDTO, @PathVariable Integer id) {
        try {
            TransaccionDTO transaccionActualizada = transaccionService.actualizarTransaccion(transaccionDTO, id);
            return ResponseEntity.ok(transaccionActualizada); // Devuelve el DTO actualizado con estado 200
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(null); // Manejo de error
        }
    }

    // Eliminar una transacción
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarTransaccion(@PathVariable Integer id) {
        try {
            transaccionService.eliminarTransaccion(id);
            return ResponseEntity.noContent().build(); // Devuelve estado 204 sin contenido
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).build(); // Manejo de error
        }
    }
}
