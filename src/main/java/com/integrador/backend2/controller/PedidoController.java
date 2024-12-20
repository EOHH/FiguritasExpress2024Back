package com.integrador.backend2.controller;

import com.integrador.backend2.domain.PedidoDTO;
import com.integrador.backend2.model.Pedido;
import com.integrador.backend2.model.Producto;
import com.integrador.backend2.model.User;
import com.integrador.backend2.service.PedidoService;
import com.integrador.backend2.service.ProductoService;
import com.integrador.backend2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date; // Importamos java.sql.Date
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api_int_2024/orders")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private UserService userService; // Servicio para obtener los detalles del usuario

    @Autowired
    private ProductoService productoService; // Servicio para obtener los detalles del producto

    @Autowired
    private WebSocketController webSocketController;


    @PostMapping("/store")
    public ResponseEntity<?> crearPedido(@RequestBody PedidoDTO pedidoDTO) {
        try {
            // Obtener detalles completos del usuario
            User usuario = userService.getUserById(pedidoDTO.getIdUser());

            if (usuario == null) {
                return ResponseEntity.status(404).body("Usuario no encontrado");
            }

            List<Pedido> pedidos = new ArrayList<>();

            for (Integer idProducto : pedidoDTO.getIdProducto()) {
                Producto producto = productoService.getProductoById(idProducto);

                if (producto == null) {
                    return ResponseEntity.status(404).body("Producto con ID " + idProducto + " no encontrado");
                }

                // Crear el pedido para cada producto
                Pedido pedido = new Pedido();
                pedido.setUsuario(usuario);
                pedido.setProducto(producto);
                pedido.setEstado(pedidoDTO.getEstado());
                pedido.setCantidad(pedidoDTO.getCantidad());
                pedido.setFechaPedido(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(pedidoDTO.getFechaPedido()).getTime()));
                pedido.setDireccion(pedidoDTO.getDireccion());

                Pedido nuevoPedido = pedidoService.crearPedido(pedido);
                pedidos.add(nuevoPedido);
            }

            // Crear el mapa para la notificación WebSocket
            Map<String, Object> orderNotification = Map.of(
                    "message", "Nuevo pedido registrado: " + pedidos.size() + " pedido(s) de " + usuario.getUsername()
            );

            // Notificación al WebSocket después de crear un pedido
            webSocketController.sendNewOrderNotification(orderNotification);

            return ResponseEntity.status(201).body(pedidos);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("Error al crear el pedido: " + e.getMessage());
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Pedido>> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = pedidoService.obtenerTodosLosPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/lastSevenDays")
    public ResponseEntity<?> obtenerPedidosUltimosDias() {
        List<Map<String, Object>> pedidosPorFecha = pedidoService.obtenerPedidosUltimosDias(7); // Llamada con parámetro explícito
        return ResponseEntity.ok(pedidosPorFecha);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<List<Pedido>> actualizarPedido(@RequestBody PedidoDTO pedidoDTO, @PathVariable Integer id) throws ParseException {
        try {
            // Obtener detalles completos del usuario
            User usuario = userService.getUserById(pedidoDTO.getIdUser());

            if (usuario == null) {
                return ResponseEntity.status(404).body(null); // Manejar si el usuario no existe
            }

            List<Pedido> pedidosActualizados = new ArrayList<>(); // Lista para almacenar los pedidos actualizados

            // Iterar sobre la lista de ID de productos
            for (Integer idProducto : pedidoDTO.getIdProducto()) {
                Producto producto = productoService.getProductoById(idProducto);

                if (producto == null) {
                    return ResponseEntity.status(404).body(null); // Manejar si el producto no existe
                }

                // Actualizar el pedido para cada producto
                Pedido pedido = new Pedido();
                pedido.setIdPedido(id); // Usar el mismo ID del pedido para actualizar
                pedido.setUsuario(usuario);
                pedido.setProducto(producto);
                pedido.setEstado(pedidoDTO.getEstado());
                pedido.setCantidad(pedidoDTO.getCantidad()); // Asumir que la cantidad aplica a todos los productos
                pedido.setFechaPedido(new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(pedidoDTO.getFechaPedido()).getTime()));
                pedido.setDireccion(pedidoDTO.getDireccion());

                Pedido pedidoActualizado = pedidoService.actualizarPedido(pedido, id); // Actualizar el pedido
                pedidosActualizados.add(pedidoActualizado); // Añadir el pedido actualizado a la lista
            }

            // Devolver todos los pedidos actualizados
            return ResponseEntity.ok(pedidosActualizados);

        } catch (Exception e) {
            return ResponseEntity.status(400).build(); // Manejo de error
        }
    }
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<?> actualizarEstadoEnvio(@PathVariable Integer id, @RequestBody PedidoDTO pedidoDTO) {
        try {
            Pedido pedido = pedidoService.obtenerPedidoPorId(id);
            if (pedido == null) {
                return ResponseEntity.status(404).body("Pedido no encontrado");
            }

            // Usamos el estado de envío del PedidoDTO para actualizar el pedido
            String nuevoEstadoEnvio = pedidoDTO.getEstadoEnvio();
            pedido.setEstadoEnvio(nuevoEstadoEnvio);  // Aquí actualizamos el estado

            pedidoService.actualizarPedido(pedido, id);
            return ResponseEntity.ok("Estado de envío actualizado");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar el estado de envío");
        }
    }


    @DeleteMapping("/delete/{id}")
    public boolean eliminarPedido(@PathVariable Integer id) {
        try {
            pedidoService.eliminarPedido(id);

            // Crear el mapa para la notificación de eliminación de pedido
            Map<String, Object> deleteOrderNotification = Map.of(
                    "message", "Pedido eliminado con ID: " + id
            );

            // Enviar la notificación de eliminación de pedido
            webSocketController.sendDeleteNotification(deleteOrderNotification);


            return true; // Devuelve true si la eliminación fue exitosa
        } catch (Exception e) {
            return false; // Devuelve false si ocurre un error
        }
    }
}
