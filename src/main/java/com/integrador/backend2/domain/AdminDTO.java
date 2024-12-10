package com.integrador.backend2.domain;

public class AdminDTO {

    private Integer idAdmin;
    private String username;
    private String email;
    private String password;
    private String profileImage;

    public AdminDTO() {}

    public AdminDTO(Integer idAdmin, String username, String email, String password) {
        this.idAdmin = idAdmin;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public Integer getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Integer idAdmin) {
        this.idAdmin = idAdmin;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = this.profileImage;
    }
}
