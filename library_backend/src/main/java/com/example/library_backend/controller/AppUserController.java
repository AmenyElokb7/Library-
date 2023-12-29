package com.example.library_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.library_backend.dao.RegisterUserDto;
import com.example.library_backend.security.service.AppUserService;

@RestController
@CrossOrigin(origins = "*")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @PostMapping("/api/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserDto registerUserDto) {
        try {
            appUserService.registerUser(registerUserDto, "USER"); // Pass the default "USER" role
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User registration failed: " + e.getMessage());
        }
    }

   @PostMapping("/api/auth/register/addAdmin")
    public ResponseEntity<String> registerAdmin() {
        try {
            // Define hardcoded values for the admin user
            String adminUsername = "admin";
            String adminEmail    = "admin@example.com";
            String adminPassword = "adminPassword"; // In a real system, use a strong, securely stored password

            // Create a DTO with the hardcoded values
            RegisterUserDto adminDto = new RegisterUserDto(adminUsername, adminPassword, adminEmail);

            // Register the admin user with the "ADMIN" role
            appUserService.registerUser(adminDto, "ADMIN");

            return ResponseEntity.ok("Admin registered successfully");
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Admin registration failed: " + e.getMessage());
        }
    }



}
