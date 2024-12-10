package com.integrador.backend2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Método para enviar notificación de un nuevo pedido
    @PostMapping("/sendNewOrderNotification")
    public void sendNewOrderNotification(@RequestBody Map<String, Object> order) {
        // Enviar los datos del pedido como JSON al canal /topic/new-orders
        messagingTemplate.convertAndSend("/topic/new-orders", order);
        System.out.println("Notificación enviada a /topic/new-orders: " + order);
    }

    // Método para enviar notificación de nuevo producto
    @PostMapping("/sendNewProductNotification")
    public void sendNewProductNotification(@RequestBody Map<String, Object> product) {
        // Enviar los datos del producto como JSON al canal /topic/new-products
        messagingTemplate.convertAndSend("/topic/new-products", product);
        System.out.println("Notificación enviada a /topic/new-products: " + product);
    }

    // Método para enviar notificación de nuevo usuario
    @PostMapping("/sendNewUserNotification")
    public void sendNewUserNotification(@RequestBody Map<String, Object> user) {
        // Enviar los datos del nuevo usuario como JSON al canal /topic/new-users
        messagingTemplate.convertAndSend("/topic/new-users", user);
        System.out.println("Notificación enviada a /topic/new-users: " + user);
    }

    // Método para enviar notificación de eliminación
    @PostMapping("/sendDeleteNotification")
    public void sendDeleteNotification(@RequestBody Map<String, Object> deleteInfo) {
        // Enviar los datos de eliminación como JSON al canal /topic/deleted
        messagingTemplate.convertAndSend("/topic/deleted", deleteInfo);
        System.out.println("Notificación enviada a /topic/deleted: " + deleteInfo);
    }
}
