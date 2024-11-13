package com.integrador.backend2.response;

public class LoginResponse {
    private boolean success;
    private String message;
    private String token;
    private boolean isAdmin;
    private Long idUser;
    private String username; // Nuevo campo para el nombre de usuario

    // Constructor actualizado para incluir username
    public LoginResponse(boolean success, String message, String token, boolean isAdmin, Long idUser, String username) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.isAdmin = isAdmin;
        this.idUser = idUser;
        this.username = username; // Inicialización del nuevo campo
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
