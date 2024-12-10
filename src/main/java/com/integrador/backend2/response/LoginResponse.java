package com.integrador.backend2.response;

public class LoginResponse {
    private boolean success;
    private String message;
    private String token;
    private boolean isAdmin;
    private Long idUser;
    private String username;
    private String email; // Nuevo campo para el email
    private String profileImage;

    // Constructor actualizado
    public LoginResponse(boolean success, String message, String token, boolean isAdmin, Long idUser, String username, String email, String profileImage) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.isAdmin = isAdmin;
        this.idUser = idUser;
        this.username = username;
        this.email = email; // Inicialización del nuevo campo
        this.profileImage = profileImage;
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

    public String getEmail() { // Nuevo getter
        return email;
    }

    public void setEmail(String email) { // Nuevo setter
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
