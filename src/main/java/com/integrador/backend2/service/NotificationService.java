package com.integrador.backend2.service;

import com.integrador.backend2.controller.WebSocketController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private WebSocketController webSocketController;

    // Notificación cuando se crea un nuevo pedido
    public void notifyNewOrder(String orderDetails) {
        // Crear el mapa para la notificación de nuevo pedido
        Map<String, Object> orderNotification = Map.of(
                "message", "Nuevo pedido: " + orderDetails
        );
        // Enviar la notificación de nuevo pedido
        webSocketController.sendNewOrderNotification(orderNotification);
    }

    // Notificación cuando se crea un nuevo usuario
    public void notifyNewUser(String userDetails) {
        // Crear el mapa para la notificación de nuevo usuario
        Map<String, Object> userNotification = Map.of(
                "message", "Nuevo usuario: " + userDetails
        );
        // Enviar la notificación de nuevo usuario
        webSocketController.sendNewUserNotification(userNotification);
    }

    // Notificación cuando se crea un nuevo producto
    public void notifyNewProduct(String productDetails) {
        // Crear el mapa para la notificación de nuevo producto
        Map<String, Object> productNotification = Map.of(
                "message", "Nuevo producto: " + productDetails
        );
        // Enviar la notificación de nuevo producto
        webSocketController.sendNewProductNotification(productNotification);
    }

    // Notificación cuando se elimina un pedido, usuario o producto
    public void notifyDeleted(String itemDetails) {
        // Crear el mapa para la notificación de eliminación
        Map<String, Object> deleteNotification = Map.of(
                "message", "Elemento eliminado: " + itemDetails
        );
        // Enviar la notificación de eliminación
        webSocketController.sendDeleteNotification(deleteNotification);
    }
}
