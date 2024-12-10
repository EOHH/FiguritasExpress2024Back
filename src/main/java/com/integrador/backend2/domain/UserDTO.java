package com.integrador.backend2.domain;

public class UserDTO {

    private Integer idUser;
    private String username;
    private String email;
    private String password;
    private String googleId; // Nuevo campo para el ID de Google
    private String profileImage;

    public UserDTO(){}

    public UserDTO(Integer idUser, String username, String email, String password, String googleId, String profileImage) {
        this.idUser = idUser;
        this.username = username;
        this.email = email;
        this.password = password;
        this.googleId = googleId;
        this.profileImage = profileImage;
    }

    // Getters y Setters
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
