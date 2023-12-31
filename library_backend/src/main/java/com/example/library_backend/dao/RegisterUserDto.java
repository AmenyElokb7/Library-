package com.example.library_backend.dao;
public class RegisterUserDto {
    private String username;
    private String password;
    private String mail;

    public RegisterUserDto(String username, String password, String mail) {
        this.username = username;
        this.password = password;
        this.mail = mail;
    }
    
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getMail() {
        return mail;
    }

    // Getters and setters
    // ...
}
